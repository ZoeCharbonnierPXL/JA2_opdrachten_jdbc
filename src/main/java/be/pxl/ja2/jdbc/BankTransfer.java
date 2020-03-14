package be.pxl.ja2.jdbc;

import java.sql.*;
import java.sql.PreparedStatement;


public class BankTransfer {
    private static final String QUERY = "SELECT * FROM Accounts WHERE number = ?";
    private static final String UPDATE = "UPDATE Accounts SET Amount = ? WHERE number = ?";

    public static void main(String[] args) {
        try(Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3307/studentdb?useSSL=false", "user", "password")) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            try(PreparedStatement queryStatement = connection.prepareStatement(QUERY);
            PreparedStatement updateStatement = connection.prepareStatement(UPDATE)) {
                setAmount(updateStatement, 1, 5);
                setAmount(updateStatement, 2, 0);
                //connection.commit();
                double amount1 = 0;
                double amount2 = 0;

                while ((amount1 = getAmount(queryStatement, 1)) > 0) {
                    amount2 = getAmount(queryStatement, 2);
                    setAmount(updateStatement, 1, amount1 -= 1);
                    setAmount(updateStatement, 2, amount2 += 1);
                    System.out.println(amount1 + "\t" + amount2);
                    connection.commit();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                connection.rollback();
            } finally {
                //connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static double getAmount(PreparedStatement queryStatement, int number) throws SQLException {
        double amount = 0;
        queryStatement.setInt(1, number);
        try(ResultSet result = queryStatement.executeQuery()){
            if(result.next()) {
                amount = result.getDouble("amount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return amount;
    }

    private static void setAmount(PreparedStatement updateStatement, int number, double amount) throws SQLException {
        updateStatement.setInt(2, number);
        updateStatement.setDouble(1, amount);
        updateStatement.executeUpdate();
    }
}
