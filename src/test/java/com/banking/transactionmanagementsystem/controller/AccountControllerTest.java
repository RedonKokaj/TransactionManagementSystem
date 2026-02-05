package com.banking.transactionmanagementsystem.controller;

import com.banking.transactionmanagementsystem.model.Account;
import com.banking.transactionmanagementsystem.model.User;
import com.banking.transactionmanagementsystem.service.AccountService;
import com.banking.transactionmanagementsystem.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for AccountController using MockMvc.
 * This class tests the web layer in isolation by mocking the underlying services.
 */

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @MockBean
    private UserService userService; // <-- needed to satisfy the controller

    @Test
    void createAccount_success() throws Exception {
        User user = new User();
        user.setId(1L);

        Account account = new Account();
        account.setIban("IBAN123");
        account.setUser(user);

        // Mock userService
        when(userService.getById(1L)).thenReturn(user);
        // Mock accountService
        when(accountService.createAccount(user)).thenReturn(account);

        mockMvc.perform(post("/api/accounts/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "userId": 1,
                          "iban": "IBAN123",
                          "balance": 0
                        }
                    """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.iban").value("IBAN123"));
    }

    @Test
    void getUserAccounts_success() throws Exception {
        // Create a mock user
        User user = new User();
        user.setId(1L);

        // Create mock accounts and assign the user
        Account a1 = new Account();
        a1.setIban("IBAN1");
        a1.setUser(user);

        Account a2 = new Account();
        a2.setIban("IBAN2");
        a2.setUser(user);

        when(accountService.getUserAccounts(1L)).thenReturn(List.of(a1, a2));

        mockMvc.perform(get("/api/accounts/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "userId": 1
                        }
                    """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].iban").value("IBAN1"))
                .andExpect(jsonPath("$[1].iban").value("IBAN2"));
    }


}

