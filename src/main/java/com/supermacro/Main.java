package com.supermacro;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

import static com.supermacro.empType.NONE;


public class Main {

    //logging function
    static User logIN(Admin admin, String username, String password)
    {
        if(username.equalsIgnoreCase(admin.getUsername()))
        {
            if(password.equalsIgnoreCase(admin.getPassword()))
                return Admin.admin;
        }


        for(User a1 : User.users)
        {
            if(username.equalsIgnoreCase(a1.getUsername()))
            {
                if(password.equalsIgnoreCase(a1.getPassword()))
                    return a1;
            }
        }
        return null;
    }
    //-----------------------------------------------------------------------------------------



    //display menus

    static void displaySalesEmpMenu()
    {
        System.out.println("1- Search Product\n" +
                "2- List Products\n" +
                "3- Make Order\n" +
                "4- Cancel Order\n" +
                "5- Exit");
    }


    static void displayAdminMenu(){
        System.out.println("1- Add Employee\n" +
                           "2- Remove Employee\n" +
                           "3- Update Employee\n" +
                           "4- Set Unique ID\n" +
                           "5- List Employees\n" +
                           "6- Search Employee\n" +
                           "7- Set Employee Password\n" +
                            "8- Exit");
    }


    static void displayMarketingEmpMenu(){
        System.out.println("1- Make Offer\n" +
                "2- Make Report\n" +
                "3- Exit");
    }

    static void displayInventoryManagementEmpMenu(){
        System.out.println("1- Add Product\n" +
                "2- Delete Product\n" +
                "3- Update Product\n" +
                "4- List Products\n" +
                "5- Search  Product\n" +
                "6- Exit");
    }
    //------------------------------------------------------------------------------------------

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        DatabaseManager db = new DatabaseManager();
        InventoryEmp.inventory = db.getProducts();
        User.users = (ArrayList<User>) db.getEmployees().stream().map(e -> (User) e).collect(Collectors.toList());
        Admin admin = new Admin();
        int option;

        System.out.println("Welcome to SuperMarco!");

        //receiving the username and password
        System.out.print("Enter username: ");
        String givenUsername = input.nextLine();
        System.out.print("Enter password: ");
        String givenPassword = input.nextLine();


        //Logging in
        User user = logIN(admin, givenUsername, givenPassword);
        empType employeeType = user == null ? NONE : user.employeeType;
        if(employeeType != NONE) LogManager.log(user.getUsername(), "logged in", "");
        else LogManager.log(givenUsername, "failed to log in", "");


        //If credentials are incorrect
        while(employeeType == NONE)
        {
            System.out.println("Incorrect Username or Password.\n Retry.");

            System.out.print("Enter username: ");
            givenUsername = input.nextLine();

            System.out.print("Enter password: ");
            givenPassword = input.nextLine();

            user = logIN(admin, givenUsername, givenPassword);
            employeeType = user == null ? NONE : user.employeeType;
            if(employeeType != NONE) LogManager.log(user.getUsername(), "logged in", "");
            else LogManager.log(givenUsername, "failed to log in", "");
        }

        //menus
        switch(employeeType)
        {

            //If the person logging is a sales employee
            case SALES_EMPLOYEE:
                while(true)
                {
                    displaySalesEmpMenu();
                    System.out.print("Enter your choice: ");
                    option = input.nextInt();
                    input.skip("\\n");

                    switch(option)
                    {
                        case 1: //search product
                            System.out.print("Enter product ID: ");
                            int givenID=input.nextInt();

                            Product productFound = SalesEmp.searchProduct(givenID);

                            System.out.println("Product found!\n"+
                                    "Name: "+productFound.getName()+"\n"+
                                    "ID: "+givenID+"\n"+
                                    "Price: "+productFound.getPrice()+"\n"+
                                    "Expiration Date: "+productFound.getExpDate()+"\n"+
                                    "Description: "+productFound.getDescription()+"\n"+
                                    "Quantity: "+productFound.getQuantity());
                            LogManager.log(user.getUsername(), "searched for product with ID: " + givenID, "");
                            break;

                        case 2: //list products

                            ArrayList<Product> products=SalesEmp.listProducts();
                            for(Product product:products){
                                System.out.println("Name: "+product.getName()+
                                        " ID: "+product.getProductId()+
                                        " Price: "+product.getPrice());
                            }
                            LogManager.log(user.getUsername(), "listed products", "");
                            break;

                        case 3: //make order

                            System.out.print("Enter amount of different products: ");
                            int size=input.nextInt();

                            int productID;
                            Product []items=new Product[size];
                            int []quantity=new int[size];

                            for(int i=0;i<size;i++){
                                System.out.print("Enter item: ");
                                productID=input.nextInt();
                                Product product=SalesEmp.searchProduct((productID));
                                if(product!=null) {
                                    items[i] = product;
                                    System.out.print("Enter quantity: ");
                                    quantity[i] = input.nextInt();
                                }else{
                                    System.out.println("Product not found");
                                    i--;
                                }
                            }

                            SalesEmp.makeOrder(items,quantity);
                            LogManager.log(user.getUsername(), "made an order", "");
                            break;

                        case 4: //cancel order
                            System.out.print("Enter order ID: ");
                            int givenID2=input.nextInt();
                            SalesEmp.cancelOrder(givenID2);
                            LogManager.log(user.getUsername(), "canceled an order", "");
                            break;
                        case 5:
                            LogManager.log(user.getUsername(), "logged out", "");
                            System.exit(0);
                        default:
                            System.out.print("Invalid Operation.");
                    }
                }


                //If the person logging is an admin
            case ADMIN:
                while(true)
                {
                    displayAdminMenu();
                    System.out.print("Enter your choice: ");
                    option = input.nextInt();
                    input.skip("\\n");

                    switch(option)
                    {
                        case 1:
                            break;
                        case 2:
                            break;
                        case 3:
                            break;
                        case 4:
                            break;
                        case 5:
                            break;
                        case 6:
                            break;
                        case 7:
                            break;
                        case 8:
                            LogManager.log(user.getUsername(), "logged out", "");
                            System.exit(0);
                        default:
                            System.out.print("Invalid Operation.");
                    }
                }


            //If the person logging in is an inventory employee
            case INVENTORY_EMPLOYEE:
                while(true)
                {
                    displayInventoryManagementEmpMenu();
                    System.out.print("Enter your choice: ");
                    option = input.nextInt();
                    input.skip("\\n");

                    switch(option)
                    {
                        case 1: //add product
                            int price;
                            String name;
                            int productId;
                            String expDate;
                            String description;
                            int quantity;

                            //input product info
                            System.out.print("Enter name: ");
                            name = input.nextLine();
                            System.out.print("Enter price: ");
                            price = input.nextInt();
                            input.nextLine();
                            System.out.print("Please enter a date in the format yyyy-MM-dd: ");
                            expDate = input.nextLine();
                            System.out.print("Enter description: ");
                            description = input.nextLine();
                            System.out.print("Enter quantity available: ");
                            quantity = input.nextInt();
                            input.nextLine();

                            //adding product
                            InventoryEmp.inventory.add(new Product(price, name, expDate, description, quantity));
                            LogManager.log(user.getUsername(), "added a product", "");
                            break;

                        case 2: //delete product
                            System.out.print("Enter the product's name you want to remove: ");
                            String productName = input.nextLine();
                            Product product = InventoryEmp.searchProduct(productName);

                            if(product == null)
                            {
                                System.out.print("Product not found!");
                                break;
                            }

                            InventoryEmp.deleteProduct(product);
                            LogManager.log(user.getUsername(), "deleted a product", "");
                            break;

                        case 3: //update product
                            /*
                            System.out.print("Enter the product's name you want to update: ");
                            productName = input.nextLine();
                            Product product2 = InventoryEmp.searchProduct(productName);

                            if(product2 == null)
                            {
                                System.out.print("Product not found!");
                                break;
                            }
                            */

                            LogManager.log(user.getUsername(), "updated a product", "");
                            break;

                        case 4: //list products

                            ArrayList<Product> products=InventoryEmp.listProduct();
                            for(Product product3:products){
                                System.out.println("Name: "+product3.getName()+
                                        " ID: "+product3.getProductId()+
                                        " Price: "+product3.getPrice());
                            }
                            LogManager.log(user.getUsername(), "listed products", "");
                            break;

                        case 5: //search product

                            System.out.print("Enter product name: ");
                            productName = input.nextLine();
                            Product productFound = InventoryEmp.searchProduct(productName);
                            System.out.println("Product found!\n"+
                                    "Name: "+productFound.getName()+"\n"+
                                    "ID: "+productFound.getProductId()+"\n"+
                                    "Price: "+productFound.getPrice()+"\n"+
                                    "Expiration Date: "+productFound.getExpDate()+"\n"+
                                    "Description: "+productFound.getDescription()+"\n"+
                                    "Quantity: "+productFound.getQuantity());
                            LogManager.log(user.getUsername(), "searched for product", "");
                            break;

                        case 6:
                            db.setProducts(InventoryEmp.inventory);
                            LogManager.log(user.getUsername(), "logged out", "");
                            System.exit(0);
                        default:
                            System.out.print("Invalid Operation.");
                    }
                }

                //If the person logging is a marketing employee
            case MARKETING_EMPLOYEE:
                while (true)
                {
                    displayMarketingEmpMenu();
                    System.out.print("Enter your choice: ");
                    option = input.nextInt();
                    input.skip("\\n");

                    switch(option)
                    {
                        case 1:
                            break;
                        case 2:
                            break;
                        case 3:
                            LogManager.log(user.getUsername(), "logged out", "");
                            System.exit(0);
                        default:
                            System.out.print("Invalid Operation.");
                    }
                }
        }
    }
}