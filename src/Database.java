/*
 * Copyright (C) 2018 Michał Zieliński
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 *
 * @author Michał Zieliński
 */

import java.math.BigDecimal;
import java.sql.*;

public class Database {
    
    private Connection conn = null;
    private static Database instance = null;
       
    private Database() throws Exception{
        Class.forName("oracle.jdbc.driver.OracleDriver");
        String serverName = "localhost";
        String portNumber = "1521";
        String sid = "xe";
        String url = "jdbc:oracle:thin:@" + serverName + ":" + portNumber + ":" + sid;
        String username = "IOPROJECT";
        String password = "qwe";
        conn = DriverManager.getConnection(url, username, password);
    }
    
    public static Database getInstance() throws Exception {
        if(instance == null) {
            instance = new Database();
        } else if (instance.getConnection().isClosed())
            instance = new Database();
        return instance;
    }
    
    private Connection getConnection() {
        return conn;
    }
    
    public ResultSet searchQuery(String searchString, boolean onlyAvailable) throws Exception{
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM PRODUCTS WHERE LOWER(NAME) LIKE LOWER(?) ORDER BY NAME ASC");
        if (onlyAvailable) stmt = conn.prepareStatement("SELECT * FROM PRODUCTS WHERE LOWER(NAME) LIKE LOWER(?) AND QUANTITY > 0 ORDER BY NAME ASC");
        stmt.setString(1, "%" + searchString + "%");
        ResultSet rs = stmt.executeQuery();
        return rs; 
    }
    
    public String addProduct(String name, BigDecimal quantity, BigDecimal Price) throws Exception{
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO PRODUCTS (NAME, QUANTITY, PRICE) VALUES (?,?,?)", new String[]{"ID"});
        stmt.setString(1, name);
        stmt.setBigDecimal(2, quantity);
        stmt.setBigDecimal(3, Price);
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        
        BigDecimal id = new BigDecimal("0");
        if (rs.next()) {
            id = rs.getBigDecimal(1);
        }
        return id.toString(); 
    }
    
    public void updateProduct(String ID, String name, BigDecimal quantity, BigDecimal Price) throws Exception{
        PreparedStatement stmt = conn.prepareStatement("UPDATE PRODUCTS SET NAME = ?, QUANTITY = ?, PRICE = ? WHERE ID = ?");
        stmt.setString(1, name);
        stmt.setBigDecimal(2, quantity);
        stmt.setBigDecimal(3, Price);
        stmt.setString(4, ID);
        stmt.executeUpdate(); 
    }
    
    public void addAttribute(String productID, String name, String value) throws Exception{
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO ATTRIBUTES (PRODUCT_ID, NAME, VALUE) VALUES (?,?,?)");
        stmt.setString(1, productID);
        stmt.setString(2, name);
        stmt.setString(3, value);
        stmt.executeUpdate();
    }
    
    public ResultSet attributesQuery(String ID) throws Exception{
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM ATTRIBUTES WHERE PRODUCT_ID = ? ORDER BY NAME ASC");
        stmt.setString(1, ID);
        ResultSet rs = stmt.executeQuery();
        return rs; 
    }
    
    public ResultSet productQuery(String ID) throws Exception{
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM PRODUCTS WHERE ID = ? ORDER BY NAME ASC");
        stmt.setString(1, ID);
        ResultSet rs = stmt.executeQuery();
        return rs; 
    }
    
    public void deleteAttrByID(String ID) throws Exception {
        PreparedStatement stmt = conn.prepareStatement("SELECT PRODUCT_ID FROM ATTRIBUTES WHERE PRODUCT_ID = ?",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stmt.setString(1, ID);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {            
            rs.deleteRow();
        } 
    }
    
    public void deleteRowByID(String ID) throws Exception{
        PreparedStatement stmt = conn.prepareStatement("SELECT ID FROM PRODUCTS WHERE ID = ?",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stmt.setString(1, ID);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {            
            rs.deleteRow();
        } 
        
    }
    
    
}
