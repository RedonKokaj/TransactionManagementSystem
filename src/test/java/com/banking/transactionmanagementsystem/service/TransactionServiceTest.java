package com.banking.transactionmanagementsystem.service;

import com.banking.transactionmanagementsystem.model.Account;
import com.banking.transactionmanagementsystem.model.TransactionType;
import com.banking.transactionmanagementsystem.model.User;
import com.banking.transactionmanagementsystem.repository.AccountRepository;
import com.banking.transactionmanagementsystem.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Integration tests for TransactionService.
 * Verifies core banking operations including deposits, withdrawals, and transfers,
 * ensuring database consistency and correct balance calculations.
 */

@SpringBootTest
@ActiveProfiles("test")
@Rollback
@Transactional
class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    private Account accountA;
    private Account accountB;

    @BeforeEach
    void setup() {

        User user = new User();
        user.setName("Pierino");
        user.setSurname("NonSoIlCognome");
        user.setEmail("pierino@test.com");
        user.setPasswordHash("password123");
        user.setBirthDate(LocalDate.of(2000, 1, 1));
        userRepository.save(user);

        accountA = new Account();
        accountA.setIban("IBAN_A");
        accountA.setBalance(BigDecimal.valueOf(1000));
        accountA.setUser(user);
        accountA = accountRepository.save(accountA);

        accountB = new Account();
        accountB.setIban("IBAN_B");
        accountB.setBalance(BigDecimal.valueOf(500));
        accountB.setUser(user);
        accountB = accountRepository.save(accountB);

    }

    @Test
    void deposit_success() {
        transactionService.processTransaction(
                null,
                accountA.getIban(),
                BigDecimal.valueOf(200),
                TransactionType.DEPOSIT
        );

        Account updated = accountRepository.findById(accountA.getId()).orElseThrow();
        assertEquals(BigDecimal.valueOf(1200), updated.getBalance());
    }

    @Test
    void withdraw_success() {
        transactionService.processTransaction(
                accountA.getIban(),
                null,
                BigDecimal.valueOf(300),
                TransactionType.WITHDRAW
        );

        Account updated = accountRepository.findById(accountA.getId()).orElseThrow();
        assertEquals(BigDecimal.valueOf(700), updated.getBalance());
    }

    @Test
    void withdraw_insufficientBalance_throwsException() {
        assertThrows(RuntimeException.class, () ->
                transactionService.processTransaction(
                        accountA.getIban(),
                        null,
                        BigDecimal.valueOf(2000),
                        TransactionType.WITHDRAW
                )
        );
    }

    @Test
    void transfer_success() {
        transactionService.processTransaction(
                accountA.getIban(),
                accountB.getIban(),
                BigDecimal.valueOf(300),
                TransactionType.TRANSFER
        );

        Account updatedA = accountRepository.findById(accountA.getId()).orElseThrow();
        Account updatedB = accountRepository.findById(accountB.getId()).orElseThrow();

        assertEquals(BigDecimal.valueOf(700), updatedA.getBalance());
        assertEquals(BigDecimal.valueOf(800), updatedB.getBalance());
    }
}
