package com.urlshortener.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String JDBC_URL = "jdbc:h2:./urlshortenerdb";

    static {
        try (Connection conn = getConnection()) {
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS users (username VARCHAR(255) PRIMARY KEY, password VARCHAR(255))");
            stmt.execute("CREATE TABLE IF NOT EXISTS urls (short_code VARCHAR(20) PRIMARY KEY, original_url VARCHAR(2048), username VARCHAR(255))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, "sa", "");
    }
}
