module com.example.paskolos {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.opencsv;


    opens com.example.paskolos to javafx.fxml;
    exports com.example.paskolos;
}