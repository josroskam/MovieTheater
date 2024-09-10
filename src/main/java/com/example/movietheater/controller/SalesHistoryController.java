package com.example.movietheater.controller;

import com.example.movietheater.MovieTheaterApplication;
import com.example.movietheater.model.Context;
import com.example.movietheater.model.User;
import javafx.event.ActionEvent;

import java.io.IOException;

public class SalesHistoryController  extends BaseController {
    private User user;

    @Override
    public void initData(Object data) {
        this.user = (User) data;
        System.out.println("SalesHistoryController initialized with user: " + user.getFullName());
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
