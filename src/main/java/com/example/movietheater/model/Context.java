package com.example.movietheater.model;

import com.example.movietheater.database.InMemoryDatabase;
import com.example.movietheater.model.Movie;
import com.example.movietheater.model.User;

public class Context {
    private User user;
    private Movie movie;
    private InMemoryDatabase inMemoryDatabase;

    public Context(User user, Movie movie, InMemoryDatabase inMemoryDatabase) {
        this.user = user;
        this.movie = movie;
        this.inMemoryDatabase = inMemoryDatabase;
    }

    public User getUser() {
        return user;
    }

    public Movie getMovie() {
        return movie;
    }

    public InMemoryDatabase getInMemoryDatabase() {
        return inMemoryDatabase;
    }
}