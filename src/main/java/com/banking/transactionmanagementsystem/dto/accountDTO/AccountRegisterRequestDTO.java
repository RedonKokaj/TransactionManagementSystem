package com.banking.transactionmanagementsystem.dto.accountDTO;

import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for account registration requests.
 * Used to capture the necessary information to open a new bank account.
 */

public class AccountRegisterRequestDTO {

    @NotNull
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
