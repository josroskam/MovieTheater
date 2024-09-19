package com.example.movietheater.controller;

import com.example.movietheater.MovieTheaterApplication;
import com.example.movietheater.model.Context;
import com.example.movietheater.model.Movie;
import com.example.movietheater.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DashboardController extends BaseController {
    @FXML
    private Label welcomeLabel;

    @FXML
    private Label roleLabel;

    @FXML
    private Label datetimeLabel;

    @FXML
    private AnchorPane navigationPane;  // Reference to the pane where Navigation.fxml is included

    private User user;

    @Override
    public void initData(Object data) {
        Context context = (Context) data;
        this.user = context.getUser();

        // Call the common method from BaseController to load navigation
        loadNavigation(navigationPane, context);

        // Set dashboard labels
        welcomeLabel.setText("Welcome " + user.getFullName());
        roleLabel.setText("You are logged in as: " + user.getRole().toString());

        // Format the current date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String formattedDate = now.format(formatter);

        datetimeLabel.setText("The current date and time is: " + formattedDate);
    }
}
