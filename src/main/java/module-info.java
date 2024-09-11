module com.example.movietheater {

    opens com.example.movietheater.controller to javafx.fxml;

    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.compiler;
    requires java.desktop;

    opens com.example.movietheater.model to javafx.base;


    opens com.example.movietheater to javafx.fxml;
    exports com.example.movietheater;
    exports com.example.movietheater.controller;
}