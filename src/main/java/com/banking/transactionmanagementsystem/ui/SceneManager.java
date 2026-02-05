package com.banking.transactionmanagementsystem.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {

    private static Stage primaryStage;

    public static void setStage(Stage stage) {
        primaryStage = stage;
    }

    public static void switchScene(String fxml, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    SceneManager.class.getResource("/ui/" + fxml)
            );
            Parent root = loader.load();

            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle(title);
            primaryStage.show();

        } catch (IOException e) {
            throw new RuntimeException("Failed to load scene: " + fxml, e);
        }
    }

    // ✅ NEW generic method WITH controller access
    public static <T> T switchSceneWithController(String fxml, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    SceneManager.class.getResource("/ui/" + fxml)
            );
            Parent root = loader.load();

            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle(title);
            primaryStage.show();

            return loader.getController();

        } catch (IOException e) {
            throw new RuntimeException("Failed to load scene: " + fxml, e);
        }
    }
}
