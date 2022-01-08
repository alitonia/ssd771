package com.isd.ict.capstoneproject.payment;

import com.isd.ict.capstoneproject.common.exception.db.DataSourceException;
import com.isd.ict.capstoneproject.common.exception.interbank.PaymentException;
import com.isd.ict.capstoneproject.common.exception.interbank.UnrecognizedException;
import com.isd.ict.capstoneproject.payment.creditcard.CreditCard;
import com.isd.ict.capstoneproject.payment.transaction.PaymentTransaction;
import com.isd.ict.capstoneproject.rental.Rental;
/**
 * The {@link PaymentService paymentService} interface provides functionalities of payment transaction service.
 *
 * @author Group 3
 *
 */
public interface PaymentService {
    /**
     * Pay rent
     * @param amount
     * @param contents
     * @param cardCode
     * @param owner
     * @param dateExpired
     * @param cvvCode
     * @return {@link PaymentTransaction paymentTransaction}
     * @throws PaymentException
     * @throws UnrecognizedException
     */
    PaymentTransaction payRent(int amount, String contents,
                               String cardCode, String owner, String dateExpired, String cvvCode)
            throws PaymentException, UnrecognizedException;

    /**
     * Refund rent
     * @param amount
     * @param contents
     * @param card
     * @return {@link PaymentTransaction paymentTransaction}
     * @throws PaymentException
     * @throws UnrecognizedException
     */
    PaymentTransaction refundRent(int amount, String contents, CreditCard card)
            throws PaymentException, UnrecognizedException;

    /**
     * Get credit card to refund
     * @param rentalId
     * @return {@link CreditCard creditCard}
     * @throws DataSourceException
     */
    CreditCard getCreditCardToRefund(int rentalId) throws DataSourceException;

    /**
     * Insert invoice to database
     * @param transaction
     * @param rental
     * @param invoiceType
     * @throws DataSourceException
     */
    void insertInvoice(PaymentTransaction transaction, Rental rental, int invoiceType)
            throws DataSourceException;
}
