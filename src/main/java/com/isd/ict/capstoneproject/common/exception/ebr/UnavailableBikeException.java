package com.isd.ict.capstoneproject.common.exception.ebr;

/**
 * The {@link UnavailableBikeException unavailableBikeException} exception for unavailable bike.
 *
 *
 */
public class UnavailableBikeException extends EBRException {
    public UnavailableBikeException() {
        super("ERROR: Unavailable Bike");
    }
}
