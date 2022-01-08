package com.isd.ict.capstoneproject.payment.transaction;

import com.isd.ict.capstoneproject.common.exception.db.DataSourceException;
import com.isd.ict.capstoneproject.common.exception.db.NotFoundException;
import com.isd.ict.capstoneproject.common.exception.db.NotUpdatedException;
import com.isd.ict.capstoneproject.payment.creditcard.CreditCardRepo;
import com.isd.ict.capstoneproject.payment.creditcard.CreditCardRepoImpl;
import com.isd.ict.capstoneproject.repository.ResultSetMappable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
/**
 * The {@link PaymentTransactionRepoImpl PaymentTransactionRepoImpl} class is repository of payment transaction.
 *
 *
 */
public class PaymentTransactionRepoImpl implements PaymentTransactionRepo, ResultSetMappable<PaymentTransaction> {

    /**
     * credit card repository
     */
    private final CreditCardRepo creditCardRepo;
    
    public PaymentTransactionRepoImpl() {
        this.creditCardRepo = new CreditCardRepoImpl();
    }

    /**
     * Get payment transaction by id
     *
     * @param id
     * @return {@link PaymentTransaction paymentTransaction}
     * @throws DataSourceException
     */
    @Override
    public PaymentTransaction getById(String id) throws DataSourceException {
        try {
            String sql = "select * from PaymentTransaction where id = '" + id + "'";

            ResultSet res = DATA_SOURCE.getConnection().createStatement().executeQuery(sql);
            if (res.next()) {
                return mapFromResultSet(res);
            }
            throw new NotFoundException();
        } catch (SQLException ex) {
            throw new DataSourceException(ex);
        }
    }

    /**
     * Insert payment transaction to DB
     *
     * @param transaction
     * @return {@link PaymentTransaction paymentTransaction}
     * @throws DataSourceException
     */
    @Override
    public PaymentTransaction insert(PaymentTransaction transaction) throws DataSourceException {
        try {
            String cardCode = transaction.getCard().getCardCode();
            if (Objects.isNull(creditCardRepo.getById(cardCode))) {
                creditCardRepo.insert(transaction.getCard());
            } else creditCardRepo.update(transaction.getCard());

            String sql = "insert into PaymentTransaction(id, cardCode, amount, contents, errorCode, createdAt) values " +
                    "(" + "'" + transaction.getTransactionId() + "'" + "," +
                    "'" + cardCode + "'" + "," +
                    "'" + transaction.getAmount() + "'" + "," +
                    "'" + transaction.getContents() + "'" + "," +
                    "'" + transaction.getErrorCode() + "'" + "," +
                    "'" + transaction.getCreatedAt() + "'" + ")";
            int noOfInserted = DATA_SOURCE.getConnection().createStatement().executeUpdate(sql);
            if (noOfInserted == 0) throw new NotUpdatedException("Not Inserted PaymentTransaction");
            return getById(transaction.getTransactionId());
        } catch (SQLException ex) {
            throw new DataSourceException(ex);
        }
    }

    /**
     * Convert result set to payment transaction
     *
     * @param res
     * @return {@link PaymentTransaction paymentTransaction}
     * @throws SQLException
     * @throws DataSourceException
     */
    @Override
    public PaymentTransaction mapFromResultSet(ResultSet res) throws SQLException, DataSourceException {
        PaymentTransaction transaction = PaymentTransaction.builder()
                .transactionId(res.getString("id"))
                .amount(res.getInt("amount"))
                .card(creditCardRepo.getById(res.getString("cardCode")))
                .contents(res.getString("contents"))
                .createdAt(res.getString("createdAt"))
                .errorCode(res.getString("errorCode"))
                .build();
        return transaction;
    }
}
