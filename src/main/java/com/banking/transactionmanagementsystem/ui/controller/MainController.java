package com.banking.transactionmanagementsystem.ui.controller;

import com.banking.transactionmanagementsystem.ui.SceneManager;

public class MainController {

    public void goToLogin() {
        SceneManager.switchScene("login.fxml", "Login");
    }

    public void goToRegister() {
        SceneManager.switchScene("register.fxml", "Register");
    }
}
