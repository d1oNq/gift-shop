package com.giftshop.db;

import com.giftshop.log.GiftLogger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    private static Connection conn;

    public static Connection getConn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/giftshop";
            conn = DriverManager.getConnection(url, "root", "3216549870");
            GiftLogger.logInfo("Database connection established successfully");
        } catch (ClassNotFoundException e) {
            GiftLogger.logFatal("JDBC driver class not found: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            GiftLogger.logFatal("Error establishing database connection: " + e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }
}
