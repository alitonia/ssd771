package com.isd.ict.capstoneproject.repository;

import com.isd.ict.capstoneproject.common.exception.db.DataSourceException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * The {@link DataSource dataSource} class is data source.
 *
 * @author Group 3
 *
 */
public class DataSource {

    /**
     * singleton instance
     */
    private static DataSource INSTANCE;
    /**
     * Connection to DB
     */
    private Connection connection;
    /**
     * In a transaction or not
     */
    private boolean transactional;

    public static DataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DataSource();
        }
        return INSTANCE;
    }

    private DataSource() {
        this.connection = getConnection();
    }

    /**
     * Get connect to DB
     * @return {@link Connection connection}
     */
    public Connection getConnection() {
        if (connection != null || transactional) return connection;
        try {
            connection = DriverManager.getConnection(
                    DataSourceConfigs.DB_URL,
                    DataSourceConfigs.DB_USERNAME,
                    DataSourceConfigs.DB_PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Enable transaction
     *
     * @throws DataSourceException
     */
    public void enableTransaction() throws DataSourceException {
        try {
            this.getConnection().setAutoCommit(false);
            this.transactional = true;
        } catch (SQLException ex) {
            throw new DataSourceException(ex);
        }
    }

    /**
     * Disable transaction
     */
    public void disableTransaction() {
        try {
            this.transactional = false;
            this.getConnection().setAutoCommit(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
