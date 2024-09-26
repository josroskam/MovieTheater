package com.example.movietheater.database;

import com.example.movietheater.model.Movie;
import com.example.movietheater.model.Sales;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SalesDatabase implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Sales> sales;

    public SalesDatabase() {
        this.sales = new ArrayList<>();
    }

    public void addSale(Sales sale) {
        sales.add(sale);
    }

    public ObservableList<Sales> getAllSales() {
        return FXCollections.observableArrayList(sales);
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
