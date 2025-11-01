    package com.example.config;

    import com.example.bank.Account;
    import com.example.bank.Transaction;
    import com.example.di.Course;
    import com.example.di.Student;
    import org.hibernate.SessionFactory;
    import org.springframework.context.annotation.*;
    import org.springframework.jdbc.datasource.DriverManagerDataSource;
    import org.springframework.orm.hibernate5.HibernateTransactionManager;
    import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
    import org.springframework.transaction.PlatformTransactionManager;
    import org.springframework.transaction.annotation.EnableTransactionManagement;

    import javax.sql.DataSource;
    import java.util.Properties;

    // KEY 1: Enables the detection of @Transactional annotations
    @EnableTransactionManagement 
    // KEY 2: Scans for @Component, @Service, @Repository in the example package
    @ComponentScan(basePackages = "com.example") 
    @Configuration // KEY 3: Designates this class as the Spring configuration source
    public class AppConfig {

        // --- PHASE 2: Dependency Injection (DI) Demo Beans (Placeholder definitions) ---

        @Bean
        public Course javaCourse() {
            return new Course("Advanced Spring & Hibernate");
        }

        @Bean
        public Student engineeringStudent(Course javaCourse) {
            // This demonstrates Constructor Injection
            return new Student("Anannya Sharma", javaCourse);
        }

        // --- PHASE 3: Hibernate and Transaction Management Beans ---
        
        // Defines the database connection properties (using H2 in-memory DB)
        @Bean
        public DataSource dataSource() {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("org.h2.Driver");
            dataSource.setUrl("jdbc:h2:mem:bankdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
            dataSource.setUsername("sa");
            dataSource.setPassword("");
            return dataSource;
        }

        // Configures Hibernate properties
        private Properties hibernateProperties() {
            Properties properties = new Properties();
            properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
            properties.put("hibernate.show_sql", "true");
            properties.put("hibernate.hbm2ddl.auto", "update"); // Create/update schema automatically
            return properties;
        }

        // Creates the Hibernate SessionFactory
        @Bean
        public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
            LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
            sessionFactory.setDataSource(dataSource);
            // Register Hibernate Entities (We will create Account/Transaction in Phase 3)
            sessionFactory.setAnnotatedClasses(Account.class, Transaction.class);
            sessionFactory.setHibernateProperties(hibernateProperties());
            return sessionFactory;
        }

        // Defines the Transaction Manager (essential for @Transactional)
        @Bean
        public PlatformTransactionManager hibernateTransactionManager(SessionFactory sessionFactory) {
            HibernateTransactionManager transactionManager = new HibernateTransactionManager();
            transactionManager.setSessionFactory(sessionFactory);
            return transactionManager;
        }
    }
    
