package com.isd.ict.capstoneproject.rental.strategy;

import com.isd.ict.capstoneproject.rental.Rental;

/**
 * The {@link IRentalCostStrategy iRentalCostStrategy} interface provide functionalities for rental object.
 *
 *
 */
public interface IRentalCostStrategy {
    /**
     * Calculate rental cost
     *
     * @param rental
     * @return {@link Rental rental}
     */
    int calculateRentalCost(Rental rental);
}
