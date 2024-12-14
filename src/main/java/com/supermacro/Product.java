package com.supermacro;



public class Product {
    private double price;
    private double total;  // price after discount
    public static int count = 1;
    private String name;
    private int productId;
    private String expDate;
    private String description;
    private int quantity;
    private double discount;


    public Product (double price,String name,String expDate,String description,int quantity,double discount) {
        this.price = price;
        this.total = price - discount;
        this.name = name;
        this.productId = count;
        count++;
        this.expDate = expDate;
        this.description = description;
        this.quantity = quantity;
        this.discount = discount;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
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

    public void setDiscount(double discount) {
        this.discount = discount;
        this.total = price- discount;
    }

    public double getDiscount() {
        return discount;
    }

    public double getTotal(){
        return total;
    }
}