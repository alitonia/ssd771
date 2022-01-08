package com.isd.ict.capstoneproject.bike.type;

import com.isd.ict.capstoneproject.common.exception.db.DataSourceException;
import com.isd.ict.capstoneproject.repository.BaseRepo;

import java.util.List;
/**
 * The {@link BikeTypeRepo bikeTypeRepo} interface provides functionalities of bike type's repository.
 *
 */
public interface BikeTypeRepo extends BaseRepo<BikeType, Integer>{
    /**
     * Get all bike type
     *
     * @return {@link List<BikeType> bikeTypeList}
     * @throws DataSourceException
     */
    List<BikeType> getAll() throws DataSourceException;
}
