package com.banking.transactionmanagementsystem.ui.controller;

import com.banking.transactionmanagementsystem.dto.accountDTO.AccountResponseDTO;
import com.banking.transactionmanagementsystem.model.Account;
import com.banking.transactionmanagementsystem.model.User;
import com.banking.transactionmanagementsystem.ui.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;

public class AccountsController {

    @FXML
    private ListView<AccountResponseDTO> accountsListView;

    @FXML
    private Label messageLabel;

    @FXML
    public void initialize() {

        Account a1 = new Account();
        a1.setId(1L);
        a1.setIban("IT60X0000000000000000001");
        a1.setBalance(BigDecimal.valueOf(1000));

        User u = new User();
        u.setId(10L);
        a1.setUser(u);

        Account a2 = new Account();
        a2.setId(2L);
        a2.setIban("IT60X0000000000000000002");
        a2.setBalance(BigDecimal.valueOf(250));
        a2.setUser(u);

        accountsListView.getItems().addAll(
                new AccountResponseDTO(a1),
                new AccountResponseDTO(a2)
        );

        accountsListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(AccountResponseDTO item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getIban() + " — " + item.getBalance() + " €");
                }
            }
        });


        accountsListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                AccountResponseDTO selected = accountsListView.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/account-details.fxml"));
                        Parent root = loader.load();

                        AccountDetailsController controller = loader.getController();
                        controller.setAccount(selected);

                        Stage stage = new Stage();
                        stage.setTitle("Account Details");
                        stage.setScene(new Scene(root));
                        stage.show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }



    @FXML
    public void onCreateAccount() {
        messageLabel.setText("Create account clicked");
        // next step → call backend POST /accounts/create
    }

    private void openAccountDetails(AccountResponseDTO account) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ui/account-details.fxml")
            );
            Parent root = loader.load();

            // Get the controller instance created by FXMLLoader
            AccountDetailsController controller = loader.getController();

            // Set the account on that instance
            controller.setAccount(account);

            Stage stage = new Stage();
            stage.setTitle("Account Details");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @FXML
    public void logout() {
        SceneManager.switchScene("main.fxml", "Welcome");
    }
}
