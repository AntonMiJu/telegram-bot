package database;

import objects.Currency;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class CurrencyDAO {
    private static final String getAll = "SELECT * FROM CURRENCY;";
    private static final String getByName = "SELECT * FROM CURRENCY WHERE CURRENCY = :name ;";

    @PersistenceContext
    private EntityManager entityManager;

    public List<Currency> getAll() {
        return entityManager.createNativeQuery(getAll, Currency.class)
                .getResultList();
    }

    public Currency getByName(String name) {
        return (Currency) entityManager.createNativeQuery(getByName, Currency.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public Currency get(Long id) {
        return entityManager.find(Currency.class, id);
    }

    public Currency save(Currency currency) {
        entityManager.persist(currency);
        return currency;
    }

    public Currency update(Currency currency) {
        return entityManager.merge(currency);
    }

    public void delete(Long id) {
        Currency currency = entityManager.find(Currency.class, id);
        entityManager.remove(currency);
    }


//    public void save(String currency, String bid, String ask) {
//        try (Connection connection = getConnection()) {
//            PreparedStatement prepareStatement = connection.prepareStatement("UPDATE GOVERLA SET BID = ?, ASK = ? WHERE CURRENCY = ?");
//
//            prepareStatement.setString(3, currency);
//            prepareStatement.setString(1, bid);
//            prepareStatement.setString(2, ask);
//
//            int response = prepareStatement.executeUpdate();
//            System.out.println(currency + " " + bid + " " + ask + " " + response);
//        } catch (SQLException e) {
//            System.err.println("500: System exception.");
//            e.printStackTrace();
//        }
//    }

//    public List<Currency> getRate(){
//        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
//            ResultSet resultSet = statement.executeQuery("SELECT CURRENCY, BID, ASK FROM GOVERLA");
//
//            List<Currency> currencies = new ArrayList<>();
//            while (resultSet.next()) {
//                Currency currency = new Currency(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
//                currencies.add(currency);
//            }
//            return currencies;
//        } catch (SQLException e) {
//            System.err.println("500: System exception.");
//            e.printStackTrace();
//        }
//        return null;
//    }
}
