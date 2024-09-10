package com.example.movietheater.controller;

import com.example.movietheater.MovieTheaterApplication;
import com.example.movietheater.model.Context;
import com.example.movietheater.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

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

    private User user;

    @Override
    public void initData(Object data) {
// Cast data to the correct type: Context
        Context context = (Context) data;
        this.user = context.getUser();

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

    public void handleTicketSales(ActionEvent event) throws IOException {
        MovieTheaterApplication.getSceneController().changeScene("TicketSales", new Context(user, null, MovieTheaterApplication.getInMemoryDatabase()));
    }

    public void handleShowingsManagement(ActionEvent event) throws IOException {
        MovieTheaterApplication.getSceneController().changeScene("ManageShowings", new Context(user, null, MovieTheaterApplication.getInMemoryDatabase()));
    }

    public void handleSalesHistory(ActionEvent event) throws IOException {
        MovieTheaterApplication.getSceneController().changeScene("SalesHistory", new Context(user, null, MovieTheaterApplication.getInMemoryDatabase()));
    }
}