package com.supermacro;


import java.util.ArrayList;

public class InventoryEmp extends Employee{
    public static ArrayList<Product> inventory = new ArrayList<>();
    employeeType = InventoryEmp;

    public void addProduct(Product p){
        if(!inventory.contains(p))
            inventory.add(p);
    }
    public void deleteProduct(Product p){
            inventory.remove(p);
    }
    public void updateProduct(Product p){
        for(int i=0;i< inventory.size();i++){
            if(inventory.get(i).getProductId()==p.getProductId())
                inventory.set(i,p);
        }

    }
    public Product searchProduct(String name){
        for(int i=0; i< inventory.size(); i++) {
            if(name== inventory.get(i).getName())
                return inventory.get(i);
        }
        return null;
    }
public ArrayList<Product> listProduct(){
        return inventory;
}
public ArrayList<Product> productProblems(){
        ArrayList<Products>
}
}
