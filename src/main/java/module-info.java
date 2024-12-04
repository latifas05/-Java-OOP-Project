module org.example.petadoplati {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.petadoplati to javafx.fxml;
    exports org.example.petadoplati;
}