package com.isd.ict.capstoneproject.payment.invoice;

import com.isd.ict.capstoneproject.common.exception.db.DataSourceException;
import com.isd.ict.capstoneproject.common.exception.db.NotFoundException;
import com.isd.ict.capstoneproject.common.exception.db.NotUpdatedException;
import com.isd.ict.capstoneproject.payment.transaction.PaymentTransactionRepo;
import com.isd.ict.capstoneproject.payment.transaction.PaymentTransactionRepoImpl;
import com.isd.ict.capstoneproject.rental.RentalService;
import com.isd.ict.capstoneproject.rental.RentalServiceImpl;
import com.isd.ict.capstoneproject.repository.ResultSetMappable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * The {@link InvoiceRepoImpl invoiceRepoImpl} class is repository of invoice.
 *
 * @author Group 3
 *
 */
public class InvoiceRepoImpl implements InvoiceRepo, ResultSetMappable<Invoice> {

    /**
     * rental repository
     */
    private RentalService rentalService;
    /**
     * payment transaction repository
     */
    private PaymentTransactionRepo paymentTransactionRepo;

    public InvoiceRepoImpl() {
        this.rentalService = new RentalServiceImpl();
        this.paymentTransactionRepo = new PaymentTransactionRepoImpl();
    }

    /**
     * Get invoice by id
     *
     * @param id
     * @return {@link Invoice invoice}
     * @throws DataSourceException
     */
    @Override
    public Invoice getById(Integer id) throws DataSourceException {
        try {
            String sql = "select * from Invoice where id = " + id;
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
     * Get deposit invoice by rental id
     *
     * @param rentalId
     * @return {@link Invoice invoice}
     * @throws DataSourceException
     */
    @Override
    public Invoice getDepositInvoiceByRentalId(int rentalId) throws DataSourceException {
        try {
            String sql = "select * from Invoice where rentalId = " + rentalId + " and type = " + Invoice.TYPE_PAY;
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
     * Insert invoice to DB
     *
     * @param invoice
     * @return {@link Invoice invoice}
     * @throws DataSourceException
     */
    @Override
    public Invoice insert(Invoice invoice) throws DataSourceException {
        try {
            String sql = "insert into Invoice(transactionId, type, rentalId, amount) values " +
                    "(" + "'" + invoice.getTransaction().getTransactionId() + "'" + "," +
                          "'" + invoice.getType()                           + "'" + "," +
                          "'" + invoice.getRental().getRentId()             + "'" + "," +
                          "'" + invoice.getAmount()                         + "'" + ")";
            PreparedStatement stm = DATA_SOURCE.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.execute();
            ResultSet res = stm.getGeneratedKeys();
            if (res.next()) {
                return getById(res.getInt(1));
            }
            throw new NotUpdatedException("Not Inserted Invoice");
        } catch (SQLException ex) {
            throw new DataSourceException(ex);
        }
    }

    /**
     * Convert result set to invoice
     *
     * @param res
     * @return {@link Invoice invoice}
     * @throws SQLException
     * @throws DataSourceException
     */
    @Override
    public Invoice mapFromResultSet(ResultSet res) throws SQLException, DataSourceException {
        Invoice invoice = Invoice.builder()
                .rental(rentalService.getById(res.getInt("rentalId")))
                .transaction(paymentTransactionRepo.getById(res.getString("transactionId")))
                .type(res.getInt("type"))
                .amount(res.getInt("amount"))
                .build();

        return invoice;
    }
}
