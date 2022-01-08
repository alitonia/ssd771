package com.isd.ict.capstoneproject.subsystem;

import com.isd.ict.capstoneproject.common.exception.interbank.PaymentException;
import com.isd.ict.capstoneproject.common.exception.interbank.UnrecognizedException;
import com.isd.ict.capstoneproject.payment.creditcard.CreditCard;
import com.isd.ict.capstoneproject.payment.transaction.PaymentTransaction;
import com.isd.ict.capstoneproject.subsystem.interbank.InterbankSubsystemController;

/**
 * The {@link InterbankSubsystem InterbankSubsystem} class is used to communicate with the
 * Interbank to make transaction.
 * @author Group 3
 */
public class InterbankSubsystem implements InterbankInterface {

	/**
	 * Represent the controller of the subsystem
	 */
	private final InterbankSubsystemController ctrl;

	/**
	 * Initializes a newly created {@code InterbankSubsystem} object so that it
	 * represents an Interbank subsystem.
	 */
	public InterbankSubsystem() {
		this.ctrl = new InterbankSubsystemController();
	}

	/**
	 * Pay order, and then return the payment transaction
	 *
	 * @param card     - the credit card used for payment
	 * @param amount   - the amount to pay
	 * @param contents - the transaction contents
	 * @return {@link PaymentTransaction paymentTransaction}
	 * @throws UnrecognizedException
	 * @throws PaymentException
	 */
	public PaymentTransaction pay(CreditCard card, int amount, String contents) throws UnrecognizedException, PaymentException {
		PaymentTransaction transaction = ctrl.pay(card, amount, contents);
		return transaction;
	}

	/**
	 * Refund, and then return the payment transaction
	 *
	 * @param card     - the credit card which would be refunded to
	 * @param amount   - the amount to refund
	 * @param contents - the transaction contents
	 * @return {@link PaymentTransaction paymentTransaction}
	 */
	public PaymentTransaction refund(CreditCard card, int amount, String contents) {
		PaymentTransaction transaction = ctrl.refund(card, amount, contents);
		return transaction;
	}
}
