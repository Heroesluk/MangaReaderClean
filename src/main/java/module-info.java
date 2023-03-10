module com.example.cleanfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cleanfx to javafx.fxml;
    exports com.example.cleanfx;
}