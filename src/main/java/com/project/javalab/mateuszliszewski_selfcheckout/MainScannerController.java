package com.project.javalab.mateuszliszewski_selfcheckout;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class MainScannerController {

    @FXML
    private Button adminMainButton;
    @FXML
    private Button editListButton;
    @FXML
    private Button showCartButton;



    @FXML
    public void onAdminMainButtonClick(){

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

}
