package com.example.movietheater.database;

import com.example.movietheater.model.Role;
import com.example.movietheater.model.User;

public class InMemoryDatabase {
    private UserDatabase userDatabase;

    public InMemoryDatabase() {
        this.userDatabase = new UserDatabase();
        initializeData();
    }

    private void initializeData() {
        userDatabase.addUser(new User("admin", "Admin Fullname", "admin", Role.ADMIN));
    }

    public UserDatabase getUserDatabase() {
        return userDatabase;
    }
}
