module com.example.cleanfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;


    opens com.example.cleanfx to javafx.fxml;
    exports com.example.cleanfx;
}