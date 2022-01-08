package com.isd.ict.capstoneproject.common.exception.ebr;

/**
 * The {@link UserRentalNotFinishedException userRentalNotFinishedException} exception for user rental not finished.
 *
 */
public class UserRentalNotFinishedException extends EBRException {

    public UserRentalNotFinishedException() {
        super("ERROR: User Current Rental Not Finished");
    }
}
