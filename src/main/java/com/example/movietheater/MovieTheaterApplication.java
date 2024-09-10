package com.example.movietheater;

import com.example.movietheater.controller.SceneController;
import com.example.movietheater.database.InMemoryDatabase;
import com.example.movietheater.model.Context;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class MovieTheaterApplication extends Application {
    private static SceneController sceneController;
    private static InMemoryDatabase inMemoryDatabase;

    @Override
    public void start(Stage primaryStage) throws IOException {
        inMemoryDatabase = new InMemoryDatabase();
        sceneController = new SceneController(primaryStage);

        // Pass the InMemoryDatabase to the Login scene via the Context
        sceneController.changeScene("Login", new Context(null, null, inMemoryDatabase));
    }

    public static InMemoryDatabase getInMemoryDatabase() {
        return inMemoryDatabase;
    }

    public static SceneController getSceneController() {
        return sceneController;
    }

    public static void main(String[] args) {
        launch();
    }
}
