package com.supermacro;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.Collectors;
import static com.supermacro.empType.ADMIN;
import static com.supermacro.empType.NONE;


public class Main {

    //logging function
    static User logIN(Admin admin, String username, String password)
    {
        if(username.equalsIgnoreCase(admin.getUsername()))
        {
            if(password.equalsIgnoreCase(admin.getPassword()))
                return Admin.mainAdmin;
        }


        for(User a1 : Employee.employees)
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
                "5- Set Username\n" +
                "6- Set Password\n" +
                "7- Exit");
    }


    static void displayAdminMenu(){
        System.out.println("1- Add Employee\n" +
                           "2- Remove Employee\n" +
                           "3- Update Employee\n" +
                           "4- Set Unique ID\n" +
                           "5- List Employees\n" +
                           "6- Search Employee\n" +
                           "7- Set Employee Password\n" +
                           "8- Set Username\n" +
                            "9- Set Password\n" +
                            "10- Exit");
    }


    static void displayMarketingEmpMenu(){
        System.out.println("1- Make Offer\n" +
                "2- Make Report\n" +
                "3- Set Username\n" +
                "4- Set Password\n" +
                "5- Exit");
    }

    static void displayInventoryManagementEmpMenu(){
        System.out.println("1- Add Product\n" +
                "2- Delete Product\n" +
                "3- Update Product\n" +
                "4- List Products\n" +
                "5- Search  Product\n" +
                "6- Set Username\n" +
                "7- Set Password\n" +
                "8- Exit");
    }
    //------------------------------------------------------------------------------------------

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        DatabaseManager db = new DatabaseManager();
        InventoryEmp.inventory = db.getProducts();
        Employee.employees = db.getEmployees();
        Product.count = db.getLastUsedProductID() + 1;
        Admin.idCounter = db.getLastUsedEmpId() + 1;
        Admin admin = Admin.mainAdmin;
        int option;

        System.out.println("Welcome to SuperMarco!");

        //receiving the username and password
        System.out.print("Enter username: ");
        String givenUsername = input.nextLine();
        System.out.print("Enter password: ");
        String givenPassword = input.nextLine();


        //Logging in
        User user = logIN(admin,givenUsername,givenPassword);
        empType employeeType = user == null ? NONE : user.employeeType;
        if(employeeType != NONE) LogManager.log(givenUsername, "logged in");
        else LogManager.log(givenUsername, "failed to log in");


        //If credentials are incorrect
        while(employeeType == NONE)
        {
            System.out.println("Incorrect Username or Password.\nRetry.");

            System.out.print("Enter username: ");
            givenUsername = input.nextLine();

            System.out.print("Enter password: ");
            givenPassword = input.nextLine();

            user = logIN(admin,givenUsername,givenPassword);
            employeeType = user == null ? NONE : user.employeeType;
            if(employeeType != NONE) LogManager.log(givenUsername, "logged in");
            else LogManager.log(givenUsername, "failed to log in");
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
                    input.nextLine();

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
                            LogManager.log(givenUsername, "searched for product with ID: " + givenID);
                            break;

                        case 2: //list products

                            ArrayList<Product> products=SalesEmp.listProducts();
                            for(Product product:products){
                                System.out.println("Name: "+product.getName()+
                                        " ID: "+product.getProductId()+
                                        " Price: "+product.getPrice());
                            }
                            LogManager.log(givenUsername, "listed products");
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
                            LogManager.log(givenUsername, "made an order");
                            break;

                        case 4: //cancel order

                            System.out.print("Enter order ID: ");
                            int givenID2=input.nextInt();
                            SalesEmp.cancelOrder(givenID2);
                            LogManager.log(givenUsername, "canceled an order");
                            break;

                        case 5:// update username

                            System.out.print("Enter new username: ");
                            String username=input.next();
                            user.setUsername(username);
                            break;

                        case 6: //update password
                            System.out.print("Enter password: ");
                            String password=input.next();
                            user.setPassword(password);
                            break;

                        case 7: //exit system
                            db.setEmployees(Employee.employees);
                            LogManager.log(givenUsername, "logged out");
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
                    input.nextLine();

                    switch(option)
                    {
                        case 1:

                            break;
                        case 2:
                            System.out.print("Enter username: ");
                            String givenUsername2 = input.nextLine();

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
                            break;
                        case 9:
                            break;
                        case 10:
                            db.setEmployees(Employee.employees);
                            LogManager.log(givenUsername, "logged out");
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
                    input.nextLine();

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

                            InventoryEmp.addProduct(new Product(price,name,expDate,description,quantity,0));
                            LogManager.log(givenUsername, "added a product");
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
                            LogManager.log(givenUsername, "deleted a product");
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

                            LogManager.log(givenUsername, "updated a product");
                            break;

                        case 4: //list products

                            ArrayList<Product> products=InventoryEmp.listProduct();
                            for(Product product3:products){
                                System.out.println("Name: "+product3.getName()+
                                        " ID: "+product3.getProductId()+
                                        " Price: "+product3.getPrice());
                            }
                            LogManager.log(givenUsername, "listed products");
                            break;

                        case 5: //search product

                            System.out.print("Enter product name: ");
                            productName = input.nextLine();
                            Product productFound = InventoryEmp.searchProduct(productName);
                            if(productFound == null)
                            {
                                System.out.println("Product Not found!");
                                break;
                            }
                            System.out.println("Product found!\n"+
                                    "Name: "+productFound.getName()+"\n"+
                                    "ID: "+productFound.getProductId()+"\n"+
                                    "Price: "+productFound.getPrice()+"\n"+
                                    "Expiration Date: "+productFound.getExpDate()+"\n"+
                                    "Description: "+productFound.getDescription()+"\n"+
                                    "Quantity: "+productFound.getQuantity());
                            LogManager.log(givenUsername, "searched for product");
                            break;

                        case 6:
                            break;

                        case 7:
                            break;

                        case 8:
                            db.setProducts(InventoryEmp.inventory);
                            db.setEmployees(Employee.employees);
                            LogManager.log(givenUsername, "logged out");
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
                    input.nextLine();

                    MarketingEmp emp = (MarketingEmp) user;
                    switch(option)
                    {
                        case 1:
                            System.out.print("Enter the Title: ");
                            String title = input.nextLine();
                            System.out.print("Enter the Description: ");
                            String desc = input.nextLine();
                            Report report = emp.makeReport(title,desc);
                            System.out.println(report.toString());
                            break;
                        case 2:
                            System.out.print("Enter the Product Id: ");
                            int id = input.nextInt();
                            System.out.print("Enter the Discount: ");
                            int discount = input.nextInt();
                            emp.makeOffer(id,discount);
                            break;
                        case 3:
                            System.out.print("Enter new username: ");
                            String username = input.next();
                            user.setUsername(username);
                            break;
                        case 4:
                            System.out.print("Enter password: ");
                            String password = input.next();
                            user.setPassword(password);
                            break;
                        case 5:
                            db.setEmployees(Employee.employees);
                            db.setProducts(InventoryEmp.inventory);
                            LogManager.log(givenUsername, "logged out");
                            System.exit(0);
                        default:
                            System.out.print("Invalid Operation.");
                    }
                }
        }
    }
}