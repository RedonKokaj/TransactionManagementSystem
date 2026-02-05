package com.banking.transactionmanagementsystem.dto.accountDTO;

import com.banking.transactionmanagementsystem.model.Account;
import java.math.BigDecimal;

/**
 * Data Transfer Object for account response data.
 * This class is used to send account details back to the client.
 */

public class AccountResponseDTO {

    private Long id;
    private String iban;
    private BigDecimal balance;
    private Long userId;

    public AccountResponseDTO(Account account){
        this.id = account.getId();
        this.iban = account.getIban();
        this.balance = account.getBalance();
        this.userId = account.getUser().getId();
    }

    //Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
