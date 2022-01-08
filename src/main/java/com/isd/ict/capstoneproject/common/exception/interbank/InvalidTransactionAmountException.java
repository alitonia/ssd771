package com.isd.ict.capstoneproject.common.exception.interbank;

/**
 * The {@link InvalidTransactionAmountException invalidTransactionAmountException} exception for invalid transaction amount.
 *
 * @author Group 3
 *
 */
public class InvalidTransactionAmountException extends PaymentException {
	public InvalidTransactionAmountException() {
		super("ERROR: Invalid Transaction Amount!");
	}
}
