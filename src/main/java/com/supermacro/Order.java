package com.supermacro;

public class Order {
    public static int idCounter=1;
    private int id;
    private Product [] items;  //array for the items in the order
    private int [] quantity;  //array for the quantity of each item

    public Order(){}

    public Order(Product []items,int [] quantity)
    {
        this.id= idCounter;
        idCounter++;
        this.items=items;
        this.quantity=quantity;

    }

    public void setId(int id){
        this.id=id;
    }


    public void setQuantity(int [] quantity){
        this.quantity=quantity;
    }


    public void setItems(Product[] items){
        this.items=items;
    }


    public int getId(){
        return id;
    }


    public int[] getQuantity(){
        return quantity;
    }


    public Product[] getItems(){
        return items;
    }


    public double calculateTotal(){
        double total=0.0;
        for(int i=0;i<items.length;i++){
            total+=items[i].getPrice() * quantity[i];
        }
        return total;
    }
}
