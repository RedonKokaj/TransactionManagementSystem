package com.banking.transactionmanagementsystem.controller;

import com.banking.transactionmanagementsystem.dto.accountDTO.AccountRegisterRequestDTO;
import com.banking.transactionmanagementsystem.dto.accountDTO.AccountResponseDTO;
import com.banking.transactionmanagementsystem.model.Account;
import com.banking.transactionmanagementsystem.model.User;
import com.banking.transactionmanagementsystem.service.AccountService;
import com.banking.transactionmanagementsystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for account management endpoints.
 * Endpoints: create account, retrieve all accounts for a specific user.
 */


@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;
    private final UserService userService;

    public AccountController(AccountService accountService, UserService userService){
        this.accountService = accountService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody @Valid AccountRegisterRequestDTO requestDTO){

        User user = userService.getById(requestDTO.getUserId());
        Account account = accountService.createAccount(user);

        // Transform in DTO to hide sensitive data of table user/account
        AccountResponseDTO responseDTO = new AccountResponseDTO(account);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/check/{userId}")
    public ResponseEntity<List<AccountResponseDTO>> getUserAccounts(@PathVariable Long userId) {
        List<Account> accounts = accountService.getUserAccounts(userId);

        List<AccountResponseDTO> responseDTOs = accounts.stream()
                .map(AccountResponseDTO::new) // Maps each entity to DTO using constructor
                .toList();

        return ResponseEntity.ok(responseDTOs);
    }

}
