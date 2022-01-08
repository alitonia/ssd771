package com.isd.ict.capstoneproject.controller.dto;

import com.isd.ict.capstoneproject.rental.Rental;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * The {@link DepositBikeInvoiceDTO depositBikeInvoiceDTO} object wraps information to display to user about deposit bike invoice.
 *
 */
@Builder
@Data
@AllArgsConstructor
public class DepositBikeInvoiceDTO {
    /**
     * Rental
     */
    private Rental rental;
    /**
     * Amount
     */
    private int amount;
}
