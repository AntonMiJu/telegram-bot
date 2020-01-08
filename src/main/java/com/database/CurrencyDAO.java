package com.database;

import com.objects.Currency;

import javax.persistence.*;
import java.util.List;

public class CurrencyDAO {
    private static final String getAll = "SELECT * FROM CURRENCY ORDER BY ID;";
    private static final String getByName = "SELECT * FROM CURRENCY WHERE CURRENCY = :name ;";

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public List<Currency> getAll() {
        return getEntityManager().createNativeQuery(getAll, Currency.class)
                .getResultList();
    }

    public Currency getByName(String name) {
        try {
            return (Currency) getEntityManager().createNativeQuery(getByName, Currency.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Not found currency " + name);
        }
        return null;
    }

    public Currency get(Long id) {
        return getEntityManager().find(Currency.class, id);
    }

    public void save(Currency currency) {
        EntityTransaction tr = entityManager.getTransaction();

        tr.begin();
        getEntityManager().persist(currency);
        tr.commit();
    }

    public void update(Currency currency) {
        EntityTransaction tr = entityManager.getTransaction();

        tr.begin();
        getEntityManager().merge(currency);
        tr.commit();
    }

    public void delete(Long id) {
        Currency currency = getEntityManager().find(Currency.class, id);
        EntityTransaction tr = entityManager.getTransaction();

        tr.begin();
        getEntityManager().remove(currency);
        tr.commit();
    }

    private EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null)
            entityManagerFactory = Persistence.createEntityManagerFactory("entity-manager");
        return entityManagerFactory;
    }

    private EntityManager getEntityManager() {
        if (entityManager == null)
            entityManager = getEntityManagerFactory().createEntityManager();
        return entityManager;
    }

    /**
     * below is pure way to connect with DB without any frameworks
     */

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
