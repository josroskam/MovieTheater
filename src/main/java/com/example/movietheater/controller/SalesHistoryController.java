package com.example.movietheater.controller;
import com.example.movietheater.model.Context;
import com.example.movietheater.model.Sales;
import com.example.movietheater.model.User;
import com.example.movietheater.service.SalesService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class SalesHistoryController extends BaseController {

    @FXML
    private TableView<Sales> salesTableView;

    @FXML
    private TableColumn<Sales, String> dateTimeColumn;

    @FXML
    private TableColumn<Sales, String> ticketAmountColumn;

    @FXML
    private TableColumn<Sales, String> customerColumn;

    @FXML
    private TableColumn<Sales, String> showingColumn;

    private SalesService salesService;
    private ObservableList<Sales> salesList;

    @FXML
    private AnchorPane navigationPane;

    @Override
    public void initialize(Object data) {
        Context context = (Context) data;
        this.salesService = new SalesService(context.getInMemoryDatabase());

        loadNavigation(navigationPane, context);

        setupColumnWidths();
        populateTable();
    }

    private void setupColumnWidths(){
        salesTableView.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            dateTimeColumn.setPrefWidth(newWidth.doubleValue() * 0.20);
            ticketAmountColumn.setPrefWidth(newWidth.doubleValue() * 0.15);
            customerColumn.setPrefWidth(newWidth.doubleValue() * 0.25);
            showingColumn.setPrefWidth(newWidth.doubleValue() * 0.45);
        });
    }

    // Populates the TableView with initial data
    private void populateTable() {
        salesList = salesService.getAllSales();

        // Bind the columns to the Sales properties
        bindColumns();

        salesTableView.setItems(salesList);
    }

    // Method to bind TableColumn properties to Sales fields
    private void bindColumns() {
        dateTimeColumn.setCellValueFactory(sales ->
                new SimpleStringProperty(sales.getValue().formatDateTime(sales.getValue().getDateTime())));

        ticketAmountColumn.setCellValueFactory(sales ->
                new SimpleStringProperty(String.valueOf(sales.getValue().getSeats().size())));

        customerColumn.setCellValueFactory(sales ->
                new SimpleStringProperty(sales.getValue().getUsername()));

        showingColumn.setCellValueFactory(sales ->
                new SimpleStringProperty(sales.getValue().getMovie().getTitle() + " at " +
                        sales.getValue().getMovie().formatDateTime(sales.getValue().getMovie().getStartTime())));
    }
}


