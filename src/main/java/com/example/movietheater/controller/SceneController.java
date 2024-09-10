package com.example.movietheater.controller;

import com.example.movietheater.model.Context;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
    private Stage stage;

    public SceneController(Stage stage) {
        this.stage = stage;
    }

    public void changeScene(String component, Context context) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/movietheater/" + component + ".fxml"));
        Scene scene = new Scene(loader.load());

        BaseController controller = loader.getController();
        controller.initData(context);

        stage.setTitle(component);
        stage.setScene(scene);
        stage.show();
    }
}
