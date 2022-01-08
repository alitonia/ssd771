package com.isd.ict.capstoneproject.rental.strategy;

/**
 * The {@link RentalCountingTimeStrategyFactory rentalCountingTimeStrategyFactory} is used for picking the correct counting time strategy for Rental both when create new Rental and reload Rental from Database.
 * @author thanhld
 */
public class RentalCountingTimeStrategyFactory {

    public IRentalCountingTimeStrategy create(String strategyType) {
        switch (strategyType) {
            default: {
                return new RentalDefaultCountingTimeStrategy();
            }
        }
    }
}
