package com.banking.transactionmanagementsystem.controller;

import com.banking.transactionmanagementsystem.dto.userDTO.UserLoginRequestDTO;
import com.banking.transactionmanagementsystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for authentication and login endpoints.
 * Endpoints: perform user login.
 */

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody UserLoginRequestDTO userLoginRequestDTO) {
        try {
            userService.login(userLoginRequestDTO.getEmail(), userLoginRequestDTO.getPassword());
            return ResponseEntity.ok("Login successful");
        } catch (RuntimeException e) {
            if ("Invalid credentials".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
            throw e; // Rethrow other exceptions
        }
    }

}
