package com.example.movietheater.service;
import com.example.movietheater.database.InMemoryDatabase;
import com.example.movietheater.model.Movie;
import com.example.movietheater.model.Sales;
import javafx.collections.ObservableList;

import java.util.List;

public class SalesService {

    private InMemoryDatabase database;

    public SalesService(InMemoryDatabase database) {
        this.database = database;
    }

    public ObservableList<Sales> getAllSales() {
        return database.getSalesDatabase().getAllSales();
    }

    public List<Sales> getSalesByMovie(Movie movie) {
        return database.getSalesDatabase().getSalesByMovie(movie);
    }

    public void addSale(Sales sales) {
        database.getSalesDatabase().addSale(sales);
    }

    public int getSeatsSoldForMovie(Movie value) {
        return database.getSalesDatabase().getSeatsSoldForMovie(value);
    }
}
