package com.supermacro;

import java.util.ArrayList;

public abstract class User {

    public empType employeeType;
    protected String username;
    protected String password;
    public static ArrayList <User> users = new ArrayList<>();


    public abstract void setUsername(String username);

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
