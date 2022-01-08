package com.isd.ict.capstoneproject.bike;

import com.isd.ict.capstoneproject.common.exception.db.DataSourceException;
import com.isd.ict.capstoneproject.common.exception.ebr.UnavailableBikeException;

import java.util.List;
/**
 * The {@link BikeService bikeService} interface provides functionalities of bike service.
 *
 * @author Group 3
 *
 */
public interface BikeService {

    /**
     * Check whether the bike is available for renting
     *
     * @param barcode - the barcode of the bike
     * @return {@link Bike bike}
     * @throws UnavailableBikeException - if int the bike is not available for renting
     */
    Bike getBikeIfAvailable(String barcode) throws UnavailableBikeException;

    /**
     * Get bike by location
     *
     * @param stationId
     * @return {@link List <Bike> bikeList}
     * @throws DataSourceException
     */
    List<Bike> getAllByLocation(int stationId) throws DataSourceException;

    /**
     * Update bike location after being rented
     * @param barcode
     * @return {@link Bike bike}
     * @throws DataSourceException
     */
    Bike updateRentedBikeLocation(String barcode) throws DataSourceException;

    /**
     * Update bike location after being returned
     * @param barcode
     * @param dockStationId
     * @return {@link Bike bike}
     * @throws DataSourceException
     */
    Bike updateReturnedBikeLocation(String barcode, int dockStationId) throws DataSourceException;

    /**
     * Get the bike entity from data storage with given ID (Barcode)
     * @param bikeBarcode
     * @return
     */
    Bike getById(String bikeBarcode) throws DataSourceException;
}
