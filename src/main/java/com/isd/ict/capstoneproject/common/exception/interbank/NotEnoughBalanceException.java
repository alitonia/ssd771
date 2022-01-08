package com.isd.ict.capstoneproject.common.exception.interbank;
/**
 * The {@link NotEnoughBalanceException notEnoughBalanceException} exception for not enough balance.
 *
 *
 */
public class NotEnoughBalanceException extends PaymentException{

	public NotEnoughBalanceException() {
		super("ERROR: Not enough balance in card!");
	}

}
