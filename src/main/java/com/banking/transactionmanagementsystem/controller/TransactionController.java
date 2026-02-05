package com.banking.transactionmanagementsystem.controller;

import com.banking.transactionmanagementsystem.dto.transactionDTO.TransactionRequestDTO;
import com.banking.transactionmanagementsystem.dto.transactionDTO.TransactionResponseDTO;
import com.banking.transactionmanagementsystem.model.Transaction;
import com.banking.transactionmanagementsystem.model.TransactionType;
import com.banking.transactionmanagementsystem.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller for transaction management endpoints.
 * Endpoints: perform deposit, perform withdrawal, perform money transfer,
 * and retrieve all transactions for a specific account.
 */

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestBody @Valid TransactionRequestDTO requestDTO){
        transactionService.processTransaction("", requestDTO.getTargetAccountIban(), requestDTO.getAmount(), TransactionType.DEPOSIT);
        return ResponseEntity.ok("Deposited " + requestDTO.getAmount());
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestBody @Valid TransactionRequestDTO requestDTO){
        transactionService.processTransaction(requestDTO.getSourceAccountIban(), "", requestDTO.getAmount(), TransactionType.WITHDRAW);
        return ResponseEntity.ok("Withdrawn " + requestDTO.getAmount());
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody @Valid TransactionRequestDTO requestDTO){
        transactionService.processTransaction(requestDTO.getSourceAccountIban(), requestDTO.getTargetAccountIban(), requestDTO.getAmount(), TransactionType.TRANSFER);
        return ResponseEntity.ok("Transferred " + requestDTO.getAmount());
    }

    @GetMapping("/check/{accountId}")
    public ResponseEntity<List<TransactionResponseDTO>> getUserTransactions(
            @PathVariable Long accountId) {

        List<Transaction> transactions = transactionService.getAccountTransactions(accountId);

        List<TransactionResponseDTO> response = transactions.stream()
                .map(tx -> new TransactionResponseDTO(
                        tx.getId(),
                        tx.getType(),
                        tx.getAmount(),
                        tx.getTimestamp(),
                        tx.getSourceAccount() != null ? tx.getSourceAccount().getId() : null,
                        tx.getTargetAccount() != null ? tx.getTargetAccount().getId() : null
                ))
                .toList();

        return ResponseEntity.ok(response);
    }
}
