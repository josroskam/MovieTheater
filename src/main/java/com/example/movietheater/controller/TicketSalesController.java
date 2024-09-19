package com.example.movietheater.controller;

import com.example.movietheater.MovieTheaterApplication;
import com.example.movietheater.database.MovieDatabase;
import com.example.movietheater.database.SalesDatabase;
import com.example.movietheater.model.Context;
import com.example.movietheater.model.Movie;
import com.example.movietheater.model.User;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.LocalDateTime;

public class TicketSalesController  extends BaseController {
    public Button selectSeatBtn;
    private User user;
    private MovieDatabase movieDatabase;
    private SalesDatabase salesDatabase;
    private ObservableList<Movie> movieList;

    @FXML
    private TableView<Movie> upcomingShowingsTable;

    @FXML
    private TableColumn<Movie, String> startColumn;

    @FXML
    private TableColumn<Movie, String> endColumn;

    @FXML
    private TableColumn<Movie, String> titleColumn;

    @FXML
    private TableColumn<Movie, String> seatsColumn;

    @FXML
    private Label selectedSeatLbl;

    private Movie selectedMovie;

    @FXML
    private AnchorPane navigationPane;  // Reference to the pane where Navigation.fxml is included

    @Override
    public void initData(Object data) {
        Context context = (Context) data;
        this.movieDatabase = context.getInMemoryDatabase().getMovieDatabase();
        this.salesDatabase = context.getInMemoryDatabase().getSalesDatabase();
        this.user = context.getUser();

        if (context == null) {
            System.out.println("Context is null");  // Log to help with debugging
            return;
        }

        loadNavigation(navigationPane, context);

        // Bind the button disable properties to whether an item is selected
        selectSeatBtn.disableProperty().bind(Bindings.isNull(upcomingShowingsTable.getSelectionModel().selectedItemProperty()));

        // Initialize the data
        populateTable();

        // Ensure each column takes up 25% of the TableView's width
        upcomingShowingsTable.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            startColumn.setPrefWidth(newWidth.doubleValue() * 0.25);
            endColumn.setPrefWidth(newWidth.doubleValue() * 0.25);
            titleColumn.setPrefWidth(newWidth.doubleValue() * 0.25);
            seatsColumn.setPrefWidth(newWidth.doubleValue() * 0.25);
        });

        // Add event listener for row click
        upcomingShowingsTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // single click
                selectedMovie = upcomingShowingsTable.getSelectionModel().getSelectedItem();
                if (selectedMovie != null) {
                    System.out.println("Selected movie: " + selectedMovie.getTitle());
                    handleSeatSelection(selectedMovie);
                }
            }
        });
    }

    private void handleSeatSelection(Movie selectedMovie) {
        // Handle the seat selection logic here
        System.out.println("Handling seat selection for movie: " + selectedMovie.getTitle());
        // Navigate to another scene or trigger seat selection
        String movieDetails = selectedMovie.formatDateTime(selectedMovie.getStartTime()) + " - " + selectedMovie.getTitle();
//        movie.getValue().formatDateTime(movie.getValue().getStartTime()
        selectedSeatLbl.setText("Selected movie: " + movieDetails);
    }

    // Populates the TableView with initial data
    private void populateTable() {
        // Get the shared ObservableList from the MovieDatabase
        movieList = movieDatabase.getMovies();

        // Use formatted string for start and end times
        startColumn.setCellValueFactory(movie -> new SimpleStringProperty(movie.getValue().formatDateTime(movie.getValue().getStartTime())));
        endColumn.setCellValueFactory(movie -> new SimpleStringProperty(movie.getValue().formatDateTime(movie.getValue().getEndTime())));

        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        // Update seats column with "X/Y" format where X is remaining seats and Y is total seats
        seatsColumn.setCellValueFactory(movie -> {
            int totalSeats = movie.getValue().getSeats();
            int soldSeats = salesDatabase.getSeatsSoldForMovie(movie.getValue());  // Assuming you have a method to get sold seats
            String seatsLeft = movie.getValue().getRemainingSeats(soldSeats) + "/" + totalSeats;
            return new SimpleStringProperty(seatsLeft);
        });

        upcomingShowingsTable.setItems(movieList);
    }

    public void selectSeats(ActionEvent event) throws IOException {
        MovieTheaterApplication.getSceneController().changeScene("SelectSeat", new Context(user, selectedMovie, MovieTheaterApplication.getInMemoryDatabase()));
    }
}
