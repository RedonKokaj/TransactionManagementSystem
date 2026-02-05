package com.banking.transactionmanagementsystem.ui.controller;

import com.banking.transactionmanagementsystem.dto.accountDTO.AccountResponseDTO;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.math.BigDecimal;

public class DepositController {

    @FXML
    private TextField amountField;

    private AccountResponseDTO account;

    public void setAccount(AccountResponseDTO account) {
        this.account = account;
    }

    @FXML
    public void onConfirm() {
        try {
            BigDecimal amount = new BigDecimal(amountField.getText());
            account.setBalance(account.getBalance().add(amount));

            // Add transaction to the account's list if needed
            // If using a real backend, call service to save transaction

            Stage stage = (Stage) amountField.getScene().getWindow();
            stage.close();
        } catch (NumberFormatException e) {
            System.out.println("Invalid deposit amount");
        }
    }

    @FXML
    public void onCancel() {
        Stage stage = (Stage) amountField.getScene().getWindow();
        stage.close();
    }
}
