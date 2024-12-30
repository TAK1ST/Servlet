/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author vothimaihoa
 */
public class DBUtils {

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private static final String DB_NAME = "MvcDemo";
    private static final String DB_USER_NAME = "root";
    private static final String DB_PASSWORD = "1234"; // cac ban nen de la 12345 vi de PE de vay

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        try {
            String url = "jdbc:mysql://localhost:3306/" + DB_NAME + "?useSSL=false&allowPublicKeyRetrieval=true";
            conn = DriverManager.getConnection(url, DB_USER_NAME, DB_PASSWORD);
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
            throw e;
        }
        return conn;
    }
}
