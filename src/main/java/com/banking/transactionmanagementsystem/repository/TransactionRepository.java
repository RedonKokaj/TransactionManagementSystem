package com.banking.transactionmanagementsystem.repository;

import com.banking.transactionmanagementsystem.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for Transaction entity.
 * Provides standard CRUD operations and custom query methods using Spring Data JPA.
 */

public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {

    List<Transaction> findBySourceAccountId(Long accountId);

    List<Transaction> findByTargetAccountId(Long accountId);

    List<Transaction> findBySourceAccountIdOrTargetAccountId(
            Long sourceId, Long targetId
    );

}
