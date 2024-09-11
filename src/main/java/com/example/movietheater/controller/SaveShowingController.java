package com.example.movietheater.controller;

import com.example.movietheater.MovieTheaterApplication;
import com.example.movietheater.database.MovieDatabase;
import com.example.movietheater.model.Context;
import com.example.movietheater.model.Movie;
import com.example.movietheater.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.LocalDateStringConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class SaveShowingController extends  BaseController {
    private User user;
    private Movie movie;
    private MovieDatabase movieDatabase;

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
    private Button cancelSaving;

    private Boolean isEditing = false;

    @FXML
    private AnchorPane navigationPane;  // Reference to the pane where Navigation.fxml is included

    @Override
    public void initData(Object data) {
        Context context = (Context) data;
        this.user = context.getUser();
        this.movie = context.getMovie();
        this.movieDatabase = context.getInMemoryDatabase().getMovieDatabase();

        loadNavigation(navigationPane, context);

        if (movie != null) {
            System.out.println("Is editing movie: " + movie);
            isEditing = true;
            editMovie();
        } else {
            createMovie();
        }
    }

    private void editMovie() {
        heading.setText("Creating a new movie");
        saveShowing.setText("Edit movie");
        title.setText(movie.getTitle());

        // Set LocalDate to DatePicker and LocalTime to time fields
        startDate.setValue(movie.getStartTime().toLocalDate());
        endDate.setValue(movie.getEndTime().toLocalDate());

        // Set the time values
        startTime.setText(movie.getStartTime().toLocalTime().toString());
        endTime.setText(movie.getEndTime().toLocalTime().toString());
    }

    private void createMovie() {
        heading.setText("Creating a new movie");
        saveShowing.setText("Save showing");
    }

    public void cancelSaving(ActionEvent event) throws IOException {
        MovieTheaterApplication.getSceneController().changeScene("ManageShowings", new Context(user, null, MovieTheaterApplication.getInMemoryDatabase()));
    }

    public void saveShowing(ActionEvent event) throws IOException {
        Movie newMovie = readMovie();

        // check if boolean isEditing is true
        if (isEditing) {
            // update the movie in the database
            movieDatabase.updateMovie(newMovie);
            System.out.println("Movie updated in database: " + newMovie);
        } else {
            // add the movie to the database
            movieDatabase.addMovie(newMovie);
            System.out.println("Movie added to database: " + newMovie);
        }
    }

    // Get the values from the form
    private Movie readMovie() {
        Integer id = movie != null ? movie.getId() : null;
        String title = this.title.getText();

        String startDate = this.startDate.getValue().toString();
        String startTime = this.startTime.getText();

        LocalDateTime startDateTime = LocalDateTime.parse(startDate + "T" + startTime);

        String endDate = this.endDate.getValue().toString();
        String endTime = this.endTime.getText();

        LocalDateTime endDateTime = LocalDateTime.parse(endDate + "T" + endTime);

        return new Movie(id, title, startDateTime, endDateTime, 100);
    }
}
