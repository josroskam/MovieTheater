package com.example.movietheater.database;

import com.example.movietheater.model.Movie;
import com.example.movietheater.model.Role;
import com.example.movietheater.model.User;

import java.io.Serializable;
import java.time.LocalDateTime;

public class InMemoryDatabase implements Serializable {
    private static final long serialVersionUID = 1L;

    private MovieDatabase movieDatabase;
    private SalesDatabase salesDatabase;
    private UserDatabase userDatabase;

    public InMemoryDatabase() {
        this.movieDatabase = new MovieDatabase();
        this.salesDatabase = new SalesDatabase();
        this.userDatabase = new UserDatabase();
         initUsers();
    }

    private void initUsers(){
        User user4 = new User("mike_jones", "Mike Jones", "mikePassword2024", Role.MANAGEMENT);
        User user5 = new User("lisa_lee", "Lisa Lee", "lisaPass2024", Role.SALES);
        User user6 = new User("susan_wong", "Susan Wong", "susanPass2024", Role.ADMIN);
        User user7 = new User("admin", "Joe Smith", "admin", Role.SALES);

        userDatabase.addUser(user4);
        userDatabase.addUser(user5);
        userDatabase.addUser(user6);
        userDatabase.addUser(user7);

        Movie movie1 = new Movie( "Movie 1", LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2), 100);
        Movie movie2 = new Movie( "Movie 2", LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2), 100);

        movieDatabase.addMovie(movie1);
        movieDatabase.addMovie(movie2);

    }

    public MovieDatabase getMovieDatabase() {
        return movieDatabase;
    }

    public SalesDatabase getSalesDatabase() {
        return salesDatabase;
    }

    public UserDatabase getUserDatabase() {
        return userDatabase;
    }
}
