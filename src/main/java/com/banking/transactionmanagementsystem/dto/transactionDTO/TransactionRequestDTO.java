package com.banking.transactionmanagementsystem.dto.transactionDTO;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for transaction requests.
 * Used to capture the necessary information to perform a transaction.
 */

public class TransactionRequestDTO {

    @NotNull
    private BigDecimal amount;

    private LocalDateTime timestamp; // optional, can default to now

    private String sourceAccountIban; // nullable for DEPOSIT

    private String targetAccountIban; // nullable for WITHDRAW

    //Getters & Setters

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getSourceAccountIban() {
        return sourceAccountIban;
    }

    public void setSourceAccountId(String sourceAccountId) {
        this.sourceAccountIban = sourceAccountId;
    }

    public String getTargetAccountIban() {
        return targetAccountIban;
    }

    public void setTargetAccountIban(String targetAccountId) {
        this.targetAccountIban = targetAccountId;
    }
}
