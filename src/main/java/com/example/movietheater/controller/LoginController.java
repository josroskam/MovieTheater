package com.example.movietheater.controller;
import com.example.movietheater.MovieTheaterApplication;
import com.example.movietheater.database.InMemoryDatabase;
import com.example.movietheater.model.Context;
import com.example.movietheater.model.User;
import com.example.movietheater.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController extends BaseController {

    @FXML
    private Label loginResponseLabel;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    private UserService userService;
    private InMemoryDatabase inMemoryDatabase;

    @Override
    public void initialize(Object data) {
        Context context = (Context) data;
        this.inMemoryDatabase = context.getInMemoryDatabase();
        this.userService = new UserService(inMemoryDatabase);
    }

    @FXML
    public void userLogin(ActionEvent event) throws IOException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        // Null or empty input handling
        if (isInputInvalid(username, password)) {
            ShowError("Please enter a username and password");
            return;
        }

        try {
            User user = userService.validateUser(username, password);

            if (user != null) {
                MovieTheaterApplication.getSceneController().changeScene("Dashboard", new Context(user, null, inMemoryDatabase));
            } else {
                ShowError("Wrong username or password");
            }
        } catch (Exception e) {
            ShowError("An error occurred. Please try again.");
        }
    }

    private boolean isInputInvalid(String username, String password) {
        return username == null || username.isEmpty() || password == null || password.isEmpty();
    }

    private void ShowError(String message) {
        loginResponseLabel.setText(message);
    }
}
