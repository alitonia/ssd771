package com.isd.ict.capstoneproject.repository;

import com.isd.ict.capstoneproject.common.exception.db.DataSourceException;

/**
 * The {@link BaseRepo baseRepo} interface provides functionalities for repositories.
 *
 *
 */
public interface BaseRepo<T, ID> {

    DataSource DATA_SOURCE = DataSource.getInstance();
    /**
     * Get by id
     * @param id
     * @return {@link T t}
     * @throws DataSourceException
     */
    T getById(ID id) throws DataSourceException;
}
