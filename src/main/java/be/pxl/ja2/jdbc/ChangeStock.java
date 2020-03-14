package be.pxl.ja2.jdbc;

import java.sql.*;

public class ChangeStock {
    public static void main(String[] args) {
        String sqlStatement = "UPDATE Beers SET Stock = 50 WHERE Name like '%kriek%'";
        try(Connection connection =
                DriverManager.getConnection("jdbc:mariadb://localhost:3307/studentdb?useSSL=false", "user", "password");
                Statement statement = connection.createStatement()) {
            int result = statement.executeUpdate(sqlStatement);
            System.out.println("Updated rows: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
