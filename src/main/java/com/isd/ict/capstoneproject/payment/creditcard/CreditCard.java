package com.isd.ict.capstoneproject.payment.creditcard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The {@link CreditCard creditCard} object wraps information about credit card.
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

    public String getProperDBDateString() {
        if (dateExpired.length() == 4) {
            var month = Integer.parseInt(dateExpired.substring(0, 2));
            var year = Integer.parseInt(dateExpired.substring(2)) + 2000;
            return year + "-" + month + "-" + "28";

        } else {
            return dateExpired;
        }
    }
}
