package com.isd.ict.capstoneproject.controller.dto;

import com.isd.ict.capstoneproject.rental.Rental;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The {@link RentalDetailDTO rentDetailDTO} object wraps information to display to user about rental information.
 *
 * @author Group 3
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RentalDetailDTO {
    private Rental rental;
    private int depositAmount;
    private int costAmount;
}
