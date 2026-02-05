package com.banking.transactionmanagementsystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Shows that the application is running.
 */

@RestController
public class RootController {

    @GetMapping("/")
    public String home() {
        return "Application is running";
    }
}
