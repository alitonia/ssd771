package com.isd.ict.capstoneproject.payment;

import com.isd.ict.capstoneproject.common.exception.db.DataSourceException;
import com.isd.ict.capstoneproject.common.exception.interbank.InvalidCardException;
import com.isd.ict.capstoneproject.common.exception.interbank.PaymentException;
import com.isd.ict.capstoneproject.common.exception.interbank.UnrecognizedException;
import com.isd.ict.capstoneproject.payment.creditcard.CreditCard;
import com.isd.ict.capstoneproject.payment.creditcard.CreditCardValidator;
import com.isd.ict.capstoneproject.payment.invoice.Invoice;
import com.isd.ict.capstoneproject.payment.invoice.InvoiceRepo;
import com.isd.ict.capstoneproject.payment.invoice.InvoiceRepoImpl;
import com.isd.ict.capstoneproject.payment.transaction.PaymentTransaction;
import com.isd.ict.capstoneproject.payment.transaction.PaymentTransactionRepo;
import com.isd.ict.capstoneproject.payment.transaction.PaymentTransactionRepoImpl;
import com.isd.ict.capstoneproject.rental.Rental;
import com.isd.ict.capstoneproject.subsystem.InterbankInterface;
import com.isd.ict.capstoneproject.subsystem.InterbankSubsystem;
import com.isd.ict.capstoneproject.utils.Utils;

import java.util.Map;
import java.util.logging.Logger;

/**
 * The {@link PaymentServiceImpl paymentController} class provides functionalities for rental charge.
 *
 * @author Group 3
 *
 */
public class PaymentServiceImpl implements PaymentService {
    private static final Logger LOGGER = Utils.getLogger(PaymentServiceImpl.class.getName());

    /**
     * Invoice repository
     */
    private InvoiceRepo invoiceRepo;
    /**
     * Card validator
     */
    private CreditCardValidator creditCardValidator;
    /**
     * Interbank interface sub system
     */
    private InterbankInterface interbankInterface;

    /**
     * Payment transaction repository
     */
    private PaymentTransactionRepo paymentTransactionRepo;

    public PaymentServiceImpl() {
        this.interbankInterface = new InterbankSubsystem();
        this.invoiceRepo = new InvoiceRepoImpl();
        this.creditCardValidator = new CreditCardValidator();
        this.paymentTransactionRepo = new PaymentTransactionRepoImpl();
    }

    /**
     * Pay order, and then return the result with a message.
     *
     * @param amount         - the amount to pay
     * @param contents       - the transaction contents
     * @param cardCode     - the card number
     * @param owner - the card holder name
     * @param dateExpired - the expiration date in the format "mm/yy"
     * @param cvvCode   - the cvv/cvc code of the credit card
     * @return {@link Map Map} represent the payment result with a
     *         message.
     */
    public PaymentTransaction payRent(int amount,
                                      String contents,
                                      String cardCode,
                                      String owner,
                                      String dateExpired,
                                      String cvvCode) throws PaymentException, UnrecognizedException {
        creditCardValidator.validateCreditCardInfo(cardCode, owner, dateExpired, cvvCode);
        CreditCard card = CreditCard.builder()
                .cardCode(cardCode)
                .owner(owner)
                .cvvCode(Integer.parseInt(cvvCode))
                .dateExpired(getExpirationDate(dateExpired))
                .build();
        return interbankInterface.pay(card, amount, contents);
    }

    /**
     * Refund rent, then return the result with a message.
     *
     * @param amount         - the amount to pay
     * @param contents       - the transaction contents
     * @return {@link Map Map} represent the payment result with a
     *         message.
     */
    public PaymentTransaction refundRent(int amount, String contents, CreditCard card) {
        return interbankInterface.refund(card, amount, contents);
    }

    @Override
    public CreditCard getCreditCardToRefund(int rentalId) throws DataSourceException {
        Invoice depositInvoice = invoiceRepo.getDepositInvoiceByRentalId(rentalId);
        CreditCard card = paymentTransactionRepo
                .getById(depositInvoice.getTransaction().getTransactionId())
                .getCard();
        return card;
    }

    /**
     * Convert which should be in the format "mm/yy", and then
     * return a {@link String String} representing the date in the
     * required format "mmyy" .
     *
     * @param date - the {@link String String} represents the input date
     * @return {@link String String} - date representation of the required
     *         format
     * @throws InvalidCardException - if the string does not represent a valid date
     *                              in the expected format
     */
    private String getExpirationDate(String date) throws InvalidCardException {
        String[] strs = date.split("/");
        String expirationDate = strs[0] + strs[1];
        return expirationDate;
    }


    @Override
    public void insertInvoice(PaymentTransaction transaction, Rental rental, int invoiceType) throws DataSourceException {
        paymentTransactionRepo.insert(transaction);

        // save invoice
        Invoice invoiceDao = Invoice.builder()
                .rental(rental)
                .transaction(transaction)
                .amount(transaction.getAmount())
                .type(invoiceType)
                .build();
        invoiceRepo.insert(invoiceDao);
    }
}
