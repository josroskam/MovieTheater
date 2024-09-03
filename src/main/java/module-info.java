module com.example.movietheater {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.movietheater to javafx.fxml;
    exports com.example.movietheater;
}