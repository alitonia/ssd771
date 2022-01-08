package com.isd.ict.capstoneproject.bike;

import com.isd.ict.capstoneproject.bike.type.BikeType;
import com.isd.ict.capstoneproject.station.DockStation;
import lombok.*;

/**
 * The {@link Bike bike} object wraps information about bike.
 *
 * @author Group 3
 *
 */
@Getter
@Setter(AccessLevel.PACKAGE)
@Builder(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Bike {

    /**
     * Bike type
     */
    private BikeType type;
    /**
     * Barcode
     */
    private String barcode;
    /**
     * License plate number
     */
    private String licensePlateNumber;
    /**
     * Available
     */
    private boolean available;
    /**
     * Location
     */
    private DockStation location;
    /**
     * Battery percentage
     */
    private Integer batteryPercentage;

    public Bike(BikeType bikeType) {
        this.type = bikeType;
    }

    /**
     * Check if bike is available to rent
     *
     * @return -true if available
     */
    public boolean isAvailable() {
        return this.available;
    }

    /**
     * Get location of this bike
     *
     * @return {@link DockStation DockStation} - Location of this bike
     */
    public DockStation getLocation() {
        return this.location;
    }

    public Integer getBatteryPercentage() {
        if (this.type.isElectricType()) return this.batteryPercentage;
        else return null;
    }
}
