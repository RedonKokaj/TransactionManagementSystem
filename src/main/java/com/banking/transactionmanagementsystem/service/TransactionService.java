package com.banking.transactionmanagementsystem.service;

import com.banking.transactionmanagementsystem.model.Account;
import com.banking.transactionmanagementsystem.model.Transaction;
import com.banking.transactionmanagementsystem.model.TransactionType;
import com.banking.transactionmanagementsystem.repository.AccountRepository;
import com.banking.transactionmanagementsystem.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing bank transaction business logic.
 * Handles transaction processing and retrieval of transactions history of a specified account.
 */

@Service
@Transactional
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository){
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    //DEPOSIT - WITHDRAW - TRANSFER
    public void processTransaction(String sourceIban, String targetIban, BigDecimal amount, TransactionType type) {

        //compareTo gives the following values:
        //-1 if amount is less than 0
        //0 if amount is equal to 0
        //1 if amount is more than 0
        //With that value we can then compare if it is <= 0
        //BigDecimal is a complex object
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Invalid amount");
        }

        Account source = null;
        Account target = null;

        //PRE-VALIDATION: Find all necessary accounts first
        if (type == TransactionType.WITHDRAW || type == TransactionType.TRANSFER) {
            Optional<Account> sourceOpt = accountRepository.findByIban(sourceIban);
            if (sourceOpt.isEmpty()) {
                throw new RuntimeException("Source account not found");
            }
            source = sourceOpt.get();

            //Check funds before doing anything
            if (source.getBalance().compareTo(amount) < 0) {
                throw new RuntimeException("Insufficient funds");
            }
        }

        if (type == TransactionType.DEPOSIT || type == TransactionType.TRANSFER) {
            Optional<Account> targetOpt = accountRepository.findByIban(targetIban);
            if (targetOpt.isEmpty()) {
                throw new RuntimeException("Target account not found");
            }
            target = targetOpt.get();
        }

        //EXECUTION: If we reached this point, we have the accounts we need
        if (source != null) { //so either WITHDRAW or TRANSFER
            source.setBalance(source.getBalance().subtract(amount));
            accountRepository.save(source);
        }

        if (target != null) { //so either DEPOSIT or TRANSFER
            target.setBalance(target.getBalance().add(amount));
            accountRepository.save(target);
        }

        //Create transaction record
        Transaction tx = new Transaction();
        tx.setAmount(amount);
        tx.setTimestamp(LocalDateTime.now());
        tx.setType(type);
        tx.setSourceAccount(source);
        tx.setTargetAccount(target);

        transactionRepository.save(tx);
    }

    public List<Transaction> getAccountTransactions(Long accountId) {
        return transactionRepository
                .findBySourceAccountIdOrTargetAccountId(accountId, accountId);
    }
}
