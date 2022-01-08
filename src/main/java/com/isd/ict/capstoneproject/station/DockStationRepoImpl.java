package com.isd.ict.capstoneproject.station;

import com.isd.ict.capstoneproject.common.exception.db.DataSourceException;
import com.isd.ict.capstoneproject.common.exception.db.NotFoundException;
import com.isd.ict.capstoneproject.common.exception.db.NotUpdatedException;
import com.isd.ict.capstoneproject.repository.ResultSetMappable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 * The {@link DockStationRepoImpl DockStationRepoImpl} class is repository of dock station.
 *
 *
 */
public class DockStationRepoImpl implements DockStationRepo, ResultSetMappable<DockStation> {

    /**
     * Get dock station by id
     *
     * @param id
     * @return {@link DockStation dockStation}
     * @throws DataSourceException
     */
    @Override
    public DockStation getById(Integer id) throws DataSourceException {
        try {
            Statement stmDock = DATA_SOURCE.getConnection().createStatement();
            ResultSet res = stmDock.executeQuery("select * from Station where id = " + id);
            if (res.next()) {
                return mapFromResultSet(res);
            }
            throw new NotFoundException();
        } catch (SQLException ex) {
            throw new DataSourceException(ex);
        }
    }

    @Override
    public List<DockStation> getAll() throws DataSourceException {
        try {
            Statement stm = DATA_SOURCE.getConnection().createStatement();
            ResultSet res = stm.executeQuery("select * from Station");
            List<DockStation> dockStations = new ArrayList<>();
            while (res.next()) {
                dockStations.add(mapFromResultSet(res));
            }
            if (dockStations.isEmpty()) throw new NotFoundException();
            return dockStations;
        } catch (SQLException ex) {
            throw new DataSourceException(ex);
        }
    }

    @Override
    public List<DockStation> getByKeyword(String keyword) throws DataSourceException {
        try {
            Statement stm = DATA_SOURCE.getConnection().createStatement();
            ResultSet res = stm.executeQuery("select * from Station " +
                    "where name like '%" + keyword + "%' " +
                    "or address like '%" + keyword + "%'");
            List<DockStation> dockStations = new ArrayList<>();
            while (res.next()) {
                dockStations.add(mapFromResultSet(res));
            }
            if (dockStations.isEmpty()) throw new NotFoundException();
            return dockStations;
        } catch (SQLException ex) {
            throw new DataSourceException(ex);
        }
    }

    public int getCapacity(int dockStationId) throws DataSourceException {
        try {
            Statement stm = DATA_SOURCE.getConnection().createStatement();
            String sql = "select count(*) from Bike where location = " + dockStationId;
            return stm.executeQuery(sql).getInt(1);
        } catch (SQLException ex) {
            throw new DataSourceException(ex);
        }
    }

    public void updateCapacity(int dockStationId) throws DataSourceException {
        try {
            String countBikeQuery = "(select count(*) from Bike where location = " + dockStationId + ")";
            String sql = "update Station set capacity = " + countBikeQuery + " where id = " + dockStationId;
            int noOfChanged = DATA_SOURCE.getConnection().createStatement().executeUpdate(sql);
            if (noOfChanged == 0) throw new NotUpdatedException("Not Updated Dock Station with id = " + dockStationId);
        } catch (SQLException ex) {
            throw new DataSourceException(ex);
        }
    }

    /**
     * Convert result set to dock station
     * @param res
     * @return {@link DockStation dockStation}
     * @throws SQLException
     * @throws DataSourceException
     */
    @Override
    public DockStation mapFromResultSet(ResultSet res) throws SQLException, DataSourceException {
        DockStation dockStation = DockStation.builder()
                .dockStationId(res.getInt("id"))
                .name(res.getString("name"))
                .address(res.getString("address"))
                .distance(res.getInt("distance"))
                .maxCapacity(res.getInt("maxCapacity"))
                .capacity(res.getInt("capacity"))
                .walkingTimeByMinutes(res.getInt("walkingTimeByMinutes"))
                .build();

        return dockStation;
    }
}
