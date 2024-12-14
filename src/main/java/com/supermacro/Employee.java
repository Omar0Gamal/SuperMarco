package com.supermacro;

import java.util.ArrayList;

public abstract class Employee extends User{
    int ID;
    public static ArrayList<Employee> employees = new ArrayList<>();
    public  void setID(int id){
        this.ID=id;
    }
    public int getID(){
        return ID;
    }
}
