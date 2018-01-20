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
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM PRODUCTS WHERE LOWER(NAME) LIKE LOWER(?) ORDER BY ID ASC");
        if (onlyAvailable) stmt = conn.prepareStatement("SELECT * FROM PRODUCTS WHERE LOWER(NAME) LIKE LOWER(?) AND QUANTITY > 0 ORDER BY ID ASC");
        stmt.setString(1, "%" + searchString + "%");
        ResultSet rs = stmt.executeQuery();
        return rs; 
    }
    
    
}
