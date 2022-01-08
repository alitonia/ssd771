package com.isd.ict.capstoneproject.common.exception.db;

import java.sql.SQLException;
/**
 * The {@link DataSourceException dataSourceException} exception about data source.
 *
 *
 */
public class DataSourceException extends Exception {

    public DataSourceException() {
        super("ERROR: Error from database");
    }

    public DataSourceException(String message) {
        super(message);
    }

    public DataSourceException(SQLException ex) {
        super(ex.getMessage());
        ex.printStackTrace();
    }
}
