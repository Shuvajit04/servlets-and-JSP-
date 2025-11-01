package com.example.bank;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

// Persistence Layer: Handles direct database interaction
@Repository
public class AccountDAO {

    private final SessionFactory sessionFactory;

    // Dependency Injection (Constructor Injection)
    @Autowired
    public AccountDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public Account findById(Long id) {
        // Uses the current transactional session
        return getCurrentSession().find(Account.class, id);
    }

    public void update(Account account) {
        // Updates the account within the transaction
        getCurrentSession().merge(account);
    }

    public void save(Account account) {
        getCurrentSession().persist(account);
    }
    
    public void saveTransaction(Transaction transaction) {
        getCurrentSession().persist(transaction);
    }

    public List<Account> findAll() {
        return getCurrentSession().createQuery("from Account", Account.class).list();
    }

    // Utility method to set up initial data (not transactional)
    public void initializeAccounts() {
        // Using openSession() here because this method is called outside of a transaction 
        // before the main application logic starts.
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            
            // Clean slate
            session.createMutationQuery("DELETE from Transaction").executeUpdate();
            session.createMutationQuery("DELETE from Account").executeUpdate();

            Account a1 = new Account();
            a1.setId(1001L);
            a1.setOwnerName("Alice");
            a1.setBalance(new BigDecimal("1000.00"));

            Account a2 = new Account();
            a2.setId(1002L);
            a2.setOwnerName("Bob");
            a2.setBalance(new BigDecimal("500.00"));
            
            session.persist(a1);
            session.persist(a2);
            session.getTransaction().commit();
            System.out.println("--- Initial Account Balances Set ---");
        } catch (Exception e) {
            System.err.println("Error initializing accounts: " + e.getMessage());
        }
    }
}
