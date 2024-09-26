package com.example.movietheater.controller;

import com.example.movietheater.MovieTheaterApplication;
import com.example.movietheater.database.SalesDatabase;
import com.example.movietheater.model.*;
import com.example.movietheater.service.SalesService;
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

    private static final String SELECTED_SEAT_COLOR = "lightgreen";
    private static final String AVAILABLE_SEAT_COLOR = "grey";
    private static final String SOLD_SEAT_COLOR = "red";

    @FXML
    private Label errorLabel;

    @FXML
    private GridPane seatGridPane;

    @FXML
    private Button confirmSelectionBtn;

    @FXML
    private Label selectedSeatsLabel;

    @FXML
    private TextField customerNameField;

    @FXML
    private AnchorPane navigationPane;

    @FXML
    private Label movieTitleLabel;

    private SalesService salesService;
    private List<Seat> selectedSeats = new ArrayList<>();
    private List<Seat> soldSeats = new ArrayList<>();

    private final int rows = 6;
    private final int cols = 12;

    private User user;
    private Movie movie;

    @Override
    public void initialize(Object data) {
        Context context = (Context) data;
        this.user = context.getUser();
        this.movie = context.getMovie();
        this.salesService = new SalesService(context.getInMemoryDatabase());

        movieTitleLabel.setText(movie.formatDateTime(movie.getStartTime()) + " " + movie.getTitle());
        loadNavigation(navigationPane, context);

        retrieveSoldSeats();
        loadSeats();
    }

    private void retrieveSoldSeats() {
        List<Sales> sales = salesService.getSalesByMovie(movie);
        sales.forEach(sale -> soldSeats.addAll(sale.getSeats()));
    }

    private void loadSeats() {
        for (int row = 0; row < rows; row++) {
            seatGridPane.add(createRowLabel(row), 0, row);

            for (int col = 0; col < cols; col++) {
                Seat seat = new Seat(row, col);
                Button seatButton = createSeatButton(seat);
                seatGridPane.add(seatButton, col + 1, row);  // Column +1 to skip the row label
            }
        }
    }

    private Label createRowLabel(int row) {
        Label rowLabel = new Label("Row " + (row + 1));
        rowLabel.setStyle("-fx-padding: 10px; -fx-font-weight: bold;");
        return rowLabel;
    }

    private Button createSeatButton(Seat seat) {
        Button seatButton = new Button(String.valueOf(seat.getColumn() + 1));  // Display numbers 1 to 12
        setButtonStyle(seatButton, seat);

        seatButton.setMinSize(40, 40);
        seatButton.setPrefSize(40, 40);
        seatButton.setMaxSize(40, 40);

        if (!soldSeats.contains(seat)) {
            seatButton.setOnAction(event -> toggleSeatSelection(seatButton, seat));
        } else {
            seatButton.setDisable(true);
        }

        return seatButton;
    }

    private void setButtonStyle(Button seatButton, Seat seat) {
        if (soldSeats.contains(seat)) {
            seatButton.setStyle("-fx-background-color: " + SOLD_SEAT_COLOR + ";");
        } else {
            seatButton.setStyle("-fx-background-color: " + AVAILABLE_SEAT_COLOR + ";");
        }
    }

    private void toggleSeatSelection(Button seatButton, Seat seat) {
        if (selectedSeats.contains(seat)) {
            seatButton.setStyle("-fx-background-color: " + AVAILABLE_SEAT_COLOR + ";");
            selectedSeats.remove(seat);
        } else {
            seatButton.setStyle("-fx-background-color: " + SELECTED_SEAT_COLOR + ";");
            selectedSeats.add(seat);
        }

        updateSelectedSeatsLabel();
        confirmSelectionBtn.setText("Sell " + selectedSeats.size() + " tickets");
    }

    private void updateSelectedSeatsLabel() {
        StringBuilder seatsText = new StringBuilder();
        selectedSeats.forEach(seat -> seatsText.append("Row ").append(seat.getRow() + 1).append(" / ").append(seat.getColumn() + 1).append("\n"));
        selectedSeatsLabel.setText(seatsText.toString());
    }

    public void confirmSelection() throws IOException {
        if (selectedSeats.isEmpty()) {
            showError("Please select at least one seat.");
            return;
        }

        if (customerNameField.getText().isEmpty()) {
            showError("Please enter customer name.");
            return;
        }

        Sales sales = new Sales(customerNameField.getText(), movie, LocalDateTime.now(), selectedSeats);
        salesService.addSale(sales);

        MovieTheaterApplication.getSceneController().changeScene("TicketSales", new Context(user, null, MovieTheaterApplication.getInMemoryDatabase()));
    }

    public void cancelSelection(ActionEvent event) throws IOException {
        MovieTheaterApplication.getSceneController().changeScene("TicketSales", new Context(user, null, MovieTheaterApplication.getInMemoryDatabase()));
    }

    private void showError(String message) {
        errorLabel.setText(message);
    }
}
