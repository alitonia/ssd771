package com.isd.ict.capstoneproject.rental.strategy;

import com.isd.ict.capstoneproject.common.exception.db.DataSourceException;
import com.isd.ict.capstoneproject.repository.BaseRepo;

import java.util.List;
/**
 * The {@link RentalStrategyRepo rentalStrategyRepo} interface provides functionalities of rental strategy's repository.
 *
 * @author Group 3
 *
 */
public interface RentalStrategyRepo extends BaseRepo<RentalStrategy, Integer> {
    /**
     * Get all rental strategy
     *
     * @return {@link List<RentalStrategy> rentalStrategyList}
     * @throws DataSourceException
     */
    List<RentalStrategy> getAll() throws DataSourceException;
}
