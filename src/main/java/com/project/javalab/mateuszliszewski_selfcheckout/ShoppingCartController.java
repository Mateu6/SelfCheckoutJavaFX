package com.project.javalab.mateuszliszewski_selfcheckout;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.project.javalab.mateuszliszewski_selfcheckout.connection.ConnectionClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

public class ShoppingCartController {

    @FXML
    private ListView<String> cartList;
    @FXML
    private Button scanButton;
    @FXML
    private Button goShoppingButton;
    @FXML
    private Button deleteFromCartButton;
    @FXML
    private Button randomCartButton;




    private void getRandomItemsIntoCart(){
        try {
            ConnectionClass connectionClass = new ConnectionClass();
            String query = "SELECT * FROM Products";
            PreparedStatement preparedStatement = connectionClass.getConnection().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String current = resultSet.getString("productName");
                ObservableList<String> shoppingList = FXCollections.observableArrayList(current);
                cartList.getItems().addAll(shoppingList);
            }

            preparedStatement.close();
            resultSet.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void deleteFromCart(){

        String selectedCartProduct = cartList.getSelectionModel().getSelectedItem();

        if(selectedCartProduct == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Błąd:");
            alert.setHeaderText("Nic nie zaznaczono.");
            alert.setContentText("Wiersz jest pusty lub nie zaznaczono żadnego wiersza tabeli do usunięcia.");
            alert.showAndWait();

        } else {
            cartList.getItems().remove(selectedCartProduct);
        }
    }

    @FXML
    private void populateCartList(){

        if(cartList.getItems().isEmpty()){
            try{
                getRandomItemsIntoCart();
            } catch (Exception e){
                e.printStackTrace();
            }

        } else {

            Alert overwriteCart = new Alert(Alert.AlertType.CONFIRMATION);
            overwriteCart.setTitle("Możliwa utrata obecnego koszyka");
            overwriteCart.setHeaderText("Nastąpi wymiana koszyka na nowy.");
            overwriteCart.setContentText("Zastąpić?");

            ButtonType confirmButtonType = new ButtonType("Zastąp");
            ButtonType cancelButtonType = new ButtonType("Anuluj", ButtonBar.ButtonData.CANCEL_CLOSE);

            overwriteCart.getButtonTypes().setAll(confirmButtonType, cancelButtonType);

            Optional<ButtonType> result = overwriteCart.showAndWait();
            if (result.get() == confirmButtonType) {
                cartList.getItems().clear();
                try{
                    getRandomItemsIntoCart();
                } catch (Exception e){
                    e.printStackTrace();
                }

            } else {

            }
        }
    }
}
