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
        // initUsers();
    }

//    private void initUsers(){
//        User user4 = new User("mike_jones", "Mike Jones", "mikePassword2024", Role.MANAGEMENT);
//        User user5 = new User("lisa_lee", "Lisa Lee", "lisaPass2024", Role.SALES);
//        User user6 = new User("susan_wong", "Susan Wong", "susanPass2024", Role.ADMIN);
//
//        userDatabase.addUser(user4);
//        userDatabase.addUser(user5);
//        userDatabase.addUser(user6);
//    }

    public MovieDatabase getMovieDatabase() {
        return movieDatabase;
    }

    public SalesDatabase getSalesDatabase() {
        return salesDatabase;
    }

    public UserDatabase getUserDatabase() {
        return userDatabase;
    }

    public void setMovieDatabase(MovieDatabase movieDatabase) {
        this.movieDatabase = movieDatabase;
    }

    public void setSalesDatabase(SalesDatabase salesDatabase) {
        this.salesDatabase = salesDatabase;
    }

    public void setUserDatabase(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }
}
