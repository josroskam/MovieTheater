package com.example.movietheater.model;

import java.util.Dictionary;

public class Sales {
    private Integer id;
    private User user;
    private Movie movie;
    private Integer ticketAmount;
    private String dateTime;
    private Integer[][] seats;

    public Sales(Integer id, User user, Movie movie, Integer ticketAmount, String dateTime, Integer[][] seats){
        this.id = id;
        this.user = user;
        this.movie = movie;
        this.ticketAmount = ticketAmount;
        this.dateTime = dateTime;
        this.seats = seats;
    }

    public Integer getId(){
        return this.id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public User getUser(){
        return this.user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public Movie getMovie(){
        return this.movie;
    }

    public void setMovie(Movie movie){
        this.movie = movie;
    }

    public Integer getTicketAmount(){
        return this.ticketAmount;
    }

    public void setTicketAmount(Integer ticketAmount){
        this.ticketAmount = ticketAmount;
    }

    public String getDateTime(){
        return this.dateTime;
    }

    public void setDateTime(String dateTime){
        this.dateTime = dateTime;
    }

    public Integer[][] getSeats(){
        return this.seats;
    }

    public void setSeats(Integer[][] seats){
        this.seats = seats;
    }
}