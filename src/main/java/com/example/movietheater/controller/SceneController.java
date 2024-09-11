package com.example.movietheater.controller;

import com.example.movietheater.model.Context;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
    private Stage stage;

    public SceneController(Stage stage) {
        this.stage = stage;
    }

    public void changeScene(String component, Object data) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/movietheater/" + component + ".fxml"));
        Parent root = loader.load();
        BaseController controller = loader.getController();

        // Initialize the data for the main controller
        controller.initData(data);

        // Check if the navigation bar exists and initialize it with the context
        NavigationController navController = (NavigationController) loader.getNamespace().get("navigationController");
        if (navController != null) {
            navController.initData(data);
        }

        stage.setScene(new Scene(root));
        stage.show();
    }

}
