module org.example.restaurant {
    requires javafx.controls;
    requires javafx.fxml;


    opens restaurant to javafx.fxml;
    exports restaurant;
    exports restaurant.model;
    opens restaurant.model to javafx.fxml;
}