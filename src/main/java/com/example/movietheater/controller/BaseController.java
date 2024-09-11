package com.example.movietheater.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public abstract class BaseController {
    public abstract void initData(Object data);

    // Common method to load and initialize navigation
    public void loadNavigation(AnchorPane navigationPane, Object context) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/movietheater/Navigation.fxml"));
            AnchorPane navigationContent = loader.load();  // Load the content of Navigation.fxml

            // Set the loaded content as the child of the navigationPane
            navigationPane.getChildren().setAll(navigationContent);

            // Get the navigation controller and pass the context
            NavigationController navigationController = loader.getController();
            navigationController.initData(context);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
