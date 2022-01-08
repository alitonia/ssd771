package com.isd.ict.capstoneproject.payment.invoice;

import com.isd.ict.capstoneproject.payment.transaction.PaymentTransaction;
import com.isd.ict.capstoneproject.rental.Rental;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The {@link Invoice invoice} object wraps information about invoice.
 *
 *
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    public static final int TYPE_PAY = 1;
    public static final int TYPE_REFUND = -1;
    /**
     * transaction
     */
    private PaymentTransaction transaction;
    /**
     * rental
     */
    private Rental rental;
    /**
     * type of invoice
     */
    private int type;
    /**
     * amount
     */
    private volatile int amount;

}
