package com.supermacro;
import java.util.Scanner;

public class Main {


    //logging function
    static empType logIN(String username, String password)
    {
        empType employeetype;

        for(User a1 : User.users)
        {
            if(username.equalsIgnoreCase(a1.getUsername()))
            {
                if(password.equalsIgnoreCase(a1.getPassword()))

            }
        }
    }
    //-----------------------------------------------------------------------------------------



    //display menus

    static void displayAdminMenu(){
        System.out.println("1- Add Employee\n" +
                           "2- Remove Employee\n" +
                           "3- Update Employee\n" +
                           "4- Set Unique ID" +
                           "5- List Employees\n" +
                           "6- Search Employee\n" +
                           "7- Set Employee Password");
    }
    //------------------------------------------------------------------------------------------

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        DatabaseManager db = new DatabaseManager();

        System.out.println("Welcome to SuperMarco hypermarket!");

        //receiving the username and password
        System.out.print("Enter username: ");
        String givenUsername = input.nextLine();
        System.out.println("Enter password: ");
        String givenPassword = input.nextLine();








    }
}