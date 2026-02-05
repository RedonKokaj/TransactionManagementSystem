package com.banking.transactionmanagementsystem.controller;

import com.banking.transactionmanagementsystem.dto.userDTO.UserRegisterRequestDTO;
import com.banking.transactionmanagementsystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for user management endpoints.
 * Endpoints: create user.
 */

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid UserRegisterRequestDTO dto){
        userService.register(dto);
        return ResponseEntity.ok("User registered successfully");
    }

}
