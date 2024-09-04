package com.example.movietheater.controller;

import com.example.movietheater.MovieTheaterApplication;
import com.example.movietheater.model.User;
import javafx.event.ActionEvent;

import java.io.IOException;

public class ManageShowingsController extends BaseController {
    private User user;

    @Override
    public void initData(Object data) {
        this.user = (User) data;
        System.out.println("ManageShowingsController initialized with user: " + user.getFullName());
    }

    public void handleTicketSales(ActionEvent event) throws IOException {
        MovieTheaterApplication.getSceneController().changeScene("TicketSales", user);
    }

    public void handleShowingsManagement(ActionEvent event) throws IOException {
        MovieTheaterApplication.getSceneController().changeScene("ManageShowings", user);
    }

    public void handleSalesHistory(ActionEvent event) throws IOException {
        MovieTheaterApplication.getSceneController().changeScene("SalesHistory", user);
    }
}
