module com.example.tableview {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires org.apache.logging.log4j;


    opens com.example.tableview to javafx.fxml;
    exports com.example.tableview;

    opens com.example.tableview.controllers;
    exports com.example.tableview.controllers;
}