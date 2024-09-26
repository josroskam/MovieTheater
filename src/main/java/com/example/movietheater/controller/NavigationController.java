package com.example.movietheater.controller;

import com.example.movietheater.MovieTheaterApplication;
import com.example.movietheater.model.Context;
import com.example.movietheater.model.Role;
import javafx.event.ActionEvent;

import java.io.IOException;

public class NavigationController {
    private Context context;

    public void initialize(Object data) {
        this.context = (Context) data;
    }

    private void navigateTo(String scene, Role requiredRole, ActionEvent event) throws IOException {
//        if (context.getUser().getRole().equals(requiredRole)) {
            MovieTheaterApplication.getSceneController().changeScene(scene, context);
//        } else {
//            System.out.println("User does not have permission to access this page");
//        }
    }

    // Navigation handlers
    public void handleTicketSales(ActionEvent event) throws IOException {
        navigateTo("TicketSales", Role.SALES, event);
    }

    public void handleShowingsManagement(ActionEvent event) throws IOException {
        navigateTo("ManageShowings", Role.MANAGEMENT, event);
    }

    public void handleSalesHistory(ActionEvent event) throws IOException {
        navigateTo("SalesHistory", Role.MANAGEMENT, event);
    }
}
