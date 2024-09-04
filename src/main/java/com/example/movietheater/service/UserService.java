package com.example.movietheater.service;

import com.example.movietheater.database.UserDatabase;
import com.example.movietheater.model.User;

public class UserService {
    private UserDatabase userDatabase;

    public UserService() {
        userDatabase = new UserDatabase();
    }

    public User getUserByUsername(String username) {
        return userDatabase.getUserByUsername(username);
    }
}