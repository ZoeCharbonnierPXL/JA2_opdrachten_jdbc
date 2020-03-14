package be.pxl.ja2.jdbc;

import java.sql.*;

public class ConnectDB {
    public static void main(String[] args) {
        try(Connection connection =
                    DriverManager.getConnection("jdbc:mariadb://localhost:3307/studentdb?useSSL=false", "user", "password");
                    Statement statement = connection.createStatement()){
            System.out.println("Connection OK");
        } catch (Exception e) {
            System.out.println("Oops, something went wrong!");
            e.printStackTrace(System.err);
        }
    }
}
