package com.example.movietheater.controller;

import com.example.movietheater.MovieTheaterApplication;
import com.example.movietheater.database.SalesDatabase;
import com.example.movietheater.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SeatSelectionController extends BaseController {

    @FXML
    private GridPane seatGridPane;

    @FXML
    private Button confirmSelectionBtn;

    @FXML
    private Label selectedSeatsLabel;

    @FXML
    private TextField customerNameField;

    @FXML
    private AnchorPane navigationPane;  // Reference to the pane where Navigation.fxml is included

    @FXML
    private Label movieTitleLabel;

    private SalesDatabase salesDatabase;  // Reference to the sales database

    private final int rows = 6;
    private final int cols = 12;

    private List<Seat> selectedSeats = new ArrayList<>();  // Store selected seats
    private List<Seat> soldSeats = new ArrayList<>();      // Store already sold seats

    private User user;
    private Movie movie;

    @Override
    public void initData(Object data) {
        Context context = (Context) data;

        this.user = context.getUser();
        this.movie = context.getMovie();
        this.salesDatabase = context.getInMemoryDatabase().getSalesDatabase();

        retrieveSoldSeats();
        loadSeats();
        loadNavigation(navigationPane, context);

        movieTitleLabel.setText(movie.getStartTime() + " " + movie.getTitle());
    }

    // Method to retrieve sold seats for the current movie
    private void retrieveSoldSeats() {
        List<Sales> salesList = salesDatabase.getSalesByMovie(movie);
        for (Sales sale : salesList) {
            soldSeats.addAll(sale.getSeats());  // Add all sold seats to the soldSeats list
        }
    }

    // Method to dynamically load seats into the GridPane
    private void loadSeats() {
        for (int row = 0; row < rows; row++) {
            // Add a label with the row number at the start of each row
            Label rowLabel = new Label("Row " + (row + 1));
            rowLabel.setStyle("-fx-padding: 10px; -fx-font-weight: bold;");
            seatGridPane.add(rowLabel, 0, row);  // Add the label in the first column (index 0)

            // Generate seat buttons for each column, starting from column 1
            for (int col = 0; col < cols; col++) {
                final int currentRow = row;
                final int currentCol = col;

                // Create a button for each seat
                Button seatButton = new Button(String.valueOf(currentCol + 1));  // Display numbers 1 to 12

                // Set fixed size for the button
                seatButton.setMinWidth(40);  // Minimum width
                seatButton.setMinHeight(40);  // Minimum height
                seatButton.setPrefWidth(40);  // Preferred width
                seatButton.setPrefHeight(40);  // Preferred height
                seatButton.setMaxWidth(40);  // Maximum width
                seatButton.setMaxHeight(40);  // Maximum height

                seatButton.setStyle("-fx-background-color: grey; -fx-padding: 10px;");

                Seat seat = new Seat(currentRow, currentCol);

                // Check if the seat is sold and mark it accordingly
                if (soldSeats.contains(seat)) {
                    seatButton.setStyle("-fx-background-color: red; -fx-padding: 10px;");
                    seatButton.setDisable(true);  // Disable the button for sold seats
                } else {
                    seatButton.setOnAction(event -> handleSeatSelection(event, currentRow, currentCol));
                }

                // Add the button to the GridPane starting from column 1 (as column 0 is reserved for row labels)
                seatGridPane.add(seatButton, currentCol + 1, currentRow);  // Column shifted by +1
            }
        }
    }


    // Handle seat selection and toggle selection
    private void handleSeatSelection(ActionEvent event, int row, int col) {
        Button selectedSeat = (Button) event.getSource();
        Seat seat = new Seat(row, col);  // Create a seat object based on row and col

        if (selectedSeat.getStyle().contains("lightgreen")) {
            selectedSeat.setStyle("-fx-background-color: grey;");  // Deselect seat
            selectedSeats.remove(seat);  // Remove seat from the selected seats list
        } else {
            selectedSeat.setStyle("-fx-background-color: lightgreen;");  // Select seat
            selectedSeats.add(seat);  // Add seat to the selected seats list
        }

        // Update the number of selected seats
        confirmSelectionBtn.setText("Sell " + selectedSeats.size() + " tickets");

        // Update the label to show selected seats
        updateSelectedSeatsLabel();
    }

    // Method to update the label showing selected seats
    private void updateSelectedSeatsLabel() {
        StringBuilder seatsText = new StringBuilder();
        for (Seat seat : selectedSeats) {
            seatsText.append("Row ").append(seat.getRow() + 1).append(" / ").append(seat.getColumn() + 1).append("\n");
        }
        selectedSeatsLabel.setText(seatsText.toString());
    }

    // Method to confirm seat selection and create a sales object
    public void confirmSelection() throws IOException {
        // Create a new Sales object based on selected seats
        Sales sales = new Sales(customerNameField.getText(), movie, LocalDateTime.now(), selectedSeats);

        // Add the sale to the database
        salesDatabase.addSale(sales);

        // Return to ticket sales view
        MovieTheaterApplication.getSceneController().changeScene("TicketSales", new Context(user, null, MovieTheaterApplication.getInMemoryDatabase()));
    }
}
