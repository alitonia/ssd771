package com.isd.ict.capstoneproject.common.exception.ebr;
/**
 * The {@link NotFoundUserRentalException notFoundUserRentalException} exception for not found user's rental.
 *
 */
public class NotFoundUserRentalException extends EBRException {

    public NotFoundUserRentalException() {
        super("ERROR: User Currently Doesnt Have Any Rentals.");
    }
}
