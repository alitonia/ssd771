package com.isd.ict.capstoneproject.subsystem.interbank;


import com.isd.ict.capstoneproject.common.exception.interbank.PaymentException;
import com.isd.ict.capstoneproject.common.exception.interbank.UnrecognizedException;
import com.isd.ict.capstoneproject.payment.creditcard.CreditCard;
import com.isd.ict.capstoneproject.payment.transaction.PaymentTransaction;
import com.isd.ict.capstoneproject.utils.Utils;

import java.util.logging.Logger;
/**
 * The {@link InterbankSubsystemController interbankSubsystemController} class provides functionalities for interbank.
 *
 * @author Group 3
 *
 */
public class InterbankSubsystemController {

	private static final Logger LOGGER = Utils.getLogger(InterbankSubsystemController.class.getName());
	/**
	 * interbank boundary
	 */
	private static final InterbankBoundary INTERBANK_BOUNDARY = new InterbankBoundary();
	/**
	 * interbank data conversion service
	 */
	private static final InterbankDataConverter INTERBANK_DATA_CONVERTER = new InterbankDataConverter();

	/**
	 * Refund, and then return the payment transaction
	 *
	 * @param card
	 * @param amount
	 * @param contents
	 * @return {@link PaymentTransaction paymentTransaction}
	 */
	public PaymentTransaction refund(CreditCard card, int amount, String contents) {
		String payload = INTERBANK_DATA_CONVERTER.convertToPayload(card, amount, contents, InterbankConfigs.REFUND_COMMAND);
		LOGGER.info(payload);
		String responseText = INTERBANK_BOUNDARY.query(InterbankConfigs.PROCESS_TRANSACTION_URL, payload);
		LOGGER.info(responseText);

		return INTERBANK_DATA_CONVERTER.convertResponse(responseText);
	}

	/**
	 * Pay order, and then return the payment transaction
	 *
	 * @param card
	 * @param amount
	 * @param contents
	 * @return {@link PaymentTransaction paymentTransaction}
	 * @throws UnrecognizedException
	 * @throws PaymentException
	 */
	public PaymentTransaction pay(CreditCard card, int amount, String contents) throws UnrecognizedException, PaymentException {
		String payload = INTERBANK_DATA_CONVERTER.convertToPayload(card, amount, contents, InterbankConfigs.PAY_COMMAND);
		LOGGER.info(payload);
		String responseText = INTERBANK_BOUNDARY.query(InterbankConfigs.PROCESS_TRANSACTION_URL, payload);
		LOGGER.info(responseText);

		return INTERBANK_DATA_CONVERTER.convertResponse(responseText);
	}

}
