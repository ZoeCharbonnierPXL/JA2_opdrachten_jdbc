package be.pxl.ja2.jdbc;

import java.io.File;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertImage {
    public static void main(String[] args) {
        String updateSQL = "UPDATE Beers SET Image = ? WHERE Name = ?";
        try(Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3307/studentdb?useSSL=false", "user", "password")) {
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
            File file = new File("C:/Users/11400452/Documents/Switch2IT/SEMESTER_2/JavaAdvanced/JDBC/Jupiler.jpg");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
