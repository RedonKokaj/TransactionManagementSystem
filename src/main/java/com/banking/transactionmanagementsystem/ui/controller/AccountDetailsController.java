package com.banking.transactionmanagementsystem.ui.controller;

import com.banking.transactionmanagementsystem.dto.accountDTO.AccountResponseDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class AccountDetailsController {

    @FXML
    private Label ibanLabel;

    @FXML
    private Label balanceLabel;

    @FXML
    private ListView<String> transactionsListView;

    private AccountResponseDTO account;

    public void setAccount(AccountResponseDTO account) {
        this.account = account;
        ibanLabel.setText(account.getIban());
        balanceLabel.setText(String.valueOf(account.getBalance()) + " €");
        // populate transactions if you have them
    }

    @FXML
    public void onDeposit() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ui/deposit.fxml")
            );
            Parent root = loader.load();

            DepositController controller = loader.getController();
            controller.setAccount(account);

            Stage stage = new Stage();
            stage.setTitle("Deposit Money");
            stage.setScene(new Scene(root));
            stage.showAndWait(); // Wait until transaction scene closes

            // Update balance and transaction list after deposit
            balanceLabel.setText(account.getBalance() + " €");
            transactionsListView.getItems().add("Deposited: " + account.getBalance() + " €");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @FXML
//    public void onWithdraw() {
//        try {
//            BigDecimal amount = new BigDecimal(withdrawField.getText());
//            if (amount.compareTo(account.getBalance()) > 0) {
//                System.out.println("Insufficient funds");
//                return;
//            }
//
//            account.setBalance(account.getBalance().subtract(amount));            balanceLabel.setText(account.getBalance() + " €");
//            transactionsListView.getItems().add("Withdrew: " + amount + " €");
//            withdrawField.clear();
//        } catch (NumberFormatException e) {
//            System.out.println("Invalid withdraw amount");
//        }
//    }

//    @FXML
//    public void onTransfer() {
//        try {
//            BigDecimal amount = new BigDecimal(transferAmountField.getText());
//            String targetIban = transferIbanField.getText().trim();
//
//            if (amount.compareTo(account.getBalance()) > 0) {
//                System.out.println("Insufficient funds for transfer");
//                return;
//            }
//
//            account.setBalance(account.getBalance().subtract(amount));
//            balanceLabel.setText(account.getBalance() + " €");
//
//            transactionsListView.getItems().add("Transferred: " + amount + " € to " + targetIban);
//            transferAmountField.clear();
//            transferIbanField.clear();
//        } catch (NumberFormatException e) {
//            System.out.println("Invalid transfer amount");
//        }
//    }
}
