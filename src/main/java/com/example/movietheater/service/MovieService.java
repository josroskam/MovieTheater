package com.example.movietheater.service;

import com.example.movietheater.database.InMemoryDatabase;
import com.example.movietheater.model.Movie;
import javafx.collections.ObservableList;

public class MovieService{
    private InMemoryDatabase database;

    public MovieService(InMemoryDatabase database) {
        this.database = database;
    }

    public void updateMovie(Movie newMovie) {
        database.getMovieDatabase().updateMovie(newMovie);
    }

    public void addMovie(Movie newMovie) {
        database.getMovieDatabase().addMovie(newMovie);
    }

    public ObservableList<Movie> getUpcomingMovies() {
        return database.getMovieDatabase().getUpcomingMovies();
    }
}