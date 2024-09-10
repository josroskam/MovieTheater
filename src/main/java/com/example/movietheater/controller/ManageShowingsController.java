package com.example.movietheater.controller;

import com.example.movietheater.MovieTheaterApplication;
import com.example.movietheater.database.InMemoryDatabase;
import com.example.movietheater.database.MovieDatabase;
import com.example.movietheater.model.Context;
import com.example.movietheater.model.Movie;
import com.example.movietheater.model.User;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.LocalDateTime;

// only visible to management
public class ManageShowingsController extends BaseController {
    private User user;

    @FXML
    private TableView<Movie> showingsTable;

    @FXML
    private TableColumn<Movie, LocalDateTime> startColumn;

    @FXML
    private TableColumn<Movie, LocalDateTime> endColumn;

    @FXML
    private TableColumn<Movie, String> titleColumn;

    @FXML
    private TableColumn<Movie, Integer> seatsColumn;

    @FXML
    private Button editShowing;

    @FXML
    private Button deleteShowing;

    private MovieDatabase movieDatabase;

    private ObservableList<Movie> movieList;
    private InMemoryDatabase database;

    public ManageShowingsController() {
        this.database = new InMemoryDatabase();
        this.movieList = FXCollections.observableArrayList();
    }

    @Override
    public void initData(Object data) {
        Context context = (Context) data;
        this.movieDatabase = context.getInMemoryDatabase().getMovieDatabase();
        this.user = context.getUser();

        System.out.println("ManageShowingsController initialized with user: " + user.getFullName());

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

        startColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        seatsColumn.setCellValueFactory(new PropertyValueFactory<>("seats"));

        showingsTable.setItems(movieList); // Ensure the table is bound to the shared list
    }


    // Add showing button
    @FXML
    public void addShowing(ActionEvent event) throws IOException {
        System.out.println("Opening SaveShowing.fxml without an object");

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

        System.out.println("Opening SaveShowing.fxml with an object");

        // Open the SaveShowing.fxml scene
        try {
            MovieTheaterApplication.getSceneController().changeScene("ManageShowings", new Context(user, null, MovieTheaterApplication.getInMemoryDatabase()));
        } catch (IOException e) {
            System.out.println("Error opening SaveShowing.fxml: " + e.getMessage());
        }
    }

    public void deleteShowing(ActionEvent event) {
        // Get the selected movie from the TableView
        Movie selectedMovie = showingsTable.getSelectionModel().getSelectedItem();

        // Check if a movie is selected
        if (selectedMovie == null) {
            System.out.println("No movie selected for editing.");
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

    // Navigation handlers
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