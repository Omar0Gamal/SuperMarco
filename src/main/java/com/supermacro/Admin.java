package com.supermacro;

import java.util.ArrayList;

public class Admin extends User{

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
}
