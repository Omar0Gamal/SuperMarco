package com.supermacro;

public class Order {
    public static int idCounter=1;

    private int id;
    private Product [] products;
    private int [] quantity;

    public Order(){
        this.id=idCounter++;
    }

    public void setId(int id){
        this.id=id;
    }
    public void setQuantity(int [] quantity){
        this.quantity=quantity;
    }
    public void setProducts(Product[] products){
        this.products=products;
    }
    public int getId(){
        return id;
    }
    public int[] getQuantity(){
        return quantity;
    }
    public Product[] getProducts(){
        return products;
    }
    public double calculateTotal(){
        double total=0.0;
        for(int i=0;i<products.length;i++){
            total+=products[i].getPrice() * quantity[i];
        }
        return total;
    }
}
