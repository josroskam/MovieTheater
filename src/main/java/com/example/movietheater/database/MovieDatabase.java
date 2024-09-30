package com.example.movietheater.database;

import com.example.movietheater.model.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MovieDatabase implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Movie> movies;

    public MovieDatabase() {
        this.movies = new ArrayList<>();
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    public void removeMovie(Movie movie) {
        movies.remove(movie);
    }

    public ObservableList<Movie> getMovies() {
        return FXCollections.observableArrayList(movies);
    }

    public void updateMovie(Movie updatedMovie) {
        int index = -1;
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getId().equals(updatedMovie.getId())) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            movies.set(index, updatedMovie);
        } else {
            throw new IllegalArgumentException("Movie not found");
        }
    }

    public ObservableList<Movie> getUpcomingMovies() {
        List<Movie> upcomingMovies = new ArrayList<>();
        for (Movie movie : movies) {
            LocalDateTime now = LocalDateTime.now();
            if (movie.getStartTime().isAfter(now)) {
                upcomingMovies.add(movie);
            }
        }
        return FXCollections.observableArrayList(upcomingMovies);
    }
}
