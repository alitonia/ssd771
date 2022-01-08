package com.isd.ict.capstoneproject.rental.strategy;

import com.isd.ict.capstoneproject.rental.Rental;

/**
 * The {@link IRentalDepositStrategy iRentalDepositStrategy} interface provide functionalities for rental object.
 *
 * @author Group 3
 *
 */
public interface IRentalDepositStrategy {
    /**
     * Calculate rental deposit
     *
     * @param rental
     * @return {@link Rental rental}
     */
    int calculateRentalDeposit(Rental rental);
}
