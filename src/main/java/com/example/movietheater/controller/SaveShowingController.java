package com.example.movietheater.controller;

import com.example.movietheater.MovieTheaterApplication;
import com.example.movietheater.model.Context;
import com.example.movietheater.model.Movie;
import com.example.movietheater.model.User;
import com.example.movietheater.service.MovieService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class SaveShowingController extends BaseController {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    @FXML
    private Label heading;

    @FXML
    private TextField title;

    @FXML
    private TextField startTime;

    @FXML
    private TextField endTime;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private Button saveShowing;

    @FXML
    private Label errorLabel;

    @FXML
    private AnchorPane navigationPane;

    private Boolean isEditing = false;
    private User user;
    private Movie movie;
    private MovieService movieService;

    private final Integer TOTAL_SEATS = 72;

    @Override
    public void initialize(Object data) {
        Context context = (Context) data;
        this.user = context.getUser();
        this.movie = context.getMovie();
        this.movieService = new MovieService(context.getInMemoryDatabase());

        loadNavigation(navigationPane, context);

        Platform.runLater(() -> title.requestFocus());

        // Disable the text input for the DatePicker
        startDate.getEditor().setDisable(true);
        endDate.getEditor().setDisable(true);


        if (movie != null) {
            isEditing = true;
            setupForEditing();
        } else {
            setupForNewMovie();
        }
    }

    private void setupForEditing() {
        heading.setText("Editing a movie");
        saveShowing.setText("Edit movie");
        title.setText(movie.getTitle());

        startDate.setValue(movie.getStartTime().toLocalDate());
        endDate.setValue(movie.getEndTime().toLocalDate());
        startTime.setText(movie.getStartTime().toLocalTime().format(TIME_FORMATTER));
        endTime.setText(movie.getEndTime().toLocalTime().format(TIME_FORMATTER));
    }

    private void setupForNewMovie() {
        heading.setText("Creating a new movie");
        saveShowing.setText("Save movie");
    }

    public void cancelSaving(ActionEvent event) throws IOException {
        navigateToManageShowings();
    }

    public void saveShowing(ActionEvent event) throws IOException {
        try {
            Movie newMovie = readMovieInputs();
            if (newMovie == null) {
                return;
            }

            if (isEditing) {
                movieService.updateMovie(newMovie);
            } else {
                movieService.addMovie(newMovie);
            }
            navigateToManageShowings();

        } catch (DateTimeParseException e) {
            showError("Invalid date or time format. Use 'HH:mm' for time.");
        } catch (IllegalArgumentException e) {
            showError("Something went wrong while saving the movie.");
        }
    }

    private Movie readMovieInputs() {
        String title = this.title.getText();

        if (title.isEmpty()) {
            showError("Title cannot be empty.");
            return null;
        }

        LocalDateTime startDateTime = parseDateTime(startDate.getValue(), startTime.getText());
        LocalDateTime endDateTime = parseDateTime(endDate.getValue(), endTime.getText());

        if (startDateTime == null || endDateTime == null) {
            return null;
        }

        if (startDateTime.isAfter(endDateTime)) {
            showError("End time must be after start time.");
            return null;
        }

        if (isEditing) {
            movie.setTitle(title);
            movie.setStartTime(startDateTime);
            movie.setEndTime(endDateTime);
            return movie;
        }

        return new Movie(title, startDateTime, endDateTime, TOTAL_SEATS); // Default seat number to 72
    }

    // Validation for date and time fields
    private LocalDateTime parseDateTime(LocalDate date, String time) {
        if (date == null) {
            showError("Date must be selected.");
            return null;
        }

        if (time.isEmpty()) {
            showError("Time must be provided.");
            return null;
        }

        try {
            return LocalDateTime.parse(date.toString() + "T" + time);
        } catch (DateTimeParseException e) {
            showError("Invalid time format. Please use 'HH:mm'.");
            return null;
        }
    }

    private void navigateToManageShowings() throws IOException {
        MovieTheaterApplication.getSceneController().changeScene("ManageShowings", new Context(user, null, MovieTheaterApplication.getInMemoryDatabase()));
    }

    private void showError(String message) {
        errorLabel.setText(message);
    }
}
