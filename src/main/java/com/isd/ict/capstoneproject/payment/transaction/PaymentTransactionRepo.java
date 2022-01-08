package com.isd.ict.capstoneproject.payment.transaction;

import com.isd.ict.capstoneproject.common.exception.db.DataSourceException;
import com.isd.ict.capstoneproject.repository.BaseRepo;
/**
 * The {@link PaymentTransactionRepo paymentTransactionRepo} interface provides functionalities of payment transaction's repository.
 *
 * @author Group 3
 *
 */

public interface PaymentTransactionRepo extends BaseRepo<PaymentTransaction, String>{
    /**
     * Insert payment transaction to DB
     * @param transaction
     * @return {@link PaymentTransaction paymentTransaction}
     * @throws DataSourceException
     */
    PaymentTransaction insert(PaymentTransaction transaction) throws DataSourceException;
}
