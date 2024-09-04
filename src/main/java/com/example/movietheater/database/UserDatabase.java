package com.example.movietheater.database;

import com.example.movietheater.model.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDatabase {
    private Map<String, User> users;

    public UserDatabase() {
        this.users = new HashMap<>();
    }

    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    public User getUserByUsername(String username) {
        return users.get(username);
    }
}
