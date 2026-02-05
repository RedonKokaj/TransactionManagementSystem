package com.banking.transactionmanagementsystem.dto.transactionDTO;

import com.banking.transactionmanagementsystem.model.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for transaction response data.
 * This class is used to send transaction details back to the client.
 */


public class TransactionResponseDTO {

    private Long id;
    private TransactionType type;
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private Long sourceAccountId;
    private Long targetAccountId;

    public TransactionResponseDTO(Long id, TransactionType type, BigDecimal amount,
                                  LocalDateTime timestamp, Long sourceAccountId, Long targetAccountId) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
    }

    //Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

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

    public Long getSourceAccountId() {
        return sourceAccountId;
    }

    public void setSourceAccountId(Long sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    public Long getTargetAccountId() {
        return targetAccountId;
    }

    public void setTargetAccountId(Long targetAccountId) {
        this.targetAccountId = targetAccountId;
    }
}
