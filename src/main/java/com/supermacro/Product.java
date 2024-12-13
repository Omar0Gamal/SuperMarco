package com.supermacro;

import java.util.Date;

public class Product {
    private int price;
    private String name;
    private int productId;
    private Date expDate;
    private String description;
    private int quantity;


    public Product (int price,String name,String expDate,String description,int quantity) {
        this.price = price;
        this.name = name;
        this.productId = count;
        count++;
        this.expDate = expDate;
        this.description = description;
        this.quantity = quantity;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}