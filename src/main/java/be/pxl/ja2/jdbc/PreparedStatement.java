package be.pxl.ja2.jdbc;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class PreparedStatement {
    public static void main(String[] args) {
        String sqlStatement = "SELECT Name, Price, Alcohol FROM Beers WHERE Alcohol = ?";
        try(Connection connection =
                    DriverManager.getConnection("jdbc:mariadb://localhost:3307/studentdb?useSSL=false", "user", "password");
                    java.sql.PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
                    statement.setDouble(1, 2.0);
                    ResultSet result = statement.executeQuery();
                    while(result.next()) {
                        String beerName = result.getString(1);
                        double alcohol = result.getDouble(2);
                        double price = result.getDouble(3);
                        System.out.format("%s %s %s%n", beerName, alcohol, price);
                    }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
