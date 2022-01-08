package com.isd.ict.capstoneproject.rental;

import com.isd.ict.capstoneproject.common.exception.db.DataSourceException;
import com.isd.ict.capstoneproject.common.exception.ebr.UnavailableBikeException;
import com.isd.ict.capstoneproject.rental.strategy.RentalStrategy;
import com.isd.ict.capstoneproject.user.AppUser;

import java.util.List;

/**
 * The {@link RentalService RentalService} interface provides functionalities of rental service.
 *
 *
 */
public interface RentalService {

    /**
     * Create new rental
     * @param appUser
     * @param barcode
     * @param rentalStrategy
     * @return {@link Rental rental}
     * @throws UnavailableBikeException
     * @throws DataSourceException
     */
    Rental createNewRental(AppUser appUser, String barcode, int rentalStrategy) throws UnavailableBikeException, DataSourceException;

    /**
     * Start counting time for the rental
     */
    void start(Rental rental) throws DataSourceException;

    /**
     * End counting time of the rental
     */
    void finish(Rental rental) throws DataSourceException;

    /**
     * Calculate cost of rental
     * @param rental
     * @return - calculated cost of rental
     */
    int calculateCost(Rental rental);
    /**
     * Calculate deposit of rental
     * @param rental
     * @return - calculated deposit of rental
     */
    int calculateDeposit(Rental rental);

    /**
     * Insert rental to DB
     *
     * @param rental
     * @return {@link Rental rental}
     * @throws DataSourceException
     */
    Rental insert(Rental rental) throws DataSourceException;

    /**
     * Get current rental by user's id
     *
     * @param userId
     * @return {@link Rental rental}
     * @throws DataSourceException
     */
    Rental getCurrentRentalByUserId(int userId) throws DataSourceException;

    /**
     * Get Rental entity from data storage by given id
     * @param rentalId
     * @return
     */
    Rental getById(int rentalId) throws DataSourceException;

    /**
     * Load all available options of rental
     * @return {@link List<RentalStrategy> rentalStrategyList} - all available rental strategy
     */
    List<RentalStrategy> getAllRentalStrategies() throws DataSourceException;
}
