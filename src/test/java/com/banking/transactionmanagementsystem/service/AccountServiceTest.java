package com.banking.transactionmanagementsystem.service;

import com.banking.transactionmanagementsystem.model.Account;
import com.banking.transactionmanagementsystem.model.User;
import com.banking.transactionmanagementsystem.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration tests for AccountService.
 * These tests verify the business logic integration with the actual database
 * using an active 'test' profile and transactional rollbacks.
 */

@SpringBootTest
@ActiveProfiles("test")
@Rollback
@Transactional
class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setup() {
        user = new User();
        user.setName("Pierino");
        user.setSurname("NonSoIlCognome");
        user.setEmail("pierino@test.com");
        user.setPasswordHash("password123");
        user.setBirthDate(LocalDate.of(2000, 1, 1));
        userRepository.save(user);
    }

    @Test
    void createAccount_success() {
        Account account = accountService.createAccount(user);

        assertNotNull(account.getId());
        assertEquals(user.getId(), account.getUser().getId());
        assertNotNull(account.getIban());
    }

    @Test
    void getUserAccounts_returnsAccounts() {
        accountService.createAccount(user);
        accountService.createAccount(user);

        List<Account> accounts = accountService.getUserAccounts(user.getId());

        assertEquals(2, accounts.size());
    }

    @Test
    void getUserAccounts_invalidUser_returnsEmptyList() {
        List<Account> accounts = accountService.getUserAccounts(999L);
        assertEquals(0, accounts.size());
    }

}

