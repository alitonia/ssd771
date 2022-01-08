package com.isd.ict.capstoneproject.subsystem;


import com.isd.ict.capstoneproject.common.exception.interbank.PaymentException;
import com.isd.ict.capstoneproject.common.exception.interbank.UnrecognizedException;
import com.isd.ict.capstoneproject.payment.creditcard.CreditCard;
import com.isd.ict.capstoneproject.payment.transaction.PaymentTransaction;

/**
 * The {@link InterbankInterface interbankInterface} class is used to to make transaction
 *
 *
 */
public interface InterbankInterface {

	/**
	 * Pay order, and then return the payment transaction
	 *
	 * @param card     - the credit card used for payment
	 * @param amount   - the amount to pay
	 * @param contents - the transaction contents
	 * @return 			-if the payment is successful
	 * @throws PaymentException      if responded with a pre-defined error code
	 * @throws UnrecognizedException if responded with an unknown error code or
	 *                               something goes wrong
	 */
    PaymentTransaction pay(CreditCard card, int amount, String contents)
			throws PaymentException, UnrecognizedException;

	/**
	 * Refund, and then return the payment transaction
	 *
	 * @param card     - the credit card which would be refunded to
	 * @param amount   - the amount to refund
	 * @param contents - the transaction contents
	 * @return  - if the
	 *         payment is successful
	 * @throws PaymentException      if responded with a pre-defined error code
	 * @throws UnrecognizedException if responded with an unknown error code or
	 *                               something goes wrong
	 */
    PaymentTransaction refund(CreditCard card, int amount, String contents)
			throws PaymentException, UnrecognizedException;

}
