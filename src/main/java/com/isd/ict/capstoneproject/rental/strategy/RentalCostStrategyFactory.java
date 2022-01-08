package com.isd.ict.capstoneproject.rental.strategy;

/**
 * The {@link RentalCostStrategyFactory rentalCostStrategyFactory} is used for picking the correct cost strategy for Rental both when create new Rental and reload Rental from Database.
 *
 */
public class RentalCostStrategyFactory {

    public IRentalCostStrategy create(String strategyType) {
        switch (strategyType) {
            default: {
                return new RentalDefaultCostStrategy();
            }
        }
    }
}
