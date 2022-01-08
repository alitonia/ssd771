package com.isd.ict.capstoneproject.repository;

import com.isd.ict.capstoneproject.common.exception.db.DataSourceException;

import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * The {@link ResultSetMappable resultSetMappable} interface provides functionalities for repositories.
 *
 * @author Group 3
 *
 */
public interface ResultSetMappable<T> {
    /**
     * Convert result set to object
     * @param res
     * @return {@link T t}
     * @throws SQLException
     * @throws DataSourceException
     */
    T mapFromResultSet(ResultSet res) throws SQLException, DataSourceException;

}
