package com.example.movietheater.controller;

import com.example.movietheater.MovieTheaterApplication;
import com.example.movietheater.database.MovieDatabase;
import com.example.movietheater.model.Context;
import com.example.movietheater.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SalesHistoryController  extends BaseController {
    private User user;
    private MovieDatabase movieDatabase;
    @FXML
    private AnchorPane navigationPane;  // Reference to the pane where Navigation.fxml is included

    @Override
    public void initData(Object data) {
        Context context = (Context) data;
        this.movieDatabase = context.getInMemoryDatabase().getMovieDatabase();
        this.user = context.getUser();

        loadNavigation(navigationPane, context);
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
