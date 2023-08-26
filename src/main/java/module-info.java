module com.dev.taormina.app {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.dev.taormina.app to javafx.fxml;
    exports com.dev.taormina.app;
}