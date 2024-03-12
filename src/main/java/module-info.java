module com.example.interfaceonduleurv {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.net.http;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires jssc;


    opens com.example.interfaceonduleurv0 to javafx.fxml;
    exports com.example.interfaceonduleurv0;
}