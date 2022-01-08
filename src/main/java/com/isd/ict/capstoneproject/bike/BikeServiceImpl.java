package com.isd.ict.capstoneproject.bike;

import com.isd.ict.capstoneproject.bike.type.BikeTypeRepo;
import com.isd.ict.capstoneproject.bike.type.BikeTypeRepoImpl;
import com.isd.ict.capstoneproject.common.exception.db.DataSourceException;
import com.isd.ict.capstoneproject.common.exception.ebr.UnavailableBikeException;

import java.util.List;
/**
 * The {@link BikeServiceImpl bikeServiceImpl} class is service of bike.
 *
 * @author Group 3
 *
 */
public class BikeServiceImpl implements BikeService {
    /**
     * Bike repository
     */
    BikeRepo bikeRepo;
    /**
     * Bike type repository
     */
    BikeTypeRepo bikeTypeRepo;

    public BikeServiceImpl() {
        this.bikeRepo = new BikeRepoImpl();
        this.bikeTypeRepo = new BikeTypeRepoImpl();
    }

    /**
     * Check whether the bike is available for renting
     *
     * @param barcode - the barcode of the bike
     * @return {@link Bike bike}
     * @throws UnavailableBikeException - if int the bike is not available for renting
     */
    @Override
    public Bike getBikeIfAvailable(String barcode) throws UnavailableBikeException {
        try {
            Bike bike = bikeRepo.getById(barcode);
            if (bike.isAvailable() && bike.getLocation().getDockStationId() != 0) {
                return bike;
            }
            throw new UnavailableBikeException();
        } catch (DataSourceException ex) {
            throw new UnavailableBikeException();
        }
    }

    @Override
    public List<Bike> getAllByLocation(int stationId) throws DataSourceException {
        return bikeRepo.getAllByLocation(stationId);
    }

    /**
     * Update bike in a location
     *
     * @param barcode
     * @return {@link Bike bike}
     * @throws DataSourceException
     */
    @Override
    public Bike updateRentedBikeLocation(String barcode) throws DataSourceException {
        return bikeRepo.updateLocation(barcode, null);
    }


    /**
     * Update bike in a location
     *
     * @param barcode
     * @param dockStationId
     * @return {@link Bike bike}
     * @throws DataSourceException
     */
    @Override
    public Bike updateReturnedBikeLocation(String barcode, int dockStationId) throws DataSourceException {
        return bikeRepo.updateLocation(barcode, dockStationId);
    }

    @Override
    public Bike getById(String bikeBarcode) throws DataSourceException {
        return bikeRepo.getById(bikeBarcode);
    }
}
