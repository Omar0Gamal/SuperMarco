package com.supermacro;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseManager {

    private static final String DB_FILE = "SuperMacro.db";

    private static final String CREATE_TABLE_STATEMENT = """
            CREATE TABLE IF NOT EXISTS Employee (
                id INTEGER PRIMARY KEY,
                name TEXT NOT NULL,
                username TEXT NOT NULL,
                password TEXT NOT NULL,
                role TEXT NOT NULL
            );
            CREATE TABLE IF NOT EXISTS Admin (
                id INTEGER PRIMARY KEY,
                name TEXT NOT NULL,
                username TEXT NOT NULL,
                password TEXT NOT NULL
            );
            CREATE TABLE IF NOT EXISTS Product (
                id INTEGER PRIMARY KEY,
                name TEXT NOT NULL,
                price REAL NOT NULL,
                decs TEXT NOT NULL
            );
            CREATE TABLE IF NOT EXISTS Inventory (
                product_id INTEGER PRIMARY KEY,
                quantity INTEGER NOT NULL,
                expiration_date DATE NOT NULL,
                FOREIGN KEY (product_id) REFERENCES Product(id)
            );
            """;
    private Connection connection;

    public DatabaseManager() {
        load();
    }

    public Connection getConnection() {
        File dataFolder = new File("./", DB_FILE);
        if (!dataFolder.exists()) {
            try {
                dataFolder.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            if(connection!=null && !connection.isClosed()){
                return connection;
            }
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
            return connection;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
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
            ex.printStackTrace();
        }
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void reset() {
        try {
            connection = getConnection();
            Statement s = connection.createStatement();
            s.executeUpdate("DROP TABLE IF EXISTS Employee;");
            s.executeUpdate("DROP TABLE IF EXISTS Admin;");
            s.executeUpdate("DROP TABLE IF EXISTS Customer;");
            s.executeUpdate("DROP TABLE IF EXISTS Product;");
            s.executeUpdate("DROP TABLE IF EXISTS Inventory;");
            s.close();
            load();
        } catch (SQLException ex) {
            ex.printStackTrace();
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
                String name = rs.getString("name");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String role = rs.getString("role");
                switch (role) {
                    case "Admin":
                        /*Admin admin = new Admin();
                        admin.setId(id);
                        admin.setName(name);
                        admin.setUsername(username);
                        admin.setPassword(password);*/
                        break;
                    case "SalesEmp":
                        SalesEmp salesEmp = new SalesEmp();
                        salesEmp.ID = id;
                        //salesEmp.setId(id);
                        //salesEmp.setName(name);
                        salesEmp.setUsername(username);
                        salesEmp.setPassword(password);

                        employees.add(salesEmp);
                        break;
                    case "InventoryEmp":
                        InventoryEmp inventoryEmp = new InventoryEmp();
                        //inventoryEmp.setId(id);
                        //inventoryEmp.setName(name);
                        inventoryEmp.setUsername(username);
                        inventoryEmp.setPassword(password);
                        employees.add(inventoryEmp);
                        break;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return employees;
    }

    public void setEmployees(ArrayList<Object> employees){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement("INSERT INTO Employee (name, username, password, role) VALUES (?, ?, ?, ?);");
            for (Object employee : employees) {
                ps.setString(1, (String) employee);
                ps.setString(2, (String) employee);
                ps.setString(3, (String) employee);
                ps.setString(4, (String) employee);
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
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

            ps = conn.prepareStatement("SELECT id, name, price, decs, quantity, expiration_date from Product p join Inventory i on p.id = i.product_id");

            rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int price = rs.getInt("price");
                String description = rs.getString("decs");
                int quantity = rs.getInt("quantity");
                Date expirationDate = rs.getDate("expiration_date");
                Product product = new Product(price, name, id, expirationDate, description, quantity);
                products.add(product);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement("INSERT INTO Product (id,name, price, decs) VALUES (?,?, ?, ?);");
            for (Product product : products) {
                ps.setInt(1, product.getProductId());
                ps.setString(2, product.getName());
                ps.setInt(3, product.getPrice());
                ps.setString(4, product.getDescription());
                ps.executeUpdate();
            }

            ps = conn.prepareStatement("INSERT INTO Inventory (product_id, quantity, expiration_date) VALUES (?, ?, ?);");
            for (Product product : products) {
                ps.setInt(1, product.getProductId());
                ps.setInt(2, product.getQuantity());
                ps.setDate(3, new java.sql.Date(product.getExpDate().getTime()));
                ps.executeUpdate();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
