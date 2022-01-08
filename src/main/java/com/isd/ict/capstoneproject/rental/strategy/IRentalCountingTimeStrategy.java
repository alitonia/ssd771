package com.isd.ict.capstoneproject.rental.strategy;

import com.isd.ict.capstoneproject.rental.Rental;

/**
 * The {@link IRentalCountingTimeStrategy iRentalCountingTimeStrategy} interface provide functionalities for rental object.
 *
 *
 */
public interface IRentalCountingTimeStrategy {

    int getCurrentDurationTimeInMinutes(Rental rental);
}
