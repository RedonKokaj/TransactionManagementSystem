package com.banking.transactionmanagementsystem.controller;

import com.banking.transactionmanagementsystem.model.Account;
import com.banking.transactionmanagementsystem.model.Transaction;
import com.banking.transactionmanagementsystem.model.TransactionType;
import com.banking.transactionmanagementsystem.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for TransactionController using MockMvc.
 * This class tests the web layer in isolation by mocking the underlying services.
 */


@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    void deposit_success() throws Exception {
        mockMvc.perform(post("/api/transactions/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                      "iban": "IBAN123",
                      "amount": 100
                    }
                """))
                .andExpect(status().isOk())
                .andExpect(content().string("Deposited 100"));
    }

    @Test
    void withdraw_success() throws Exception {
        mockMvc.perform(post("/api/transactions/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                      "iban": "IBAN123",
                      "amount": 50
                    }
                """))
                .andExpect(status().isOk());
    }

    @Test
    void getTransactions_success() throws Exception {
        Transaction tx = new Transaction();
        tx.setId(1L);
        tx.setAmount(BigDecimal.valueOf(100));
        tx.setType(TransactionType.DEPOSIT);
        tx.setTimestamp(LocalDateTime.now());

        // Mock accounts for source and target to avoid NPE
        Account account = new Account();
        account.setId(1L);
        tx.setSourceAccount(account);
        tx.setTargetAccount(account);

        when(transactionService.getAccountTransactions(1L))
                .thenReturn(List.of(tx));

        mockMvc.perform(get("/api/transactions/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "accountId": 1
                        }
                    """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].amount").value(100));
    }

}
