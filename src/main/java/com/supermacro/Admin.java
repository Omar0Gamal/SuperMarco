package com.supermacro;

import java.util.ArrayList;

public class Admin extends User {

    public static Admin mainAdmin = new Admin();

    public Admin (){
        this.username = "Admin";
        this.password = "123";
        this.employeeType = empType.ADMIN;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }
    public void setpassword(String password){this.password=password;}
    public static int idCounter = 1;



    public static void addMarketing(String username,String password){  //add MarketingEmployee
        MarketingEmp mar=new MarketingEmp(username,password,idCounter);
        mar.setUsername(username);
        setUniqueId(mar);
        Employee.employees.add(mar);

    }

    public static void addInventory(String username,String password){  //add InventoryEmployee

        InventoryEmp inv=new InventoryEmp(username,password,idCounter);
        inv.setUsername(username);
        setUniqueId(inv);
        Employee.employees.add(inv);

    }

    public static void addSales(String username,String password){  //add SalesEmployee
        SalesEmp sale=new SalesEmp(username,password,idCounter);
        sale.setUsername(username);
        setUniqueId(sale);
        Employee.employees.add(sale);
    }

    public static void setUniqueId(Employee e){
        e.setID(idCounter++);
    }


    public static void deleteEmp(String username){    //delete the employee
        for (int i=0;i<Employee.employees.size();i++){
            if (Employee.employees.get(i).username.equalsIgnoreCase(username)){
                Employee.employees.remove(i);
                System.out.println("Employee removed");
                break;
            }
        }
    }


    public ArrayList<Employee> getEmployees(){    //list the employee
        return Employee.employees;
    }


    public static Employee searchEmp(String username){   //search for employee
        for (int i=0;i<Employee.employees.size();i++){
            if(username.equalsIgnoreCase(Employee.employees.get(i).username)){
                return Employee.employees.get(i);
            }
        }
        return null;
    }


    public static void updateName(String username ,String newusername) {
        Employee e = searchEmp(username);
        if(e == null) return;
        if(newusername!=null){
            e.username=newusername;
        }
    }


    public static void updatePassword(String username ,String newpassword){
        for (int i=0;i<Employee.employees.size();i++){
            Employee e=Employee.employees.get(i);
            if(e.username.equalsIgnoreCase(username)){
                if(newpassword!=null){
                    e.setPassword(newpassword);
                }
            }
        }
    }
}