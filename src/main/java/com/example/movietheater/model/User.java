package com.example.movietheater.model;

public class User
{
    private String username;
    private String fullName;
    private String password;
    private Role role;

    public User(String username, String fullName, String password, Role role) {
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.role = role;
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
