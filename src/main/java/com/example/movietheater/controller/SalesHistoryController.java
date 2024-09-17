package com.example.movietheater.controller;

import com.example.movietheater.database.SalesDatabase;
import com.example.movietheater.model.Context;
import com.example.movietheater.model.Sales;
import com.example.movietheater.model.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDateTime;

public class SalesHistoryController extends BaseController {

    @FXML
    private TableView<Sales> salesTableView;  // The TableView for displaying sales history

    @FXML
    private TableColumn<Sales, String> dateTimeColumn;

    @FXML
    private TableColumn<Sales, Integer> ticketAmountColumn;

    @FXML
    private TableColumn<Sales, String> customerColumn;

    @FXML
    private TableColumn<Sales, String> showingColumn;

    private SalesDatabase salesDatabase;
    private ObservableList<Sales> salesList;
    private User user;

    @FXML
    private AnchorPane navigationPane;  // Reference to the pane where Navigation.fxml is included

    @Override
    public void initData(Object data) {
        Context context = (Context) data;
        this.salesDatabase = context.getInMemoryDatabase().getSalesDatabase();
        this.user = context.getUser();

        loadNavigation(navigationPane, context);
        populateTable();
    }

    // Populates the TableView with initial data
    private void populateTable() {
        // Fetch the sales data from the database
        salesList = FXCollections.observableArrayList(salesDatabase.getAllSales());

        // Bind the columns to the Sales properties
        dateTimeColumn.setCellValueFactory(sales -> new SimpleStringProperty(sales.getValue().getDateTime().toString()));
        ticketAmountColumn.setCellValueFactory(new PropertyValueFactory<>("ticketAmount"));
        customerColumn.setCellValueFactory(sales -> new SimpleStringProperty(sales.getValue().getUsername()));

        LocalDateTime startTime = salesList.get(0).getMovie().getStartTime();

        // use formatDateTime from movie class
        String title = salesList.get(0).getMovie().getTitle();

        // Bind the showing column to the movie title
        String showing = " (" + startTime + ")" + title;

        showingColumn.setCellValueFactory(sales -> new SimpleStringProperty(showing));

        // Set the data to the TableView
        salesTableView.setItems(salesList);

        // Optionally, print the sales for debugging purposes
        for (Sales sales : salesList) {
            System.out.println(sales);
        }
    }
}
