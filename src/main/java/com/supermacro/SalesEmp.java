package com.supermacro;

public class SalesEmp extends Employee{

    public SalesEmp(){}

    private Product searchForProduct(int id){
        for(Product p : InventoryEmp.inventory){
            if(id== p.getProductId())
                return p;
        }
        return null;
    }
    public void searchProduct(int id){
        System.out.println("name: "+ searchForProduct(id).getName());
        System.out.println("id: "+id);
        System.out.println("price: "+searchForProduct(id).getPrice());
        System.out.println("expiration date: "+searchForProduct(id).getExpDate());
        System.out.println("description: "+searchForProduct(id).getDescription());
        System.out.println("quantity: "+searchForProduct(id).getQuantity());
    }
    //public listProducts(){}

    public void makeOrder(){
        
    }

}
