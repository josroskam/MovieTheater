module com.example.movietheater {
    exports com.example.movietheater.controller to javafx.fxml;

    opens com.example.movietheater.controller to javafx.fxml;

    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.compiler;


    opens com.example.movietheater to javafx.fxml;
    exports com.example.movietheater;
}