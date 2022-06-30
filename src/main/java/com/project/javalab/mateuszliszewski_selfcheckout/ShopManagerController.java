package com.project.javalab.mateuszliszewski_selfcheckout;

import com.project.javalab.mateuszliszewski_selfcheckout.connection.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShopManagerController {
    @FXML
    private ComboBox<String> categoriesCB;
    @FXML
    private TableView<AdminTableController> productsTable;
    @FXML
    private TableColumn<AdminTableController, String> idCol;
    @FXML
    private TableColumn<AdminTableController, String> prodName;
    @FXML
    private TableColumn<AdminTableController, String> manufacturer;
    @FXML
    private TableColumn<AdminTableController, String> category;
    @FXML
    private TableColumn<AdminTableController, String> amount;
    @FXML
    private TableColumn<AdminTableController, String> pricePerItem;
    @FXML
    private Button addToBase;
    @FXML
    private Button refreshTable;
    @FXML
    private Button deleteFromBase;
    @FXML
    private TextField prodNameInput;
    @FXML
    private TextField manufacturerInput;
    @FXML
    private TextField amountInput;
    @FXML
    private TextField pricePerItemInput;
    @FXML
    private TextField idInput;

    @FXML
    private ObservableList<Products> products;



    @FXML
    private void populateProductsTable() {

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        manufacturer.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        pricePerItem.setCellValueFactory(new PropertyValueFactory<>("pricePerItem"));

        ObservableList<AdminTableController> prodList = FXCollections.observableArrayList();

        try{
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();

        String query = "SELECT * FROM Products";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery(query);

        while(resultSet.next()) {
            prodList.add(new AdminTableController(resultSet.getString("id"), resultSet.getString("productName"),resultSet.getString("manufacturer"), resultSet.getString("category"), resultSet.getString("amount"), resultSet.getString("pricePerItem")));
        }

        preparedStatement.close();
        resultSet.close();
    }catch (Exception e) {
        e.printStackTrace();
    }
        productsTable.setItems(prodList);
}

    @FXML
    private void populateCategoriesComboBox() {

        categoriesCB.setEditable(true);
        ObservableList<String> categoryList = categoriesCB.getItems();

            try {

                ConnectionClass connectionClass = new ConnectionClass();
                Connection connection = connectionClass.getConnection();

                String query = "SELECT category FROM Products";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery(query);

                while (resultSet.next()) {
                    categoriesCB.getItems().addAll(resultSet.getString("category"));
                }

                preparedStatement.close();
                resultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

    }


    @FXML
    public void onAddButtonClick() {

        String selected = categoriesCB.getSelectionModel().getSelectedItem().toString();
        categoriesCB.getValue().toString();

        try {
            ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();

            String query2 = "INSERT INTO Products (id, productName, manufacturer, category, amount, pricePerItem) VALUES(?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query2);
            preparedStatement.setString(1, idInput.getText());
            preparedStatement.setString(2, prodNameInput.getText());
            preparedStatement.setString(3, manufacturerInput.getText());
            preparedStatement.setString(4, categoriesCB.getPromptText());
            preparedStatement.setString(5, amountInput.getText());
            preparedStatement.setString(6, pricePerItemInput.getText());
            preparedStatement.execute();

            preparedStatement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @FXML
    public void deleteProduct() {
        int selectedIndex = productsTable.getSelectionModel().getSelectedIndex();
        AdminTableController adminTableController = productsTable.getSelectionModel().getSelectedItem();
        String selectedItem = adminTableController.getId();

        if(selectedIndex >= 0){

            try {
                ConnectionClass connectionClass = new ConnectionClass();
                Connection connection = connectionClass.getConnection();
                String query1 = "DELETE FROM Products WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query1);
                preparedStatement.setString(1, selectedItem);
                preparedStatement.execute();
                productsTable.getItems().remove(selectedIndex);
            } catch (Exception e){
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Błąd:");
                alert.setHeaderText("Nic nie zaznaczono.");
                alert.setContentText("Wiersz jest pusty lub nie zaznaczono żadnego wiersza tabeli do usunięcia.");
                alert.showAndWait();
            }

        } else {

        }
    }
}

