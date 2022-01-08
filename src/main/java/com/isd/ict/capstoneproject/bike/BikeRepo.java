package com.isd.ict.capstoneproject.bike;

import com.isd.ict.capstoneproject.common.exception.db.DataSourceException;
import com.isd.ict.capstoneproject.repository.BaseRepo;

import java.util.List;
/**
 * The {@link BikeRepo bikeRepo} interface provides functionalities of bike's repository.
 *
 * @author Group 3
 *
 */
public interface BikeRepo extends BaseRepo<Bike, String> {
    /**
     * Get bike by location
     *
     * @param stationId
     * @return {@link List<Bike> bikeList}
     * @throws DataSourceException
     */
    List<Bike> getAllByLocation(int stationId) throws DataSourceException;

    /**
     * Update bike by barcode in location
     * @param barcode
     * @param dockStationId
     * @return {@link Bike bike}
     * @throws DataSourceException
     */
    Bike updateLocation(String barcode, Integer dockStationId) throws DataSourceException;
}
