package com.example.movietheater.controller;

import com.example.movietheater.MovieTheaterApplication;
import com.example.movietheater.database.InMemoryDatabase;
import com.example.movietheater.database.MovieDatabase;
import com.example.movietheater.database.SalesDatabase;
import com.example.movietheater.model.Context;
import com.example.movietheater.model.Movie;
import com.example.movietheater.model.User;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.LocalDateTime;

// only visible to management
public class ManageShowingsController extends BaseController {
    private User user;

    @FXML
    private TableView<Movie> showingsTable;

    @FXML
    private TableColumn<Movie, String> startColumn;

    @FXML
    private TableColumn<Movie, String> endColumn;

    @FXML
    private TableColumn<Movie, String> titleColumn;

    @FXML
    private TableColumn<Movie, String> seatsColumn;

    @FXML
    private Button editShowing;

    @FXML
    private Button deleteShowing;

    private MovieDatabase movieDatabase;
    private SalesDatabase salesDatabase;
    private ObservableList<Movie> movieList;
    private InMemoryDatabase database;

    @FXML
    private AnchorPane navigationPane;  // Reference to the pane where Navigation.fxml is included

    public ManageShowingsController() {
        this.movieList = FXCollections.observableArrayList();
    }

    @Override
    public void initData(Object data) {
        // Call the common method from BaseController to load navigation
        Context context = (Context) data;
        this.movieDatabase = context.getInMemoryDatabase().getMovieDatabase();
        this.salesDatabase = context.getInMemoryDatabase().getSalesDatabase();
        this.user = context.getUser();
        this.database = context.getInMemoryDatabase();

        loadNavigation(navigationPane, context);

        // Ensure each column takes up 25% of the TableView's width
        showingsTable.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            startColumn.setPrefWidth(newWidth.doubleValue() * 0.25);
            endColumn.setPrefWidth(newWidth.doubleValue() * 0.25);
            titleColumn.setPrefWidth(newWidth.doubleValue() * 0.25);
            seatsColumn.setPrefWidth(newWidth.doubleValue() * 0.25);
        });

        // Bind the button disable properties to whether an item is selected
        editShowing.disableProperty().bind(Bindings.isNull(showingsTable.getSelectionModel().selectedItemProperty()));
        deleteShowing.disableProperty().bind(Bindings.isNull(showingsTable.getSelectionModel().selectedItemProperty()));

        // Initialize the data
        populateTable();
    }

    // Populates the TableView with initial data
    private void populateTable() {
        // Get the shared ObservableList from the MovieDatabase
        movieList = movieDatabase.getMovies();

        // Update seats column with "X/Y" format where X is remaining seats and Y is total seats
        seatsColumn.setCellValueFactory(movie -> {
            int totalSeats = movie.getValue().getSeats();
            int soldSeats = salesDatabase.getSeatsSoldForMovie(movie.getValue());  // Assuming you have a method to get sold seats
            String seatsLeft = movie.getValue().getRemainingSeats(soldSeats) + "/" + totalSeats;
            return new SimpleStringProperty(seatsLeft);
        });

        // Use formatted string for start and end times
        startColumn.setCellValueFactory(movie -> new SimpleStringProperty(movie.getValue().formatDateTime(movie.getValue().getStartTime())));
        endColumn.setCellValueFactory(movie -> new SimpleStringProperty(movie.getValue().formatDateTime(movie.getValue().getEndTime())));

        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
//        seatsColumn.setCellValueFactory(new PropertyValueFactory<>("seats"));

        // Set the table's data
        showingsTable.setItems(movieList);
    }


    // Add showing button
    @FXML
    public void addShowing(ActionEvent event) throws IOException {
        // Pass the shared MovieDatabase, User, and null for Movie (new showing)
        MovieTheaterApplication.getSceneController().changeScene("SaveShowing", new Context(user, null, MovieTheaterApplication.getInMemoryDatabase()));
    }

    // Edit showing button
    public void editShowing(ActionEvent event) {
        // Get the selected movie from the TableView
        Movie selectedMovie = showingsTable.getSelectionModel().getSelectedItem();

        // Check if a movie is selected
        if (selectedMovie == null) {
            System.out.println("No movie selected for editing.");
            return;
        }

        // Open the SaveShowing.fxml scene
        try {
            MovieTheaterApplication.getSceneController().changeScene("SaveShowing", new Context(user, selectedMovie, MovieTheaterApplication.getInMemoryDatabase()));
        } catch (IOException e) {
            System.out.println("Error opening SaveShowing.fxml: " + e.getMessage());
        }
    }

    public void deleteShowing(ActionEvent event) {
        Movie selectedMovie = showingsTable.getSelectionModel().getSelectedItem();

        if (selectedMovie == null) {
            System.out.println("No movie selected for deletion.");
            return;
        }

        try {
            // Remove the selected movie from the database
            database.getMovieDatabase().removeMovie(selectedMovie);

            // Refresh the TableView
            populateTable();

            System.out.println("Movie deleted successfully.");
        } catch (Exception e) {
            System.out.println("Error deleting movie: " + e.getMessage());
        }
    }
}