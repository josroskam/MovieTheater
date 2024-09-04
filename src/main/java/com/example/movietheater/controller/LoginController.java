package com.example.movietheater.controller;

import com.example.movietheater.MovieTheaterApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {

    public LoginController() {

    }

    @FXML
    private Label loginResponseLabel;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    public void userLogin(ActionEvent event) throws IOException{
        checkLogin();
    }

    private void checkLogin() throws IOException {
        MovieTheaterApplication m = new MovieTheaterApplication();

        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        if (username.equals("admin") && password.equals("admin")) {
            loginResponseLabel.setText("Login successful");

            m.changeScene("afterLogin.fxml");
        }

        else {
            loginResponseLabel.setText("Wrong username or password");
        }
    }

}
