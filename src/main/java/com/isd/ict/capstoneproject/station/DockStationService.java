package com.isd.ict.capstoneproject.station;

import com.isd.ict.capstoneproject.common.exception.db.DataSourceException;

import java.util.List;
/**
 * The {@link DockStationService dockStationService} interface provides functionalities of dock station service.
 *
 *
 */
public interface DockStationService {
    /**
     * Get dock station by id
     * @param dockStationId
     * @return {@link DockStation dockStation}
     * @throws DataSourceException
     */
    DockStation getById(int dockStationId) throws DataSourceException;

    /**
     * Get all dock station
     * @return {@link List<DockStation> dockStationList}
     * @throws DataSourceException
     */
    List<DockStation> getAll() throws DataSourceException;

    /**
     * Get dock station by keyword
     * @param keyword
     * @return {@link List<DockStation> dockStationList}
     * @throws DataSourceException
     */
    List<DockStation> getByKeyword(String keyword) throws DataSourceException;

    /**
     * Update capacity of dock station
     * @param dockStationId
     * @throws DataSourceException
     */
    void updateCapacity(int dockStationId) throws DataSourceException;
}
