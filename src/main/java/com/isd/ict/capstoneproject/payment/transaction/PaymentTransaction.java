package com.isd.ict.capstoneproject.payment.transaction;

import com.isd.ict.capstoneproject.payment.creditcard.CreditCard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The {@link PaymentTransaction paymentTransaction} object wraps information about payment transaction.
 *
 *
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentTransaction {
    /**
     * transaction id
     */
    private String transactionId;
    /**
     * credit card
     */
    private CreditCard card;
    /**
     * error code
     */
    private String errorCode;
    /**
     * content
     */
    private String contents;
    /**
     * amount
     */
    private int amount;
    /**
     * created at
     */
    private String createdAt;

}
