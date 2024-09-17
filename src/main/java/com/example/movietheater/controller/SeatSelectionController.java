package com.example.movietheater.controller;

import com.example.movietheater.MovieTheaterApplication;
import com.example.movietheater.database.SalesDatabase;
import com.example.movietheater.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

    private SalesDatabase salesDatabase;  // Reference to the sales database

    private final int rows = 6;
    private final int cols = 12;

    private List<Seat> selectedSeats = new ArrayList<>();  // Store selected seats

    private User user;
    private Movie movie;

    @Override
    public void initData(Object data) {
        Context context = (Context) data;
        this.user = context.getUser();
        this.movie = context.getMovie();
        this.salesDatabase = context.getInMemoryDatabase().getSalesDatabase();  // Initialize sales database

        loadSeats();
    }

    // Method to dynamically load seats into the GridPaneGridPane
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
                seatButton.setStyle("-fx-background-color: grey; -fx-padding: 10px;");

                seatButton.setOnAction(event -> handleSeatSelection(event, currentRow, currentCol));

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

        // sout the amount of selected seats
        System.out.println("Selected seats: " + selectedSeats.size());
        confirmSelectionBtn.setText("Sell " + selectedSeats.size() + " tickets");

        // Update the label to show selected seats
        updateSelectedSeatsLabel();

        System.out.println("Selected seats: " + selectedSeats);
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
        Sales sales = new Sales(customerNameField.getText(), movie, LocalDateTime.now(), selectedSeats);  // Passing null for the movie here

        // add to db
        salesDatabase.addSale(sales);

        // Print selected seats for confirmation
        System.out.println("Sales created with selected seats: " + sales.getSeats());
        System.out.println("Selected movie: confirm" + movie.getTitle());

        MovieTheaterApplication.getSceneController().changeScene("TicketSales", new Context(user, null, MovieTheaterApplication.getInMemoryDatabase()));

    }
}
