package com.isd.ict.capstoneproject.rental.strategy;

import lombok.Builder;
import lombok.Data;
/**
 * The {@link RentalStrategy rentalStrategy} object provide functionalities for rental object.
 *
 *
 */
@Builder
@Data
public class RentalStrategy {
    /**
     * id
     */
    protected int id;
    /**
     * name
     */
    protected String name;
    /**
     * description
     */
    protected String description;
    /**
     * cost strategy
     */
    protected IRentalCostStrategy costStrategy;
    /**
     * deposit strategy
     */
    protected IRentalDepositStrategy depositStrategy;
    /**
     *  counting time strategy
     */
    protected IRentalCountingTimeStrategy countingTimeStrategy;
}
