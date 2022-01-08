package com.isd.ict.capstoneproject.common.exception.interbank;

/**
 * The {@link InternalServerErrorException internalServerErrorException} exception for internal server error.
 *
 */
public class InternalServerErrorException extends PaymentException {

	public InternalServerErrorException() {
		super("ERROR: Internal Server Error!");
	}

}
