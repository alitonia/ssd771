package com.isd.ict.capstoneproject.bike;

import com.isd.ict.capstoneproject.bike.type.BikeTypeRepo;
import com.isd.ict.capstoneproject.bike.type.BikeTypeRepoImpl;
import com.isd.ict.capstoneproject.common.exception.db.DataSourceException;
import com.isd.ict.capstoneproject.common.exception.db.NotFoundException;
import com.isd.ict.capstoneproject.common.exception.db.NotUpdatedException;
import com.isd.ict.capstoneproject.repository.ResultSetMappable;
import com.isd.ict.capstoneproject.station.DockStationService;
import com.isd.ict.capstoneproject.station.DockStationServiceImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 * The {@link BikeRepoImpl bikeRepoImpl} class is repository of bike.
 *
 * @author Group 3
 *
 */
class BikeRepoImpl implements BikeRepo, ResultSetMappable<Bike> {
    /**
     * Bike type repository
     */
    private BikeTypeRepo bikeTypeRepo;
    /**
     * Dock station repository
     */
    private DockStationService dockStationService;

    BikeRepoImpl() {
        this.bikeTypeRepo = new BikeTypeRepoImpl();
        this.dockStationService = new DockStationServiceImpl();
    }

    /**
     * Get bike by id
     *
     * @param id
     * @return {@link Bike bike}
     * @throws DataSourceException
     */
    @Override
    public Bike getById(String id) throws DataSourceException {
        try {
            Statement stm = DATA_SOURCE.getConnection().createStatement();
            ResultSet res = stm.executeQuery("select * from Bike where barcode = " + id);
            if (res.next()) {
                return mapFromResultSet(res);
            }
            throw new NotFoundException("Not Found Bike with Barcode = " + id);
        } catch (SQLException ex) {
            throw new DataSourceException(ex);
        }
    }

    /**
     * Get all bikes in a location
     *
     * @param stationId
     * @return {@link List<Bike> bikeList}
     * @throws DataSourceException
     */
    @Override
    public List<Bike> getAllByLocation(int stationId) throws DataSourceException {
        try {
            Statement stm = DATA_SOURCE.getConnection().createStatement();
            ResultSet res = stm.executeQuery("select * from Bike where location = " + stationId);
            List<Bike> bikes = new ArrayList<>();
            while (res.next()) {
                bikes.add(mapFromResultSet(res));
            }
            if (bikes.isEmpty()) throw new NotFoundException("Not Found BikeList");
            return bikes;
        } catch (SQLException ex) {
            throw new DataSourceException(ex);
        }
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
    public Bike updateLocation(String barcode, Integer dockStationId) throws DataSourceException {
        try {
            String sql = "update Bike " +
                    "set location = " + dockStationId + " " +
                    "where barcode = " + barcode;
            int noOfChanged = DATA_SOURCE.getConnection().createStatement().executeUpdate(sql);
            if (noOfChanged == 0) throw new NotUpdatedException("Not Updated Bike with Barcode = " + barcode + " and Location = " + dockStationId);
            return getById(barcode);
        } catch (SQLException ex) {
            throw new DataSourceException(ex);
        }
    }

    /**
     * Convert result set to bike
     *
     * @param res
     * @return {@link Bike bike}
     * @throws SQLException
     * @throws DataSourceException
     */
    @Override
    public Bike mapFromResultSet(ResultSet res) throws SQLException, DataSourceException {
        Bike bike = Bike.builder()
                .barcode(res.getString("barcode"))
                .type(bikeTypeRepo.getById(res.getInt("type")))
                .licensePlateNumber(res.getString("licensePlateNumber"))
                .available(res.getInt("available") == 1)
                .batteryPercentage(res.getInt("batteryPercentage"))
                .build();
        int dockStationId = res.getInt("location");
        if (dockStationId != 0) bike.setLocation(dockStationService.getById(res.getInt("location")));
        return bike;
    }
}
