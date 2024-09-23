package com.example.movietheater.controller;

import com.example.movietheater.MovieTheaterApplication;
import com.example.movietheater.model.Context;
import com.example.movietheater.model.Role;
import javafx.event.ActionEvent;

import java.io.IOException;

public class NavigationController {
    private Context context;

    public void initData(Object data) {
        this.context = (Context) data;  // Storing the passed context
    }

    // Navigation handlers
    public void handleTicketSales(ActionEvent event) throws IOException {
        if (context.getUser().getRole().equals(Role.SALES)) {
            MovieTheaterApplication.getSceneController().changeScene("TicketSales", context);
        }
    }

    public void handleShowingsManagement(ActionEvent event) throws IOException {
        if (context.getUser().getRole().equals(Role.MANAGEMENT)) {
            MovieTheaterApplication.getSceneController().changeScene("ManageShowings", context);
        }
    }

    public void handleSalesHistory(ActionEvent event) throws IOException {
        if (context.getUser().getRole().equals(Role.MANAGEMENT)) {
            MovieTheaterApplication.getSceneController().changeScene("SalesHistory", context);
        }
    }
}
