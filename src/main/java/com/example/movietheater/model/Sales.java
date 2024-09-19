package com.example.movietheater.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class Sales {
    private String id;
    private String username;
    private Movie movie;
    private LocalDateTime dateTime;  // Use LocalDateTime for better date handling
    private List<Seat> seats;  // List of seat coordinates instead of 2D array

    public Sales(String username, Movie movie, LocalDateTime dateTime, List<Seat> seats){
        this.id = UUID.randomUUID().toString();  // Generates a unique UUID        ;
        this.username = username;
        this.movie = movie;
        this.dateTime = dateTime;
        this.seats = seats;
    }

    public String getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Movie getMovie() {
        return this.movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public List<Seat> getSeats() {
        return this.seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    // Derived property: total ticket amount is the size of the seats list
    public int getTicketAmount() {
        return this.seats.size();
    }

    public String formatDateTime(LocalDateTime dateTime) {
        // Create a formatter with the pattern "dd-MM-yyyy HH:mm"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        return dateTime.format(formatter);
    }
}
