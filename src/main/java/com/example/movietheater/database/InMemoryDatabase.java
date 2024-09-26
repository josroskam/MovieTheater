package com.example.movietheater.database;
import com.example.movietheater.model.Role;
import com.example.movietheater.model.User;
import java.io.Serializable;

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

        userDatabase.addUser(user4);
        userDatabase.addUser(user5);
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
