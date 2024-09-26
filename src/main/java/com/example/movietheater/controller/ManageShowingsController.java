package com.example.movietheater.controller;

import com.example.movietheater.MovieTheaterApplication;
import com.example.movietheater.model.Context;
import com.example.movietheater.model.Movie;
import com.example.movietheater.model.User;
import com.example.movietheater.service.ShowingsService;
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

public class ManageShowingsController extends BaseController {

    private static final double COLUMN_WIDTH_RATIO = 0.25;

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

    @FXML
    private Label errorLabel;

    @FXML
    private AnchorPane navigationPane;

    private ShowingsService showingsService;
    private ObservableList<Movie> movieList;
    private User user;
    private Movie selectedMovie;

    @Override
    public void initialize(Object data) {
        Context context = (Context) data;
        this.showingsService = new ShowingsService(context.getInMemoryDatabase());
        this.user = context.getUser();

        loadNavigation(navigationPane, context);

        setupTableWidthListener();
        setupButtonBindings();

        // Initialize the data
        populateTable();
    }

    private void setupTableWidthListener() {
        showingsTable.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            double width = newWidth.doubleValue() * COLUMN_WIDTH_RATIO;
            startColumn.setPrefWidth(width);
            endColumn.setPrefWidth(width);
            titleColumn.setPrefWidth(width);
            seatsColumn.setPrefWidth(width);
        });
    }

    private void setupButtonBindings() {
        // Bind button disable properties to whether an item is selected
        editShowing.disableProperty().bind(Bindings.isNull(showingsTable.getSelectionModel().selectedItemProperty()));
        deleteShowing.disableProperty().bind(Bindings.isNull(showingsTable.getSelectionModel().selectedItemProperty()));
    }

    // Populates the TableView with initial data
    private void populateTable() {
        movieList = showingsService.getAllMovies();

        // Update seats column with "X/Y" format where X is remaining seats and Y is total seats
        seatsColumn.setCellValueFactory(movie -> {
            int totalSeats = movie.getValue().getSeats();
            int soldSeats = showingsService.getSeatsSoldForMovie(movie.getValue());
            String seatsLeft = movie.getValue().getRemainingSeats(soldSeats) + "/" + totalSeats;
            return new SimpleStringProperty(seatsLeft);
        });

        // Use formatted string for start and end times
        startColumn.setCellValueFactory(movie -> new SimpleStringProperty(movie.getValue().formatDateTime(movie.getValue().getStartTime())));
        endColumn.setCellValueFactory(movie -> new SimpleStringProperty(movie.getValue().formatDateTime(movie.getValue().getEndTime())));

        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        // Set the table's data
        showingsTable.setItems(movieList);
    }

    // Add showing button
    @FXML
    public void addShowing(ActionEvent event) throws IOException {
        MovieTheaterApplication.getSceneController().changeScene("SaveShowing", new Context(user, null, MovieTheaterApplication.getInMemoryDatabase()));
    }

    // Edit showing button
    @FXML
    public void editShowing(ActionEvent event) {
        selectedMovie = getSelectedMovie("No movie selected for editing.");

        if (selectedMovie == null) return;

        try {
            MovieTheaterApplication.getSceneController().changeScene("SaveShowing", new Context(user, selectedMovie, MovieTheaterApplication.getInMemoryDatabase()));
        } catch (IOException e) {
            showError("Something went wrong while editing the movie.");
        }

    }

    public void deleteShowing(ActionEvent event) {
        selectedMovie = getSelectedMovie("No movie selected for deletion.");

        if (selectedMovie == null) return;

        if (showingsService.hasSoldTickets(selectedMovie)) {
            showError("Cannot delete a movie that has already sold tickets.");
            return;
        }

        try {
            showingsService.deleteMovie(selectedMovie);
            movieList.remove(selectedMovie);
            clearError();
        } catch (Exception e) {
            showError("Something went wrong while deleting the movie");
        }

    }

    private Movie getSelectedMovie(String errorMessage) {
        selectedMovie = showingsTable.getSelectionModel().getSelectedItem();
        if (selectedMovie == null) {
            showError(errorMessage);
            return null;
        }
        return selectedMovie;
    }

    private void showError(String message) {
        errorLabel.setText(message);
    }

    private void clearError() {
        errorLabel.setText("");
    }
}
