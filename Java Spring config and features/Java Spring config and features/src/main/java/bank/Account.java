package com.example.bank;

import jakarta.persistence.*;
import java.math.BigDecimal;

// Hibernate Entity
@Entity
@Table(name = "ACCOUNTS")
public class Account {
    
    @Id
    private Long id;
    
    private String ownerName;
    
    // Use BigDecimal for currency to avoid floating-point issues
    private BigDecimal balance;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    @Override
    public String toString() {
        return "Account{id=" + id + ", ownerName='" + ownerName + "', balance=" + balance + '}';
    }
}
