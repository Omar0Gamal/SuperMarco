package com.supermacro;

import java.util.ArrayList;

public class User {

    public empType employeeType;
    private String username;
    private  String password;
    public static ArrayList <User> users = new ArrayList<>();


    public void setUsername(String username){
        this.username=username;
    }

    public String getUsername(){
        return username;
    }

    public void setPassword(String password){
        this.password=password;
    }

    public String getPassword(){
        return password;
    }

    // Ay Update
}
