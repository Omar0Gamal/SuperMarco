package com.supermacro;
import java.util.Scanner;
import static com.supermacro.empType.NONE;
import static com.supermacro.empType.ADMIN;
import static com.supermacro.empType.MARKETING_EMPLOYEE;
import static com.supermacro.empType.INVENTORY_EMPLOYEE;
import static com.supermacro.empType.SALES_EMPLOYEE;


public class Main {

    //logging function
    static empType logIN(String username, String password)
    {

        for(User a1 : User.users)
        {
            if(username.equalsIgnoreCase(a1.getUsername()))
            {
                if(password.equalsIgnoreCase(a1.getPassword()))
                    return a1.employeeType;
            }
        }
        return NONE;
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

        System.out.println("Welcome to SuperMarco!");

        //receiving the username and password
        System.out.print("Enter username: ");
        String givenUsername = input.nextLine();
        System.out.println("Enter password: ");
        String givenPassword = input.nextLine();


        //Logging in
        empType employeeType = logIN(givenUsername, givenPassword);


        //If credentials are incorrect
        while(employeeType == NONE)
        {
            System.out.println("Incorrect Username or Password.\n Retry.");

            System.out.print("Enter username: ");
            givenUsername = input.nextLine();

            System.out.println("Enter password: ");
            givenPassword = input.nextLine();

            employeeType = logIN(givenUsername, givenPassword);

        }

        switch(employeeType)
        {

            case ADMIN:
                while()



        }





    }
}