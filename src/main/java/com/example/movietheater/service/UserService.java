package com.example.movietheater.service;

import com.example.movietheater.database.InMemoryDatabase;
import com.example.movietheater.model.User;

public class UserService {

    private InMemoryDatabase database;

    public UserService(InMemoryDatabase database) {
        this.database = database;
    }

    public User validateUser(String username, String password) {
        User user = database.getUserDatabase().getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
