package com.isd.ict.capstoneproject.rental;

import com.isd.ict.capstoneproject.bike.Bike;
import com.isd.ict.capstoneproject.rental.strategy.RentalStrategy;
import com.isd.ict.capstoneproject.user.AppUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * The {@link Rental rental} object wraps information about rental.
 *
 * @author Group 3
 *
 */
@Data
@Builder
@AllArgsConstructor
public class Rental {
    /**
     * rental id
     */
    protected int rentId;
    /**
     * app user
     */
    protected AppUser appUser;
    /**
     * renting bike
     */
    protected Bike bike;
    /**
     * rental duration by minutes
     */
    protected int rentalDurationByMinutes;
    /**
     * start time
     */
    protected LocalDateTime startTime;
    /**
     * finish time
     */
    protected LocalDateTime finishTime;
    /**
     * status
     */
    protected RentalStatus status;
    /**
     * rental strategy
     */
    protected RentalStrategy rentalStrategy;

    protected Rental(AppUser appUser, Bike bike, RentalStrategy rentalStrategy) {
        this.appUser = appUser;
        this.bike = bike;
        this.rentalStrategy = rentalStrategy;
        this.status = RentalStatus.NEW;
    }
}
