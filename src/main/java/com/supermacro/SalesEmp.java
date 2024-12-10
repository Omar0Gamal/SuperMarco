package com.supermacro;
import java.util.ArrayList;
public class SalesEmp extends Employee{

    public static ArrayList<Order> orders = new ArrayList<>();

    public SalesEmp(){}

    private Product searchProduct(int id){
        for(Product p : InventoryEmp.inventory){
            if(id== p.getProductId())
                return p;
        }
        return null;
    }
    /*public void searchProduct(int id){
        System.out.println("name: "+ searchForProduct(id).getName());
        System.out.println("id: "+id);
        System.out.println("price: "+searchForProduct(id).getPrice());
        System.out.println("expiration date: "+searchForProduct(id).getExpDate());
        System.out.println("description: "+searchForProduct(id).getDescription());
        System.out.println("quantity: "+searchForProduct(id).getQuantity());
    }*/
    //public listProducts(){}

    public void makeOrder(Product []products,int []quantity){
        Order o=new Order();
        orders.add(o);
        System.out.println("order made :)");
        System.out.println("order id: "+o.getId());
        for(int i=0;i<o.getProducts().length;i++){
            System.out.println("product no"+(i+1)+": "+products[i]+quantity[i]);
        }
        System.out.println("Total price= "+o.calculateTotal());
    }
    public boolean cancelOrder(int id){
        for(Order o:orders){
            if(id==o.getId()) {
                orders.remove(o);
                System.out.println("order canceled");
                return true;
            }
        }
        System.out.println("order not found");
        return false;
    }

}
