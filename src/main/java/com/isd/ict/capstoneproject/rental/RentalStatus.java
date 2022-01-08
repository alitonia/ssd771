package com.isd.ict.capstoneproject.rental;
/**
 * The {@link RentalStatus rentalStatus} THis class used for tracking status of the rental
 *
 *
 */
enum RentalStatus {

    NEW(0), DEPOSITED(1), STARTED(3), FINISHED(5), PAID_COMPLETED(6);

    private final int value;

    RentalStatus(int value) {
        this.value = value;
    }

    int getValue() {
        return this.value;
    }

    static RentalStatus mapFromInt(int value) {
        switch (value) {
            case 0: return NEW;
            case 1: return DEPOSITED;
            case 3: return STARTED;
            case 5: return FINISHED;
            case 6: return PAID_COMPLETED;
            default: throw new IllegalStateException("Inconsistency of Rental Status. RentalStatus with value = " + value + " doesn't exist.");
        }
    }

}
