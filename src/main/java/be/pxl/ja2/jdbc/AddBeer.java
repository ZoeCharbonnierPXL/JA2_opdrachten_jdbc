package be.pxl.ja2.jdbc;

import javax.swing.plaf.nimbus.State;
import java.sql.*;

public class AddBeer {
    public static void main(String[] args) {
        String sqlStatement =
                "INSERT INTO Beers (Name, Price, Stock, Alcohol) " +
                "VALUES ('Wilderen kriek', 3.0, 80, 2)";

        try(Connection connection =
                    DriverManager.getConnection("jdbc:mariadb://localhost:3307/studentdb?useSSL=false", "user", "password");
                    Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            try(ResultSet result = statement.getGeneratedKeys()) {
                if(result.next()) {
                    int id = result.getInt(1);
                    System.out.println("Generated key: " + id);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
