package com.supermacro;

public abstract class User {

    public empType employeeType;
    protected String username;
    protected String password;



    public abstract  void setUsername(String username);
    public String getUsername(){
        return username;
    }

    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return password;
    }
}
