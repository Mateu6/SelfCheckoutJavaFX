package com.project.javalab.mateuszliszewski_selfcheckout;

import com.project.javalab.mateuszliszewski_selfcheckout.connection.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.textfield.TextFields;

public class ShopManagerController {

    @FXML
    public AnchorPane background;
    @FXML
    private Scene shopManagerScene;

    @FXML
    private ComboBox<String> categoriesCB;

    @FXML
    private ContextMenu simpleSearchResults;

    @FXML
    private Label categoryLabel;
    @FXML
    private Label prodNameLabel;
    @FXML
    private Label manufacturerLabel;
    @FXML
    private Label amountLabel;
    @FXML
    private Label priceLabel;

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
    private Button editButton;
    @FXML
    private Button advancedSearchButton;

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
    private TextField searchField;

    @FXML
    private ObservableList<Products> products;

    @FXML
    private void idChangesListener() {
        AdminTableController adminTableController = productsTable.getSelectionModel().getSelectedItem();

        if (adminTableController.id != idInput.getText()) {
            editButton.setDisable(true);
        } else {
            editButton.setDisable(false);
        }
    }

    @FXML
    private void populateProductsTable() {

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        manufacturer.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        pricePerItem.setCellValueFactory(new PropertyValueFactory<>("pricePerItem"));

        TextField categoryInput = categoriesCB.getEditor();

        if (categoryInput.getText().isEmpty() || prodNameInput.getText().isEmpty() || manufacturerInput.getText().isEmpty() || amountInput.getText().isEmpty() || pricePerItemInput.getText().isEmpty()) {
            editButton.setDisable(true);
            deleteFromBase.setDisable(true);
        }


        ObservableList<AdminTableController> prodList = FXCollections.observableArrayList();

        try {
            ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();

            String query = "SELECT * FROM Products";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery(query);

            while (resultSet.next()) {
                prodList.add(new AdminTableController(resultSet.getString("id"), resultSet.getString("productName"), resultSet.getString("manufacturer"), resultSet.getString("category"), resultSet.getString("amount"), resultSet.getString("pricePerItem")));
            }

            preparedStatement.close();
            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        productsTable.setItems(prodList);
    }

    @FXML
    public void populateCategoriesComboBox() {

        if (categoriesCB.getItems().isEmpty()) {

            try {

                ConnectionClass connectionClass = new ConnectionClass();
                Connection connection = connectionClass.getConnection();

                String selectQuery = "SELECT DISTINCT category FROM Products";
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                ResultSet resultSet = preparedStatement.executeQuery(selectQuery);

                while (resultSet.next()) {
                    categoriesCB.getItems().addAll(resultSet.getString("category"));
                }

                preparedStatement.close();
                resultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    public void onAddButtonClick() {

        TextField categoryInput = categoriesCB.getEditor();

        if (categoryInput.getText().isEmpty() || prodNameInput.getText().isEmpty() || manufacturerInput.getText().isEmpty() || amountInput.getText().isEmpty() || pricePerItemInput.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Uwaga!");
            alert.setHeaderText("Puste pola formularza!");
            alert.setContentText("Należy uzupełnić każde pole przed dodaniem produktu do bazy danych.");
            alert.showAndWait();
        }

        try {

            ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();

            String insertQuery = "INSERT IGNORE INTO Products (id, productName, manufacturer, category, amount, pricePerItem) VALUES(?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, idInput.getText());
            preparedStatement.setString(2, prodNameInput.getText());
            preparedStatement.setString(3, manufacturerInput.getText());
            preparedStatement.setString(4, categoryInput.getText());
            preparedStatement.setString(5, amountInput.getText());
            preparedStatement.setString(6, pricePerItemInput.getText());
            preparedStatement.execute();

            preparedStatement.close();

            refreshTable.fire();

            idInput.clear();
            prodNameInput.clear();
            manufacturerInput.clear();
            categoriesCB.setValue("");
            amountInput.clear();
            pricePerItemInput.clear();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void deleteProduct() {
        int selectedIndex = productsTable.getSelectionModel().getSelectedIndex();
        AdminTableController adminTableController = productsTable.getSelectionModel().getSelectedItem();
        String selectedItem = adminTableController.getId();

        if (selectedIndex >= 0) {

            try {
                ConnectionClass connectionClass = new ConnectionClass();
                Connection connection = connectionClass.getConnection();
                String query1 = "DELETE FROM Products WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query1);
                preparedStatement.setString(1, selectedItem);
                preparedStatement.execute();
                productsTable.getItems().remove(selectedIndex);
                refreshTable.fire();
            } catch (SQLException e) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Błąd:");
                alert.setHeaderText("Nic nie zaznaczono.");
                alert.setContentText("Wiersz jest pusty lub nie zaznaczono żadnego wiersza tabeli do usunięcia.");
                alert.showAndWait();
            }
        }
    }


    @FXML
    public void confirmEdit() throws SQLException {
        TextField categoryInput = categoriesCB.getEditor();

        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();

        if(idInput.getText().isEmpty()){

        }
        else{
            String productTableUpdate = "UPDATE `Products` SET `productName` = ?, `manufacturer` = ?, `category` = ?, `amount` = ?, `pricePerItem` = ? WHERE `id` = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(productTableUpdate);

            preparedStatement.setString(1, prodNameInput.getText());
            preparedStatement.setString(2, manufacturerInput.getText());
            preparedStatement.setString(3, categoryInput.getText());
            preparedStatement.setString(4, amountInput.getText());
            preparedStatement.setString(5, pricePerItemInput.getText());
            preparedStatement.setString(6, idInput.getText());
            preparedStatement.executeUpdate();

            populateProductsTable();
        }
    }


    @FXML
    public void selectProduct() throws SQLException {


        AdminTableController adminTableController = productsTable.getSelectionModel().getSelectedItem();

        editButton.setDisable(false);
        deleteFromBase.setDisable(false);


        idInput.setText(adminTableController.id);
        prodNameInput.setText(adminTableController.productName);
        manufacturerInput.setText(adminTableController.manufacturer);
        categoriesCB.setValue(adminTableController.category);
        amountInput.setText(adminTableController.amount);
        pricePerItemInput.setText(adminTableController.pricePerItem);

        if (editButton.isPressed()) {
            confirmEdit();
        }
    }

    @FXML
    private void clearSelection() {
        productsTable.getSelectionModel().clearSelection();
        prodNameInput.clear();
        manufacturerInput.clear();
        idInput.clear();
        amountInput.clear();
        pricePerItemInput.clear();
        categoriesCB.setValue(null);

        deleteFromBase.setDisable(true);
        editButton.setDisable(true);
    }


    @FXML
    public void simpleSearchFromDB() throws SQLException {

        String searchPhrase = searchField.getText();

        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Products WHERE 1=1 AND productName LIKE ?");
        preparedStatement.setString(1, "%" + searchPhrase + "%");
        ResultSet resultSet = preparedStatement.executeQuery();

    }

}