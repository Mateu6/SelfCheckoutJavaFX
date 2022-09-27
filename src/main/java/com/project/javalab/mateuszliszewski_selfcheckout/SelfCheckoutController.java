package com.project.javalab.mateuszliszewski_selfcheckout;

import com.mysql.cj.jdbc.JdbcConnection;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import com.mysql.cj.xdevapi.Table;
import com.project.javalab.mateuszliszewski_selfcheckout.connection.ConnectionClass;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class SelfCheckoutController {
    @FXML
    private Button start;
    @FXML
    private Button cartStarter;
    @FXML
    private Button adminButton;
    @FXML
    private Label scanner;
    @FXML
    private TextField loginText;
    @FXML
    private TextField passwdText;
    @FXML
    private Button loginButton;
    @FXML
    private Label loginLabel;
    @FXML
    private Label connectionIndicator;

    final String passwd = "admin";


    //tu jes kurde burdel
    @FXML
    public void onConnectionIndicatorClicked() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                    try {
                        ConnectionClass connectionClass = new ConnectionClass();
                        Connection connection = connectionClass.getConnection();
                        connection.isValid(500);

                    } catch (CommunicationsException e){

                        System.Logger logger = System.getLogger(getClass().getName());
                        logger.log(System.Logger.Level.INFO, "Pole ID klasy AdminTableController ma wartość null. | ", e);

                        connectionIndicator.setTextFill(Paint.valueOf("red"));
                        connectionIndicator.setText("Brak połączenia z bazą");
                        adminButton.setDisable(true);
                        start.setDisable(true);
                        scanner.setDisable(true);
                        cartStarter.setDisable(true);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                ConnectionClass connectionClass = new ConnectionClass();
                Connection connection = connectionClass.getConnection();

                try {
                    if (connection.isValid(500))
                            connectionIndicator.setTextFill(Paint.valueOf("green"));
                            connectionIndicator.setText("Połączono z bazą");
                            adminButton.setDisable(false);
                            start.setDisable(false);
                            scanner.setDisable(false);
                            cartStarter.setDisable(false);
                } catch (SQLException e) {
                    e.printStackTrace();
                }



            }
        }, 0, 10000);

    }

    @FXML
    public void onStartButtonClick() {

        Stage welcome = (Stage) start.getScene().getWindow();
        welcome.close();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("shopping-cart.fxml"));
            Scene cart = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Koszyk");
            stage.setScene(cart);
            stage.show();
            stage.setX(920);
        } catch (IOException e) {
            System.Logger logger = System.getLogger(getClass().getName());
            logger.log(System.Logger.Level.ERROR, "Failed to create Cart Window.", e);
        }


        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("main-scanner.fxml"));
            Scene mainScanner = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Kasa samoobsługowa");
            stage.setScene(mainScanner);
            stage.show();
            stage.setX(200);
        } catch (IOException e) {
            System.Logger logger = System.getLogger(getClass().getName());
            logger.log(System.Logger.Level.ERROR, "Failed to create Main Scanner Window.", e);
        }
    }

    @FXML
    public void onCartButtonClick() {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("shopping-cart.fxml"));
            Scene cart = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Koszyk");
            stage.setScene(cart);
            stage.show();
        } catch (IOException e) {
            System.Logger logger = System.getLogger(getClass().getName());
            logger.log(System.Logger.Level.ERROR, "Failed to create Cart Window.", e);
        }
    }

    @FXML
    public void onAdminButtonClick() {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("login-prompt.fxml"));
            Scene admin = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Logowanie administracji bazy danych");
            stage.setScene(admin);
            stage.show();
        } catch (IOException e) {
            System.Logger logger = System.getLogger(getClass().getName());
            logger.log(System.Logger.Level.ERROR, "Failed to create Admin Window.", e);
        }
    }

    @FXML
    public void onLoginButtonClick(ActionEvent event) {

        String userPassword = passwdText.getText();
        if (userPassword.equals(passwd)) {
            Stage login = (Stage) loginButton.getScene().getWindow();
            login.close();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("shop-manager.fxml"));
                Scene admin = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setTitle("Zarządzanie bazą produktów");
                stage.setScene(admin);
                stage.show();
            } catch (IOException e) {
                System.Logger logger = System.getLogger(getClass().getName());
                logger.log(System.Logger.Level.ERROR, "Failed to create Admin Window.", e);
            }
        } else {
            loginLabel.setText("Niewłaściwe hasło.");
            loginLabel.setTextFill(Paint.valueOf("red"));
        }
    }
}

