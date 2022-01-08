package com.isd.ict.capstoneproject.common.exception.interbank;
/**
 * The {@link NotEnoughTransactionInfoException notEnoughTransactionInfoException} exception for not enough transaction info.
 *
 */
public class NotEnoughTransactionInfoException extends PaymentException {

	public NotEnoughTransactionInfoException() {
	super("ERROR: Not Enough Transcation Information");
}
}
