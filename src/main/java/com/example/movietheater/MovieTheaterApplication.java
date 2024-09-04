package com.example.movietheater;

import com.example.movietheater.controller.SceneController;
import com.example.movietheater.database.InMemoryDatabase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MovieTheaterApplication extends Application {
    private static SceneController sceneController;

    @Override
    public void start(Stage primaryStage) {
        sceneController = new SceneController(primaryStage);
        try {
            sceneController.changeScene("Login", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SceneController getSceneController() {
        return sceneController;
    }

    public static void main(String[] args) {
        launch();
    }
}