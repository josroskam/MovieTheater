package com.example.movietheater.controller;

import com.example.movietheater.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DashboardController extends ControllerBase {
    @FXML
    private Label welcomeLabel;

    @FXML Label roleLabel;

    @FXML Label datetimeLabel;

    private User user;

    @Override
    public void initData(Object data) {
        this.user = (User) data;
        welcomeLabel.setText("Welcome " + user.getFullName());
        roleLabel.setText("You are logged in as: " + user.getRole().toString());

        // Format the current date and time
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd HH yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        String formattedDate = now.format(dateFormatter);
        String formattedTime = now.format(timeFormatter);

        datetimeLabel.setText("The current date and time is: " + formattedDate + " " + formattedTime);
    }
}