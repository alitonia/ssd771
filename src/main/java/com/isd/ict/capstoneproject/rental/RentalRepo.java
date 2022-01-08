package com.isd.ict.capstoneproject.rental;

import com.isd.ict.capstoneproject.common.exception.db.DataSourceException;
import com.isd.ict.capstoneproject.repository.BaseRepo;
/**
 * The {@link RentalRepo rentalRepo} interface provides functionalities of rental's repository.
 *
 *
 */
public interface RentalRepo extends BaseRepo<Rental, Integer> {
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
     * Update rental
     *
     * @param rental
     * @return {@link Rental rental}
     * @throws DataSourceException
     */
    Rental update(Rental rental) throws DataSourceException;
}
