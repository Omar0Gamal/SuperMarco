package com.supermacro;

import java.util.ArrayList;

public class User implements credentials{

    public empType employeeType;
    private String username;
    private  String password;
    public static ArrayList <User> users = new ArrayList<>();

    @Override
    public void setUsername(String username){
        this.username=username;
    }

    @Override
    public String getUsername(){
        return username;
    }

    @Override
    public void setPassword(String password){
        this.password=password;
    }

    @Override
    public String getPassword(){
        return password;
    }

    // Ay Update
}
