package com.supermacro;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {

    private static final String DB_FILE = "SuperMacro.db";

    private static final String CREATE_TABLE_STATEMENT = """
            CREATE TABLE IF NOT EXISTS Employee (
                id INTEGER PRIMARY KEY,
                username TEXT NOT NULL,
                password TEXT NOT NULL,
                role TEXT NOT NULL
            );
            CREATE TABLE IF NOT EXISTS Product (
                id INTEGER PRIMARY KEY,
                name TEXT NOT NULL,
                price REAL NOT NULL,
                discount REAL NOT NULL,
                decs TEXT NOT NULL
            );
            CREATE TABLE IF NOT EXISTS Inventory (
                product_id INTEGER PRIMARY KEY,
                quantity INTEGER NOT NULL,
                expiration_date TEXT NOT NULL,
                FOREIGN KEY (product_id) REFERENCES Product(id)
            );
            """;
    private Connection connection;

    public DatabaseManager() {
        load();
    }

    public Connection getConnection() {
        File db = new File("./", DB_FILE);
        if (!db.exists()) {
            try {
                boolean created = db.createNewFile();
                if (created) {
                    LogManager.log("DatabaseManager", "Database file created.");
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        try {
            if(connection!=null && !connection.isClosed()){
                return connection;
            }
            connection = DriverManager.getConnection("jdbc:sqlite:" + db);
            return connection;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public void load() {
        try {
            connection = getConnection();
            Statement s = connection.createStatement();
            s.executeUpdate(CREATE_TABLE_STATEMENT);
            s.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public ArrayList<Employee> getEmployees() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Employee> employees = new ArrayList<>();
        try {
            conn = getConnection();

            ps = conn.prepareStatement("SELECT * FROM Employee;");

            rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String role = rs.getString("role");
                switch (role) {
                    case "SalesEmp":
                        SalesEmp salesEmp = new SalesEmp(username,password,id);
                        employees.add(salesEmp);
                        break;
                    case "InventoryEmp":
                        InventoryEmp inventoryEmp = new InventoryEmp(username,password,id);
                        employees.add(inventoryEmp);
                        break;
                    case "MarketingEmp":
                        MarketingEmp marketingEmp = new MarketingEmp(username,password,id);
                        employees.add(marketingEmp);
                        break;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return employees;
    }

    public void setEmployees(ArrayList<Employee> employees){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            Statement s = conn.createStatement();
            s.executeUpdate("DELETE FROM Employee;");
            ps = conn.prepareStatement("INSERT INTO Employee (id, username, password, role) VALUES (?, ?, ?, ?);");
            for (Employee employee : employees) {
                switch (employee.employeeType) {
                    case SALES_EMPLOYEE:
                        ps.setInt(1,employee.getID());
                        ps.setString(2, employee.getUsername());
                        ps.setString(3, employee.getPassword());
                        ps.setString(4, "SalesEmp");
                        break;
                    case INVENTORY_EMPLOYEE:
                        ps.setInt(1,employee.getID());
                        ps.setString(2, employee.getUsername());
                        ps.setString(3, employee.getPassword());
                        ps.setString(4, "InventoryEmp");
                        break;
                    case MARKETING_EMPLOYEE:
                        ps.setInt(1,employee.getID());
                        ps.setString(2, employee.getUsername());
                        ps.setString(3, employee.getPassword());
                        ps.setString(4, "MarketingEmp");
                        break;
                }
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public ArrayList<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();

            ps = conn.prepareStatement("SELECT id, name, price, discount, decs, quantity, expiration_date from Product p join Inventory i on p.id = i.product_id");

            rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                double discount = rs.getDouble("discount");
                String description = rs.getString("decs");
                int quantity = rs.getInt("quantity");
                String expirationDate = rs.getString("expiration_date");
                Product product = new Product(price, name, expirationDate, description, quantity,discount);
                product.setProductId(id);
                products.add(product);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            Statement s = conn.createStatement();
            s.executeUpdate("DELETE FROM Product; DELETE FROM Inventory;");
            ps = conn.prepareStatement("INSERT INTO Product (id, name, price, discount, decs) VALUES (?, ?, ?, ?, ?);");
            for (Product product : products) {
                ps.setInt(1, product.getProductId());
                ps.setString(2, product.getName());
                ps.setDouble(3, product.getPrice());
                ps.setDouble(4, product.getDiscount());
                ps.setString(5, product.getDescription());
                ps.executeUpdate();
            }

            ps = conn.prepareStatement("INSERT INTO Inventory (product_id, quantity, expiration_date) VALUES (?, ?, ?);");
            for (Product product : products) {
                ps.setInt(1, product.getProductId());
                ps.setInt(2, product.getQuantity());
                ps.setString(3, product.getExpDate());
                ps.executeUpdate();
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public int getLastUsedEmpId(){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int max = 1;
        try{
            conn = getConnection();

            ps = conn.prepareStatement("SELECT MAX(id) as 'max' from Employee e");

            rs = ps.executeQuery();
            while(rs.next()) {
                max = rs.getInt("max");
            }
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return max;
    }
    public int getLastUsedProductID(){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int max = 1;
        try{
            conn = getConnection();

            ps = conn.prepareStatement("SELECT MAX(id) as 'max' from Product p");

            rs = ps.executeQuery();
            while(rs.next()) {
                max = rs.getInt("max");
            }
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return max;
    }
}
