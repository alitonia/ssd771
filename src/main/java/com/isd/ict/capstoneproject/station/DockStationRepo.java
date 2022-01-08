package com.isd.ict.capstoneproject.station;

import com.isd.ict.capstoneproject.common.exception.db.DataSourceException;
import com.isd.ict.capstoneproject.repository.BaseRepo;

import java.util.List;
/**
 * The {@link DockStationRepo dockStationRepo} interface provides functionalities of dock station's repository.
 *
 * @author Group 3
 *
 */
public interface DockStationRepo extends BaseRepo<DockStation, Integer> {
    /**
     * Get all dock station
     *
     * @return {@link List<DockStation> dockStationList}
     * @throws DataSourceException
     */
    List<DockStation> getAll() throws DataSourceException;

    /**
     * Get dock station by keyword
     *
     * @param keyword
     * @return {@link List<DockStation> dockStationList}
     * @throws DataSourceException
     */
    List<DockStation> getByKeyword(String keyword) throws DataSourceException;

    /**
     * Get capacity of dock station by id
     *
     * @param dockStationId
     * @return - capacity of dock station
     * @throws DataSourceException
     */
    int getCapacity(int dockStationId) throws DataSourceException;

    /**
     * Update capacity of dock station by id
     *
     * @param dockStationId
     * @return {@link DockStation dockStation}
     * @throws DataSourceException
     */
     void updateCapacity(int dockStationId) throws DataSourceException ;
}
