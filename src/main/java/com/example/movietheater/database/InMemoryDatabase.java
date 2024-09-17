package com.example.movietheater.database;

import com.example.movietheater.model.Movie;
import com.example.movietheater.model.Role;
import com.example.movietheater.model.User;

import java.time.LocalDateTime;

public class InMemoryDatabase {
    private UserDatabase userDatabase;
    private MovieDatabase movieDatabase;
    private SalesDatabase salesDatabase;

    public InMemoryDatabase() {
        this.userDatabase = new UserDatabase();
        this.movieDatabase = new MovieDatabase();
        this.salesDatabase = new SalesDatabase();
        initializeData();
    }

    private void initializeData() {
        userDatabase.addUser(new User("admin", "john doe", "admin", Role.ADMIN));
        userDatabase.addUser(new User("management", "jane doe", "management", Role.MANAGEMENT));
        userDatabase.addUser(new User("sales", "martin doe", "sales", Role.SALES));

        movieDatabase.addMovie((new Movie(1, "The Hobbit", LocalDateTime.now(), LocalDateTime.now(), 72)));
    }

    public UserDatabase getUserDatabase() {
        return userDatabase;
    }

    public MovieDatabase getMovieDatabase() {
        return movieDatabase;
    }

    public SalesDatabase getSalesDatabase() {
        return salesDatabase;
    }
}
