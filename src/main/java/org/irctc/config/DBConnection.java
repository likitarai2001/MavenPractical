package org.irctc.config;

import java.sql.*;

public class DBConnection {
    String url = "jdbc:mysql://localhost:3306/ticket_booking";
    String username = "root";
    String password = "Simform@123";

    public Connection connect(){
        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static void createPassengerTable(Connection connection) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS `passenger` (" +
                "`id` INT NOT NULL AUTO_INCREMENT," +
                "`userID` VARCHAR(6) NOT NULL," +
                "`name` VARCHAR(45) NULL," +
                "`trainNo` INT NULL," +
                "`count` INT NULL," +
                "`sum` INT NULL," +
                "PRIMARY KEY (`id`));";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        System.out.println("Table created successfully!");
        statement.close();
    }
}
