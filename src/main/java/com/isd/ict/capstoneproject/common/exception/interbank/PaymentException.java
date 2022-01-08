package com.isd.ict.capstoneproject.common.exception.interbank;

/**
 * The {@link PaymentException paymentException} exception for payment exception.
 *
 * @author Group 3
 *
 */
public class PaymentException extends RuntimeException {
	public PaymentException(String message) {
		super(message);
	}
}
