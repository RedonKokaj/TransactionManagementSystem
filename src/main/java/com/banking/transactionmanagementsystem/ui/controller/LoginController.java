package com.banking.transactionmanagementsystem.ui.controller;

import com.banking.transactionmanagementsystem.ui.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML private TextField emailField;

    @FXML private PasswordField passwordField;

    @FXML
    public void onLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        System.out.println("Login clicked: " + email + " / " + password);

        SceneManager.switchScene("accounts.fxml", "My Accounts");
    }

    @FXML
    public void onBack() {
        SceneManager.switchScene("main.fxml", "Welcome");
    }
}
