package be.pxl.ja2.jdbc;

import java.sql.*;

public class SearchBeers {

	public static void main(String[] args) {
		String sqlStatement1 =
				"SELECT Name, Price, Alcohol " +
				"FROM Beers " +
				"ORDER BY Alcohol";

		String sqlStatement2 =
				"SELECT Name, Price, Alcohol " +
				"FROM Beers " +
				"WHERE Alcohol = 1";

		String sqlStatement3 =
				"SELECT a.Name, a.Alcohol, a.Price, b.Name, c.Category " +
				"FROM Beers a " +
				"JOIN Brewers b " +
				"ON a.BrewerId = b.Id " +
				"JOIN Categories c on a.CategoryId = c.Id";

		try(Connection connection =
					DriverManager.getConnection("jdbc:mariadb://localhost:3307/studentdb?useSSL=false", "user", "password");
					Statement statement = connection.createStatement();
					ResultSet result = statement.executeQuery(sqlStatement3)) {

			while (result.next()) {
				String beerName = result.getString(1);
				double alcohol = result.getDouble(2);
				double price = result.getDouble(3);
				String brewerName = result.getString(4);
				String category = result.getString(5);
				//System.out.format("%s %s %s%n", beerName, alcohol, price);
				System.out.format("%s %s %s %s %s%n", beerName, alcohol, price, brewerName, category);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
