    package com.example;

    import com.example.config.AppConfig;
    import org.springframework.context.annotation.AnnotationConfigApplicationContext;

    public class MainApplication {

        public static void main(String[] args) {
            // Initialize Spring Context using Java-based configuration (AppConfig.class)
            System.out.println("Starting Spring Application Context...");
            AnnotationConfigApplicationContext context = 
                    new AnnotationConfigApplicationContext(AppConfig.class);
            System.out.println("Context initialized successfully.\n");
            
            // Placeholder: We will add the actual DI and Transaction logic here in Phases 2 and 3.
            
            // Close the context when done
            context.close();
        }
    }
    
