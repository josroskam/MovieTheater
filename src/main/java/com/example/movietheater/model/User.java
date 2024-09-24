package com.example.movietheater.model;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String id;
    private String username;
    private String fullName;
    private String password;
    private Role role;

    public User(String username, String fullName, String password, Role role) {
        this.id = UUID.randomUUID().toString();  // Generates a unique UUID        ;
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }
}
