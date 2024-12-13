package com.supermacro;

public class Main {

    static empType logIN(String username, String password)
    {
        for(User a1 : User.users)
        {
            if(username.equalsIgnoreCase(a1.getUsername()))
            {
                if(password.equalsIgnoreCase(a1.getPassword()))

            }
        }
    }

    static void displayAdminMenu(){
        System.out.println("1- Add Employee\n" +
                           "2- Remove Employee\n" +
                           "3- Update Employee\n" +
                           "4- Set Unique ID" +
                           "5- List Employees\n" +
                           "6- Search Employee\n" +
                           "7- Set Employee Password");
    }


    public static void main(String[] args) {

        int test;
        DatabaseManager db = new DatabaseManager();






    }
}