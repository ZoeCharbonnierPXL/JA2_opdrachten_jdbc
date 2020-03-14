package be.pxl.ja2.jdbc;

import java.sql.*;
import java.sql.PreparedStatement;

public class Transactions {
    private static final String QUERY = "SELECT * FROM Beers WHERE Id = ?";
    private static final String UPDATE = "UPDATE Beers SET Stock = ? WHERE Id = ?";

    public static void main(String[] args) {
        try(Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3307/studentdb?useSSL=false", "user", "password")){
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            try(PreparedStatement queryStatement = connection.prepareStatement(QUERY);
            PreparedStatement updateStatement = connection.prepareStatement(UPDATE)) {
                setStock(updateStatement, 435, 60);
                setStock(updateStatement, 437, 15);

                double stock1 = 0;
                double stock2 = 0;

                while ((stock1 = getStock(queryStatement, 435)) > 50){
                    stock2 = getStock(queryStatement, 437);
                    setStock(updateStatement, 435, stock1 -= 1);
                    setStock(updateStatement, 437, stock2 -= 1);
                    connection.commit();
                    System.out.println("Stock Drie Fonteinen frambozenlambik = " + stock1 + "\t" + "Stock Drie Fonteinen kriek = " + stock2);
                }
                //connection.rollback(); WERKT NIET
                System.out.println("Stock Drie Fonteinen frambozenlambik = " + stock1 + "\t" + "Stock Drie Fonteinen kriek = " + stock2);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static double getStock(PreparedStatement queryStatement, int id) throws SQLException {
        double amount = 0;
        queryStatement.setInt(1, id);
        try(ResultSet result = queryStatement.executeQuery()) {
            if(result.next()) {
                amount = result.getDouble("Stock");
            }
        }
        return amount;
    }

    private static void setStock(PreparedStatement updateStatement, int id, double stock) throws SQLException {
        updateStatement.setInt(2, id);
        updateStatement.setDouble(1, stock);
        updateStatement.executeUpdate();
    }


}
