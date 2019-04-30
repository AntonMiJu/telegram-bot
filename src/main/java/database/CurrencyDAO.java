package database;

import objects.Currency;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CurrencyDAO {
    private static final String DB_URL = System.getenv("DB_URL");
    private static final String USER = System.getenv("USER");
    private static final String PASS = System.getenv("PASS");

    public void save(String currency, String bid, String ask) {
        try (Connection connection = getConnection()) {
            PreparedStatement prepareStatement = connection.prepareStatement("UPDATE GOVERLA SET BID = ?, ASK = ? WHERE CURRENCY = ?");

            prepareStatement.setString(3, currency);
            prepareStatement.setString(1, bid);
            prepareStatement.setString(2, ask);

            int response = prepareStatement.executeUpdate();
            System.out.println(currency + " " + bid + " " + ask + " " + response);
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    public List<Currency> getRate(){
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT CURRENCY, BID, ASK FROM GOVERLA");

            List<Currency> currencies = new ArrayList<>();
            while (resultSet.next()) {
                Currency currency = new Currency(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
                currencies.add(currency);
            }
            return currencies;
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return null;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
