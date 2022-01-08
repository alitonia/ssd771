package com.isd.ict.capstoneproject.rental.strategy;

/**
 * The {@link RentalDepositStrategyFactory rentalDepositStrategyFactory} is used for picking the correct deposit strategy for Rental both when create new Rental and reload Rental from Database.
 *
 */
public class RentalDepositStrategyFactory {

    public IRentalDepositStrategy create(String strategyType) {
        switch (strategyType) {
            default: {
                return new RentalDefaultDepositStrategy();
            }
        }
    }
}
