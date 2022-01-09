package com.isd.ict.capstoneproject.payment.creditcard;

import com.isd.ict.capstoneproject.common.exception.db.DataSourceException;
import com.isd.ict.capstoneproject.common.exception.db.NotFoundException;
import com.isd.ict.capstoneproject.common.exception.db.NotUpdatedException;
import com.isd.ict.capstoneproject.repository.ResultSetMappable;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The {@link CreditCardRepoImpl creditCardRepoImpl} class is repository of credit card.
 */
public class CreditCardRepoImpl implements CreditCardRepo, ResultSetMappable<CreditCard> {

    /**
     * Get credit card by id
     *
     * @param id
     * @return {@link CreditCard creditCard}
     * @throws DataSourceException
     */
    @Override
    public CreditCard getById(String id) throws DataSourceException {
        try {
            String sql = "select * from CreditCard where cardCode = '" + id + "'";
            System.out.println("Get it");
            System.out.println(sql);

            ResultSet res = DATA_SOURCE.getConnection().createStatement().executeQuery(sql);
            System.out.println("Get it1");
            System.out.println(res);

            if (res.next()) {
                return mapFromResultSet(res);
            }
            System.out.println("Not Get it2");
            return null;
//            throw new NotFoundException();
        } catch (SQLException ex) {
            System.out.println("F it");

            throw new DataSourceException(ex);
        }
    }

    /**
     * Insert credit card to DB
     *
     * @param card
     * @return {@link CreditCard creditCard}
     * @throws DataSourceException
     */
    @Override
    public CreditCard insert(CreditCard card) throws DataSourceException {
        try {
            String sql = "insert into CreditCard(cardCode, owner, dateExpired, cvvCode) values " +
                    "(" + "'" + card.getCardCode() + "'" + "," +
                    "'" + card.getOwner() + "'" + "," +
                    "'" + card.getDateExpired() + "'" + "," +
                    "'" + card.getCvvCode() + "'" + ")";
            int noOfChanged = DATA_SOURCE.getConnection().createStatement().executeUpdate(sql);
            if (noOfChanged == 0) throw new NotUpdatedException("Not Inserted CreditCard");
            return getById(card.getCardCode());
        } catch (SQLException ex) {
            throw new DataSourceException(ex);
        }
    }

    /**
     * Update credit card
     *
     * @param card
     * @return {@link CreditCard creditCard}
     * @throws DataSourceException
     */
    @Override
    public CreditCard update(CreditCard card) throws DataSourceException {
        try {
            System.out.println("Potential faulty impl");
            System.out.println(card.getDateExpired());

            var fourDigitsDate = card.getDateExpired();

            String sql = "update CreditCard set " +
                    "owner = " + "'" + card.getOwner() + "'" + "," +
                    "dateExpired = " + "'" + card.getDateExpired() + "'" + "," +
                    "cvvCode = " + "'" + card.getCvvCode() + "'" + " " +
                    "where cardCode = " + "'" + card.getCardCode() + "'";

            System.out.println("Update");
            System.out.println();
            System.out.println(sql);

            int noOfChanged = DATA_SOURCE.getConnection().createStatement().executeUpdate(sql);
            if (noOfChanged == 0)
                throw new NotUpdatedException("Not Updated CreditCard with Card Code = " + card.getCardCode());
            return getById(card.getCardCode());
        } catch (SQLException ex) {
            throw new DataSourceException(ex);
        }
    }

    /**
     * Convert result set to credit card
     *
     * @param res
     * @return {@link CreditCard creditCard}
     * @throws SQLException
     */
    @Override
    public CreditCard mapFromResultSet(ResultSet res) throws SQLException {
        System.out.println("Hello in credit reader");

        System.out.println(res.getString("dateExpired"));
        System.out.println(res);
        System.out.println(res);



        CreditCard card = CreditCard.builder()
                .cardCode(res.getString("cardCode"))
                .owner(res.getString("owner"))
                .dateExpired(res.getString("dateExpired"))
                .cvvCode(Integer.parseInt(res.getString("cvvCode")))
                .build();

        System.out.println(card);

        return card;
    }
}
