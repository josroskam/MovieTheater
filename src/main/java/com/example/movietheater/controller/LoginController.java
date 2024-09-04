package com.example.movietheater.controller;

import com.example.movietheater.MovieTheaterApplication;
import com.example.movietheater.database.InMemoryDatabase;
import com.example.movietheater.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController extends ControllerBase {

    @FXML
    private Label loginResponseLabel;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    private InMemoryDatabase database;

    public LoginController() {
        this.database = new InMemoryDatabase();
    }

    @Override
    public void initData(Object data) {
        // No initialization data required for login
    }

    @FXML
    public void userLogin(ActionEvent event) throws IOException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        User user = database.getUserDatabase().getUserByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            loginResponseLabel.setText("Login successful");
            MovieTheaterApplication.getSceneController().changeScene("Dashboard", user);
        } else {
            loginResponseLabel.setText("Wrong username or password");
        }
    }
}