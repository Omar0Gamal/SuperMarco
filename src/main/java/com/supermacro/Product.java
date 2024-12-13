package com.supermacro;



public class Product {
    private int price;
    private static int count = 1;
    private String name;
    private int productId;
    private String expDate;
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

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getExpDate() {
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