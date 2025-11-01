package com.example.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

// Service Layer: Contains business logic and transaction demarcation
@Service
public class AccountService {

    private final AccountDAO accountDAO;

    @Autowired
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    // THE CORE REQUIREMENT: This method is transactional.
    // If an unchecked exception occurs (like NullPointerException), 
    // the entire database operation (deduct, add, save transaction) 
    // will be automatically rolled back by Spring.
    @Transactional
    public void transferMoney(Long fromId, Long toId, BigDecimal amount, boolean introduceFailure) {
        System.out.println("\n--- Attempting Transfer: " + amount + " from " + fromId + " to " + toId + " ---");

        Account fromAccount = accountDAO.findById(fromId);
        Account toAccount = accountDAO.findById(toId);
        
        if (fromAccount == null || toAccount == null) {
            throw new IllegalArgumentException("One or both accounts not found.");
        }
        
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient funds in account " + fromId);
        }

        // 1. Deduct money (First DB operation)
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        accountDAO.update(fromAccount);

        // --- Simulated Failure Point ---
        if (introduceFailure) {
            System.out.println("\n*** SIMULATING FAILURE: Introducing NullPointerException now... ***");
            // This will trigger a rollback of the whole transaction
            throw new RuntimeException("Simulated failure after deduction."); 
        }
        // -------------------------------
        
        // 2. Add money (Second DB operation - only executes if no failure above)
        toAccount.setBalance(toAccount.getBalance().add(amount));
        accountDAO.update(toAccount);

        // 3. Record transaction (Third DB operation)
        Transaction t = new Transaction();
        t.setFromAccountId(fromId);
        t.setToAccountId(toId);
        t.setAmount(amount);
        accountDAO.saveTransaction(t);

        System.out.println("Transfer SUCCESSFUL: $" + amount + " moved.");
    }
    
    // Method to display current balances (optional, non-transactional read)
    @Transactional(readOnly = true)
    public void displayBalances() {
        System.out.println("\n--- Current Account Balances ---");
        accountDAO.findAll().forEach(System.out::println);
        System.out.println("--------------------------------");
    }
}
