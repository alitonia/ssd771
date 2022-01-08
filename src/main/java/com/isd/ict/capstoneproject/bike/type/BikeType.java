package com.isd.ict.capstoneproject.bike.type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The {@link BikeType bikeType} object wraps information about bike type.
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BikeType {
    /**
     * Bike type's id
     */
    private int bikeTypeId;
    /**
     * Bike type name
     */
    private String bikeTypeName;
    /**
     * Monetary value
     */
    private int monetaryValue;
    /**
     * Electric type
     */
    private boolean electricType;
    /**
     * Number of saddles
     */
    private int noOfSaddles;
    /**
     * Number of pedals
     */
    private int noOfPedals;
    /**
     * Number of rear seats
     */
    private int noOfRearSeats;
    /**
     * Description
     */
    private String description;
    /**
     * Bike type image URL
     */
    private String bikeTypeImageURL;

    public BikeType(int bikeTypeId) {
        this.bikeTypeId = bikeTypeId;
    }

    /**
     * Check if bike is electric bike
     * @return - true if bike is electric
     */
    public boolean isElectricType() {
        return this.electricType;
    }

}
