package com.example.movietheater.controller;

import javafx.fxml.FXMLLoader;
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
        Scene scene = new Scene(loader.load());
        ControllerBase controller = loader.getController();
        controller.initData(data);
        stage.setTitle(component);
        stage.setScene(scene);
        stage.show();
    }
}
