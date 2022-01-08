package com.isd.ict.capstoneproject.common.exception.interbank;

/**
 * The {@link InvalidVersionException invalidVersionException} exception for invalid version.
 *
 */
public class InvalidVersionException extends PaymentException{
	public InvalidVersionException() {
		super("ERROR: Invalid Version Information!");
	}
}
