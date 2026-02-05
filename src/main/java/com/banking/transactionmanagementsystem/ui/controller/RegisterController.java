package com.banking.transactionmanagementsystem.ui.controller;

import com.banking.transactionmanagementsystem.ui.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RegisterController {

    @FXML private TextField nameField;
    @FXML private TextField surnameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private DatePicker birthDatePicker;
    @FXML private Label messageLabel;

    @FXML
    public void register() {

        // BASIC validation (frontend level)
        if (nameField.getText().isBlank()
                || surnameField.getText().isBlank()
                || emailField.getText().isBlank()
                || passwordField.getText().isBlank()
                || birthDatePicker.getValue() == null) {

            messageLabel.setText("All fields are required");
            return;
        }

        // 🔴 TEMP: backend call will be added next
        messageLabel.setText("User registered successfully!");

        // After success → go to login
        SceneManager.switchScene("login.fxml", "Login");
    }

    @FXML
    public void onBack() {
        SceneManager.switchScene("main.fxml", "Welcome");
    }
}
