package com.example.movietheater.controller;
import com.example.movietheater.MovieTheaterApplication;
import com.example.movietheater.model.Context;
import com.example.movietheater.model.Movie;
import com.example.movietheater.model.User;
import com.example.movietheater.service.MovieService;
import com.example.movietheater.service.SalesService;
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

public class TicketSalesController extends BaseController {

    @FXML
    private Button selectSeatBtn;

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

    @FXML
    private AnchorPane navigationPane;

    private ObservableList<Movie> movieList;
    private Movie selectedMovie;
    private User user;

    private MovieService movieService;
    private SalesService salesService;

    @Override
    public void initialize(Object data) {
        Context context = (Context) data;
        this.user = context.getUser();
        this.movieService = new MovieService(context.getInMemoryDatabase());
        this.salesService = new SalesService(context.getInMemoryDatabase());

        loadNavigation(navigationPane, context);

        // Bind button disable properties based on the selection state
        selectSeatBtn.disableProperty().bind(Bindings.isNull(upcomingShowingsTable.getSelectionModel().selectedItemProperty()));

        setupTableColumns();
        populateTable();

        upcomingShowingsTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                selectedMovie = upcomingShowingsTable.getSelectionModel().getSelectedItem();
                if (selectedMovie != null) {
                    handleSeatSelection(selectedMovie);
                }
            }
        });
    }

    private void setupTableColumns() {
        // Ensure each column takes up 25% of the TableView's width
        upcomingShowingsTable.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            double columnWidth = newWidth.doubleValue() * 0.25;
            startColumn.setPrefWidth(columnWidth);
            endColumn.setPrefWidth(columnWidth);
            titleColumn.setPrefWidth(columnWidth);
            seatsColumn.setPrefWidth(columnWidth);
        });

        // Set up columns with data bindings
        startColumn.setCellValueFactory(movie -> new SimpleStringProperty(movie.getValue().formatDateTime(movie.getValue().getStartTime())));
        endColumn.setCellValueFactory(movie -> new SimpleStringProperty(movie.getValue().formatDateTime(movie.getValue().getEndTime())));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        seatsColumn.setCellValueFactory(movie -> {
            int totalSeats = movie.getValue().getSeats();
            int soldSeats = salesService.getSeatsSoldForMovie(movie.getValue());
            String seatsLeft = movie.getValue().getRemainingSeats(soldSeats) + "/" + totalSeats;
            return new SimpleStringProperty(seatsLeft);
        });
    }

    // Populates the TableView with movies ordered by start time
    private void populateTable() {
        movieList = movieService.getUpcomingMovies();
        movieList.sort((m1, m2) -> m1.getStartTime().compareTo(m2.getStartTime()));
        upcomingShowingsTable.setItems(movieList);
    }

    private void handleSeatSelection(Movie selectedMovie) {
        String movieDetails = selectedMovie.formatDateTime(selectedMovie.getStartTime()) + " - " + selectedMovie.getTitle();
        selectedSeatLbl.setText("Selected movie: " + movieDetails);
    }

    @FXML
    public void selectSeats(ActionEvent event) throws IOException {
        if (selectedMovie == null) {
            showError("No movie selected for seat selection.");
            return;
        }

        MovieTheaterApplication.getSceneController().changeScene("SelectSeat", new Context(user, selectedMovie, MovieTheaterApplication.getInMemoryDatabase()));
    }

    private void showError(String message) {
        selectedSeatLbl.setText(message);
    }
}
