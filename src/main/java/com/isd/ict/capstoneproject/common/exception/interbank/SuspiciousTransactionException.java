package com.isd.ict.capstoneproject.common.exception.interbank;

/**
 * The {@link SuspiciousTransactionException SuspiciousTransactionException} exception for suspicious transaction.
 *
 *
 */
public class SuspiciousTransactionException extends PaymentException {
	public SuspiciousTransactionException() {
		super("ERROR: Suspicious Transaction Report!");
	}
}
