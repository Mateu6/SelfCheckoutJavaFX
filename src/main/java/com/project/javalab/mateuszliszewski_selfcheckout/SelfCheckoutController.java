package com.project.javalab.mateuszliszewski_selfcheckout;

import com.mysql.cj.xdevapi.Table;
import com.project.javalab.mateuszliszewski_selfcheckout.connection.ConnectionClass;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.util.Objects;

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

    final String passwd = "admin";


    @FXML
    public void onStartButtonClick() throws IOException {

        Stage welcome = (Stage) start.getScene().getWindow();
        welcome.close();
        String connectionCartFill = "SELECT * FROM Products";

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("main-scanner.fxml"));
            Scene mainScanner = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Kasa samoobsługowa");
            stage.setScene(mainScanner);
            stage.show();
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
                }
                catch(IOException e){
                    System.Logger logger = System.getLogger(getClass().getName());
                    logger.log(System.Logger.Level.ERROR, "Failed to create Cart Window.", e);
            }
    }

    @FXML
    public void onAdminButtonClick(){

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("login-prompt.fxml"));
            Scene admin = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Logowanie administracji bazy danych");
            stage.setScene(admin);
            stage.show();
        }
        catch(IOException e){
            System.Logger logger = System.getLogger(getClass().getName());
            logger.log(System.Logger.Level.ERROR, "Failed to create Admin Window.", e);
        }
    }

    @FXML
    public void onLoginButtonClick(ActionEvent event){

        String userPassword = passwdText.getText();
        if(userPassword.equals(passwd)) {
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

