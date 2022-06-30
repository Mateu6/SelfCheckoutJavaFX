package com.project.javalab.mateuszliszewski_selfcheckout;

import java.sql.SQLException;

public class AdminTableController {
    String id, productName, manufacturer, category, amount, pricePerItem;

    public AdminTableController(String id, String productName, String manufacturer, String category, String amount, String pricePerItem){
        this.id = id;
        this.productName = productName;
        this.manufacturer = manufacturer;
        this.category = category;
        this.amount = amount;
        this.pricePerItem = pricePerItem;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPricePerItem() {
        return pricePerItem;
    }

    public void setPricePerItem(String amount) {
        this.pricePerItem = amount;
    }
}

