package com.supermacro;


import java.util.ArrayList;

import static com.supermacro.empType.INVENTORY_EMPLOYEE;

public class InventoryEmp extends Employee{


    public InventoryEmp(String username, String password, int ID)
    {
        this.username = username;
        this.password = password;
        this.ID = ID;
        employeeType = INVENTORY_EMPLOYEE;
    }

    public static ArrayList<Product> inventory = new ArrayList<>();
    public void setUsername(String username){
        this.username = "INV_" + username;
    }
    public static void addProduct(Product p){
        if(!inventory.contains(p))
            inventory.add(p);
    }
    public static void deleteProduct(Product p){
            inventory.remove(p);
    }

    public static Product searchProduct(String name){
        for(int i=0; i< inventory.size(); i++) {
            if(name.equalsIgnoreCase(inventory.get(i).getName()))
                return inventory.get(i);
        }
        return null;
    }
    public static ArrayList<Product> listProduct(){
        return inventory;
}

    public static ArrayList<Product> reorderPoint(int reorderQuantity){
        ArrayList<Product> shortageProducts= new ArrayList<>();
        for(int i=0; i<inventory.size(); i++){
            if(inventory.get(i).getQuantity()<= reorderQuantity)
                shortageProducts.add(inventory.get(i));
        }
        return shortageProducts;

        }


    public static void updateProductPrice(double newPrice, Product product){
                product.setPrice(newPrice);
        }
    public static void updateProductName( String newName, Product product){
        product.setName(newName);
    }
    public static void updateProductDescribtion(String newdesc, Product product) {
        product.setDescription(newdesc);
    }
    public static void updateProductQuantity(int newQuantity , Product product){
        product.setQuantity(newQuantity);
    }
}