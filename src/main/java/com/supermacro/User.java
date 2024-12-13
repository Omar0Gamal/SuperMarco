package com.supermacro;

import java.util.ArrayList;

public abstract class User {

    public empType employeeType;
    private String username;
    private  String password;
    public static ArrayList <User> users = new ArrayList<>();

    public abstract String getUsername();

    public abstract String getPassword();

    // Ay Update
}
