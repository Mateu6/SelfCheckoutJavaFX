package com.project.javalab.mateuszliszewski_selfcheckout.connection;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;


public class ConnectionClass {

    public Connection connection;

    public Connection getConnection() {

        String dbName = "SelfCheckoutDB";
        String userName = "root";
        String password = "";
        String url = "jdbc:mysql://localhost/" + dbName;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url);

        }catch (Exception e){
            e.printStackTrace();

            Alert connectionAlert = new Alert(Alert.AlertType.WARNING);
            connectionAlert.setTitle("Ostrzeżenie!");
            connectionAlert.setHeaderText("Brak połączenia z bazą danych!");
            connectionAlert.setContentText("Upewnij się, że istnieje stabilne połączenie z serwerem bazy danych.");
            connectionAlert.showAndWait();
        }

        return connection;
    }
}
