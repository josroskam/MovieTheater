package com.example.movietheater.controller;

import com.example.movietheater.MovieTheaterApplication;
import com.example.movietheater.database.InMemoryDatabase;
import com.example.movietheater.database.MovieDatabase;
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
        this.userService = new UserService(inMemoryDatabase); // Use shared database for user validation
    }

    @FXML
    public void userLogin(ActionEvent event) throws IOException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        User user = userService.validateUser(username, password);

        if (user != null) {
            // Pass the user and the shared InMemoryDatabase when navigating to the Dashboard
            MovieTheaterApplication.getSceneController().changeScene("Dashboard", new Context(user, null, inMemoryDatabase));
        } else {
            loginResponseLabel.setText("Wrong username or password");
        }
    }
}
