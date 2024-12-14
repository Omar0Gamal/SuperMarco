package com.supermacro;
import java.util.ArrayList;

import static com.supermacro.empType.SALES_EMPLOYEE;

public class SalesEmp extends Employee{

    public static ArrayList<Order> orders = new ArrayList<>();

    public SalesEmp(){
        employeeType=SALES_EMPLOYEE;
    }



    public static Product searchProduct(int id){
        for(Product p : InventoryEmp.inventory){
            if(id== p.getProductId())
                return p;
        }
        return null;
    }


    public static ArrayList<Product> listProducts(){
        return InventoryEmp.inventory;
    }



    public static void makeOrder(Product []items,int []quantity) {
        if (items.length == quantity.length){
            Order o = new Order(items, quantity);
            orders.add(o);
            System.out.println("order made :)");
            System.out.println("order id: " + o.getId());
            for (int i = 0; i < o.getItems().length; i++) {
                System.out.println("product no" + (i + 1) + ": " + items[i] + quantity[i]);
            }
            System.out.println("Total price= " + o.calculateTotal());
        }
    }


    public static void cancelOrder(int id){
        for(Order o:orders){
            if(id==o.getId()) {
                orders.remove(o);
                System.out.println("order canceled");
                return;
            }
        }
        System.out.println("order not found");
    }

    public void setUsername(String username){
        this.username="SAL_"+username;
    }


}
