package com.isd.ict.capstoneproject.rental.strategy;

import com.isd.ict.capstoneproject.rental.Rental;

/**
 * The {@link RentalDefaultCostStrategy rentalDefaultCostStrategy} object provide functionalities for rental object.
 *
 *
 */
public class RentalDefaultCostStrategy implements IRentalCostStrategy {

    @Override
    public int calculateRentalCost(Rental rental) {
        IRentalCountingTimeStrategy countingTimeStrategy = rental.getRentalStrategy().getCountingTimeStrategy();
        return applyDefaultFormula(countingTimeStrategy.getCurrentDurationTimeInMinutes(rental));
    }

    /**
     * Default formula to calculate rental cost
     * @param rentalDuration
     * @return - rental cost
     */
    private int applyDefaultFormula(int rentalDuration) {
        if (rentalDuration <= 10) {
            return 0;
        }

        if (rentalDuration <= 30) {
            return 10000;
        }

        return 10000 + ((rentalDuration - 30) + 14) / 15 * 3000;
    }
}
