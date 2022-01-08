package com.isd.ict.capstoneproject.rental.strategy;

import com.isd.ict.capstoneproject.rental.Rental;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * The {@link RentalDefaultCountingTimeStrategy rentalDefaultCountingTimeStrategy} object provide functionalities for rental strategy.
 *
 * @author Group 3
 *
 */
public class RentalDefaultCountingTimeStrategy implements IRentalCountingTimeStrategy{

    @Override
    public int getCurrentDurationTimeInMinutes(Rental rental) {
        return (int) ChronoUnit.MINUTES.between(rental.getStartTime(), LocalDateTime.now());
    }
}
