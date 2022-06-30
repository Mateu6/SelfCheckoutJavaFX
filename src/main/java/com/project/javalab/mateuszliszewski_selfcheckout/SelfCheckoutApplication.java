package com.project.javalab.mateuszliszewski_selfcheckout;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SelfCheckoutApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SelfCheckoutApplication.class.getResource("welcome-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Kasa samoobsługowa");
        stage.setScene(scene);
        stage.show();

    }




    public static void main(String[] args) {
        launch();
    }
}