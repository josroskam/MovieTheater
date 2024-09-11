package com.example.movietheater.controller;

import com.example.movietheater.model.Context;
import com.example.movietheater.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;

public class SeatSelectionController extends BaseController {

    @FXML
    private GridPane seatGridPane;  // Matches the fx:id in FXML

    private final int rows = 6;  // Number of rows for the seats
    private final int cols = 12; // Number of columns for the seats

    private User user;

    @FXML
    private AnchorPane navigationPane;  // Reference to the pane where Navigation.fxml is included

    @Override
    public void initData(Object data) {
        Context context = (Context) data;
        this.user = context.getUser();
        loadNavigation(navigationPane, context);
        loadSeats();
    }

    // Method to dynamically load seats into the GridPane
    private void loadSeats() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Button seatButton = new Button(String.valueOf(col + 1));  // Display numbers 1 to 12
                seatButton.setStyle("-fx-background-color: grey; -fx-padding: 10px;");  // Set padding
                seatButton.setPrefWidth(40);  // Set fixed width
                seatButton.setPrefHeight(40);  // Set fixed height
                seatButton.setOnAction(this::handleSeatSelection);  // Handle seat selection
                seatGridPane.add(seatButton, col, row);  // Add button to GridPane
            }
        }
    }


    @FXML
    private void handleSeatSelection(ActionEvent event) {
        Button selectedSeat = (Button) event.getSource();
        if (selectedSeat.getStyle().contains("lightgreen")) {
            selectedSeat.setStyle("-fx-background-color: grey;");  // Select seat
        } else {
            selectedSeat.setStyle("-fx-background-color: lightgreen;");  // Deselect seat
        }
        System.out.println("Selected seat: " + selectedSeat.getText());
    }
}
