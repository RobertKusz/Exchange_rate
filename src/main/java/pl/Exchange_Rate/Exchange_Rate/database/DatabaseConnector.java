package pl.Exchange_Rate.Exchange_Rate.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String URL = "jdbc:mysql://localhost:3306/exchange_rate";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL,USER,PASSWORD);
    }
}
