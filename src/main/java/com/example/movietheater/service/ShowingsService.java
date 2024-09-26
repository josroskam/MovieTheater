package com.example.movietheater.service;
import com.example.movietheater.database.InMemoryDatabase;
import com.example.movietheater.model.Movie;
import javafx.collections.ObservableList;

public class ShowingsService {
    private InMemoryDatabase database;

    public ShowingsService(InMemoryDatabase database) {
        this.database = database;
    }

    public ObservableList<Movie> getAllMovies() {
        return database.getMovieDatabase().getMovies();
    }

    public int getSeatsSoldForMovie(Movie movie) {
        return database.getSalesDatabase().getSeatsSoldForMovie(movie);
    }

    public boolean hasSoldTickets(Movie selectedMovie) {
        return getSeatsSoldForMovie(selectedMovie) > 0;
    }

    public void deleteMovie(Movie selectedMovie) {
        database.getMovieDatabase().removeMovie(selectedMovie);
    }
}
