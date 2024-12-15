package com.supermacro;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import static com.supermacro.empType.NONE;


public class Main {

    //input functions
    public static int getIntInput(String prompt, Scanner input) {
        while (true) {
            try {
                System.out.print(prompt);
                int num = input.nextInt();
                input.nextLine();
                return num;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                input.nextLine();
            }
        }
    }

    public static double getDoubleInput(String prompt, Scanner input) {
        while (true) {
            try {
                System.out.print(prompt);
                double num = input.nextDouble();
                input.nextLine();
                return num;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                input.nextLine();
            }
        }
    }

    public static String getStringInput(String prompt, Scanner input) {
        System.out.print(prompt);
        return input.nextLine();
    }
    //------------------------------------------------------------------------------------------


    //logging function
    static User logIN(String username, String password)
    {
        if(username.equalsIgnoreCase(Admin.mainAdmin.getUsername()))
        {
            if(password.equalsIgnoreCase(Admin.mainAdmin.getPassword()))
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
        System.out.println("""
                1- Search Product
                2- List Products
                3- Make Order
                4- Cancel Order
                5- Set Username
                6- Set Password
                7- Exit
                """);
    }


    static void displayAdminMenu(){
        System.out.println("""
                           1- Add Employee
                           2- Remove Employee
                           3- Update Employee
                           4- List Employees
                           5- Search Employee
                           6- Set Employee Password
                           7- Set Username
                           8- Set Password
                           9- Exit
                           """);
    }


    static void displayMarketingEmpMenu(){
        System.out.println("""
                1- Make Offer
                2- Make Report
                3- Set Username
                4- Set Password
                5- Exit
                """);
    }

    static void displayInventoryManagementEmpMenu(){
        System.out.println("""
                1- Add Product
                2- Delete Product
                3- Update Product
                4- List Products
                5- Search Product
                6- Set Username
                7- Set Password
                8- Exit
                """);
    }
    //------------------------------------------------------------------------------------------

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        DatabaseManager db = new DatabaseManager();
        InventoryEmp.inventory = db.getProducts();
        Employee.employees = db.getEmployees();
        Product.count = db.getLastUsedProductID() + 1;
        Admin.idCounter = db.getLastUsedEmpId() + 1;
        int option;

        System.out.println("""
                
                ~~~~~~~~~~~~~~~~~~~~~~
                Welcome to SuperMarco!
                ~~~~~~~~~~~~~~~~~~~~~~
                
                
                """);

        //receiving the username and password
        String givenUsername = getStringInput("Enter username: ", input);
        String givenPassword = getStringInput("Enter password: ", input);


        //Logging in
        User user = logIN(givenUsername,givenPassword);
        empType employeeType = user == null ? NONE : user.employeeType;
        if(employeeType != NONE) LogManager.log(givenUsername, "logged in");
        else LogManager.log(givenUsername, "failed to log in");


        //If credentials are incorrect
        while(employeeType == NONE)
        {
            System.out.println("Incorrect Username or Password.\nRetry.");

            givenUsername = getStringInput("Enter username: ", input);
            givenPassword = getStringInput("Enter password: ", input);

            user = logIN(givenUsername,givenPassword);
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
                    option = getIntInput("Enter your choice: ", input);

                    switch(option)
                    {
                        case 1: //search product

                           int givenID = getIntInput("Enter product ID: ",input);

                            Product productFound = SalesEmp.searchProduct(givenID);
                            if(productFound == null)
                            {
                                System.out.println("Product Not found!");
                                break;
                            }
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
                                System.out.println("Name: "+product.getName()+"\n"+
                                        " ID: "+product.getProductId()+"\n"+
                                        " Price: "+product.getPrice()+"\n"+
                                        " Price before discount: "+product.getPrice()+"\n"+
                                        "Discount: "+product.getDiscount() +"\n"+
                                        "Price after discount: "+product.getTotal()+"\n"+
                                        "quantity: "+product.getQuantity());
                            }
                            LogManager.log(givenUsername, "listed products");
                            break;

                        case 3: //make order

                            int size = getIntInput("Enter amount of different products: ", input);

                            int productID;
                            Product []items=new Product[size];
                            int []quantity=new int[size];

                            for(int i=0;i<size;i++){
                                productID = getIntInput("Enter item ID: ",input);
                                Product product=SalesEmp.searchProduct((productID));
                                if(product!=null) {
                                    items[i] = product;
                                    quantity[i] = getIntInput("Enter quantity: ",input);
                                }else{
                                    System.out.println("Product not found");
                                    i--;
                                }
                            }

                            SalesEmp.makeOrder(items,quantity);
                            LogManager.log(givenUsername, "made an order");
                            break;

                        case 4: //cancel order

                            int givenID2 = getIntInput("Enter order ID: ", input);

                            SalesEmp.cancelOrder(givenID2);
                            LogManager.log(givenUsername, "canceled an order");
                            break;

                        case 5:// update username

                            String username = getStringInput("Enter new username: ",input);
                            user.setUsername(username);
                            db.setEmployees(Employee.employees);
                            LogManager.log(givenUsername, "updated username.");
                            break;

                        case 6: //update password

                            String password = getStringInput("Enter password: ",input);
                            user.setPassword(password);
                            db.setEmployees(Employee.employees);
                            LogManager.log(givenUsername, "updated password.");
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
                    option = getIntInput("Enter your choice: ",input);

                    switch(option)
                    {
                        case 1: //picking which type of emp to add and adding them

                            int choice2 = getIntInput("""
                                    What type of employee do you want to add?
                                    1- Sales Emp
                                    2- Inventory Emp
                                    3- Marketing Emp
                                    """, input);

                            String newEmpUsername = getStringInput("Enter username: ", input);
                            String newEmpPassword = getStringInput("Enter Password: ", input);

                            switch(choice2)
                            {
                                case 1:
                                    Admin.addSales(newEmpUsername, newEmpPassword);
                                    break;

                                case 2:
                                    Admin.addInventory(newEmpUsername, newEmpPassword);
                                    break;

                                case 3:
                                    Admin.addMarketing(newEmpUsername, newEmpPassword);
                                    break;

                                default:
                                    System.out.println("Invalid Operation!");
                            }
                            db.setEmployees(Employee.employees);
                            LogManager.log(Admin.mainAdmin.getUsername(), "added an employee");
                            break;

                        case 2: //removing an employee

                            String givenUsername2 = getStringInput("Enter username: ", input);
                            Admin.deleteEmp(givenUsername2);
                            db.setEmployees(Employee.employees);
                            LogManager.log(Admin.mainAdmin.getUsername(), "removed an employee");
                            break;

                        case 3: //updating an employee

                            String givenUsername3 = getStringInput("Enter Username: ", input);

                            int choice = getIntInput("""
                                    What do you want to update in the user?
                                    1- Username
                                    2- Password
                                    """, input);

                            switch(choice)
                            {
                                case 1:
                                    String newUsername = getStringInput("Enter new username: ", input);
                                    Admin.updateName(givenUsername3, newUsername);
                                    break;

                                case 2:
                                    String newPassword = getStringInput("Enter new password: ", input);
                                    Admin.updatePassword(givenUsername3, newPassword);
                                    break;

                                default:
                                    System.out.println("Invalid Operation!");

                            }
                            db.setEmployees(Employee.employees);
                            LogManager.log(Admin.mainAdmin.getUsername(), "updated employee information");
                            break;

                        case 4: //list employees
                            ArrayList<Employee> employees=Admin.mainAdmin.getEmployees();
                            for(Employee emp:employees){
                                System.out.println("username: "+emp.getUsername()+"\n"+
                                        " ID: "+emp.getID()+"\n"+
                                        "Role: "+emp.employeeType);
                            }
                            LogManager.log(Admin.mainAdmin.getUsername(), "listed employees");
                            break;

                        case 5: //search employee
                            String givenUserName3 = getStringInput("Enter username: ",input);
                            Employee empFound = Admin.searchEmp(givenUserName3);
                            if(empFound == null)
                            {
                                System.out.println("Employee Not found!");
                                break;
                            }

                            System.out.println("username: "+givenUserName3+"\n"+
                                    "ID: "+empFound.getID()+"\n"+
                                    "Role: "+empFound.employeeType);
                            LogManager.log(Admin.mainAdmin.getUsername(), "searched for an employee");
                            break;

                        /*case 6: //update employee password

                            String username2 = getStringInput("Enter username: ",input);
                            String newPass = getStringInput("Enter new password: ",input);
                            Admin.updatePassword(username2,newPass);
                            System.out.println("password changed successfully.");
                            db.setEmployees(Employee.employees);
                            LogManager.log(Admin.mainAdmin.getUsername(), "updated employee password");
                            break; */

                        case 7: //updating username

                            String username = getStringInput("Enter new username: ",input);
                            user.setUsername(username);
                            db.setEmployees(Employee.employees);
                            LogManager.log(Admin.mainAdmin.getUsername(), "updated username");
                            break;

                        case 8: //updating password

                            String password = getStringInput("Enter new password: ",input);
                            user.setPassword(password);
                            db.setEmployees(Employee.employees);
                            LogManager.log(Admin.mainAdmin.getUsername(), "updated password");
                            break;

                        case 9:
                            db.setEmployees(Employee.employees);
                            LogManager.log(Admin.mainAdmin.getUsername(), "logged out");
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
                    option = getIntInput("Enter your choice: ",input);

                    switch(option)
                    {
                        case 1: //add product
                            int price;
                            String name;
                            String expDate;
                            String description;
                            int quantity;

                            //input product info
                            name = getStringInput("Enter name: ", input);
                            price = getIntInput("Enter price: ",input);
                            expDate = getStringInput("Please enter a date in the format yyyy-MM-dd: ",input);
                            description = getStringInput("Enter description: ",input);
                            quantity = getIntInput("Enter quantity available: ",input);

                            //adding product
                            InventoryEmp.addProduct(new Product(price,name,expDate,description,quantity,0));
                            db.setProducts(InventoryEmp.inventory);
                            LogManager.log(givenUsername, "added a product");
                            break;

                        case 2: //delete product

                            String productName1 = getStringInput("Enter the product's name you want to remove: ", input);
                            Product product = InventoryEmp.searchProduct(productName1);

                            if(product == null)
                            {
                                System.out.print("Product not found!");
                                break;
                            }

                            InventoryEmp.deleteProduct(product);
                            db.setProducts(InventoryEmp.inventory);
                            LogManager.log(givenUsername, "deleted a product");
                            break;

                        case 3: //updating a product

                            int choice = getIntInput("""
                                    What do you want to update?
                                    1- Name
                                    2- Price
                                    3- Description
                                    4- Quantity
                                    Enter your choice:\s""", input);

                            String givenProductName = getStringInput("Enter product name: ", input);
                            Product product2 = InventoryEmp.searchProduct(givenProductName);
                            if(product2 == null)
                            {
                                System.out.println("Product not found!");
                                break;
                            }

                            switch(choice)
                            {
                                case 1:

                                    String newProductName = getStringInput("Enter new wanted name: ", input);
                                    InventoryEmp.updateProductName(newProductName, product2);
                                    break;

                                case 2:

                                    double newProductPrice = getDoubleInput("Enter new wanted price: ", input);
                                    InventoryEmp.updateProductPrice(newProductPrice, product2);
                                    break;

                                case 3:

                                    String newProductDesc = getStringInput("Enter new wanted description: ", input);
                                    InventoryEmp.updateProductDescribtion(newProductDesc, product2);
                                    break;

                                case 4:

                                    int newProductQuantity = getIntInput("Enter new wanted quantity: ", input);
                                    InventoryEmp.updateProductQuantity(newProductQuantity, product2);
                                    break;

                                default:
                                    System.out.println("Invalid Operation!");
                            }
                            db.setProducts(InventoryEmp.inventory);
                            LogManager.log(givenUsername, "updated a product");
                            break;

                        case 4: //list products

                            ArrayList<Product> products=InventoryEmp.listProduct();
                            for(Product product3:products){
                                System.out.println("Name: "+product3.getName()+
                                        " ID: "+product3.getProductId()+
                                        " Price before discount: "+product3.getPrice()+
                                        "Discount: "+product3.getDiscount()  +
                                         "Price after discount: "+product3.getTotal());
                            }
                            LogManager.log(givenUsername, "listed products");
                            break;

                        case 5: //search product

                            String productName = getStringInput("Enter product name: ",input);
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

                        case 6: //updating username

                            String username = getStringInput("Enter new username: ",input);
                            user.setUsername(username);
                            db.setEmployees(Employee.employees);
                            LogManager.log(givenUsername, "updated username");
                            break;

                        case 7: //updating password

                            String password = getStringInput("Enter password: ",input);
                            user.setPassword(password);
                            db.setEmployees(Employee.employees);
                            LogManager.log(givenUsername, "updated password");
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
                    option = getIntInput("Enter your choice: ",input);

                    MarketingEmp emp = (MarketingEmp) user;
                    switch(option)
                    {
                        case 1: //making an Offer

                            int id = getIntInput("Enter the Product Id: ", input);
                            double discount = getDoubleInput("Enter the Discount: ",input);
                            emp.makeOffer(id, discount);
                            db.setProducts(InventoryEmp.inventory);
                            LogManager.log(givenUsername, "made a discount");
                            break;

                        case 2: //making a Report

                            String title = getStringInput("Enter the Title: ",input);
                            String desc = getStringInput("Enter the Description: ",input);
                            Report report = emp.makeReport(title,desc);
                            System.out.println(report.toString());
                            LogManager.log(givenUsername, "made a report");
                            break;

                        case 3: //updating username

                            String username = getStringInput("Enter new username: ",input);
                            user.setUsername(username);
                            db.setEmployees(Employee.employees);
                            LogManager.log(givenUsername, "updated username");
                            break;

                        case 4: //updating password

                            String password = getStringInput("Enter password: ",input);
                            user.setPassword(password);
                            db.setEmployees(Employee.employees);
                            LogManager.log(givenUsername, "updated password");
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