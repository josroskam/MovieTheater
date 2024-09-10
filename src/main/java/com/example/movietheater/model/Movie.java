package com.example.movietheater.model;

import java.time.LocalDateTime;

// movieShowing could be a better name
public class Movie {
    private Integer id;
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer seats;

    public Movie(Integer id, String title, LocalDateTime startTime, LocalDateTime endTime, Integer seats){
        this.id = id;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.seats = seats;
    }

    public Integer getId(){
        return this.id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getStartTime(){
        return this.formatDateTime(this.startTime);
    }

    public void setStartTime(LocalDateTime startTime){
        this.startTime = startTime;
    }

    public String getEndTime(){
        return this.formatDateTime(this.endTime);
    }

    public void setEndTime(LocalDateTime endTime){
        this.endTime = endTime;
    }

    public Integer getSeats(){
        return this.seats;
    }

    public void setSeats(Integer seats){
        this.seats = seats;
    }

    // to dd-hh-yyyy hh:mm (e.g.) 01-01-2021 12:00
    private String formatDateTime(LocalDateTime dateTime){
        return dateTime.getDayOfMonth() + "-" + dateTime.getMonthValue() + "-" + dateTime.getYear() + " " + dateTime.getHour() + ":" + dateTime.getMinute();
    }
}