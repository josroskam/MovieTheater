package com.example.movietheater.database;

import com.example.movietheater.model.Movie;
import com.example.movietheater.model.Sales;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class SalesDatabase {
    // Use a List or ObservableList instead of a Map
    private List<Sales> sales;

    public SalesDatabase() {
        this.sales = new ArrayList<>();  // Initialize with an empty list
    }

    // Add a sale to the list
    public void addSale(Sales sale) {
        sales.add(sale);
    }

    // Return the sales as an ObservableList for TableView compatibility
    public ObservableList<Sales> getAllSales() {
        return FXCollections.observableArrayList(sales);  // Wrap the list into an ObservableList
    }

    public List<Sales> getSalesByMovie(Movie movie) {
        List<Sales> salesByMovie = new ArrayList<>();
        for (Sales sale : sales) {
            if (sale.getMovie().equals(movie)) {
                salesByMovie.add(sale);
            }
        }
        return salesByMovie;
    }

    public int getSeatsSoldForMovie(Movie movie) {
        List<Sales> salesList = getSalesByMovie(movie);
        int soldSeats = 0;
        for (Sales sale : salesList) {
            soldSeats += sale.getSeats().size();  // Assuming each sale has a list of sold seats
        }
        return soldSeats;
    }

}
