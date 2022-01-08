package com.isd.ict.capstoneproject.common.exception.interbank;

/**
 * The {@link InvalidCardException invalidCardException} exception for invalid card.
 *
 * @author Group 3
 *
 */
public class InvalidCardException extends PaymentException {
	public InvalidCardException() {
		super("ERROR: Invalid card!");
	}
}
