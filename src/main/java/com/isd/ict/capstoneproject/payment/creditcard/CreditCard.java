package com.isd.ict.capstoneproject.payment.creditcard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The {@link CreditCard creditCard} object wraps information about credit card.
 *
 * @author Group 3
 *
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditCard {
    /**
     * card code
     */
    private String cardCode;
    /**
     * owner
     */
    private String owner;
    /**
     * cvv code
     */
    private int cvvCode;
    /**
     * date expiration
     */
    private String dateExpired;
}
