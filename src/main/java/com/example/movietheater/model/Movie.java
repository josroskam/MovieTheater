package com.example.movietheater.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

public class Movie implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer seats;

    public Movie(String title, LocalDateTime startTime, LocalDateTime endTime, Integer seats){
        this.id = UUID.randomUUID().toString();  // Generates a unique UUID        ;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.seats = seats;
    }

    public String getId(){
        return this.id;
    }

    public String getTitle(){
        return this.title;
    }

    public LocalDateTime  getStartTime(){
        return this.startTime;
    }

    public LocalDateTime  getEndTime(){
        return this.endTime;
    }

    public Integer getSeats(){
        return this.seats;
    }

    public int getRemainingSeats(int soldSeats) {
        return this.seats - soldSeats;  // Calculate remaining seats
    }


    public String formatDateTime(LocalDateTime dateTime) {
        // Create a formatter with the pattern "dd-MM-yyyy HH:mm"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        return dateTime.format(formatter);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(id, movie.id);  // Compare based on unique 'id'
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);  // Use 'id' for generating hashcode
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartTime(LocalDateTime startDateTime) {
        this.startTime = startDateTime;
    }

    public void setEndTime(LocalDateTime endDateTime) {
        this.endTime = endDateTime;
    }
}