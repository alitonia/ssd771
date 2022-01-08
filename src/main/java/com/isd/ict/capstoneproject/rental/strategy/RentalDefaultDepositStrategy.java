package com.isd.ict.capstoneproject.rental.strategy;

import com.isd.ict.capstoneproject.rental.Rental;
/**
 * The {@link RentalDefaultDepositStrategy rentalDefaultDepositStrategy} object provide functionalities for rental object.
 *
 *
 */

public class RentalDefaultDepositStrategy implements IRentalDepositStrategy {

    @Override
    public int calculateRentalDeposit(Rental rental) {
        return applyFormula(rental.getBike().getType().getMonetaryValue());
    }

    /**
     * Formula to calculate rental
     * @param bikeMonetaryValue
     * @return - rental cost
     */
    int applyFormula(int bikeMonetaryValue) {
        return (int) Math.ceil(bikeMonetaryValue * 0.4);
    }
}
