package com.isd.ict.capstoneproject.bike.type;

import com.isd.ict.capstoneproject.common.exception.db.DataSourceException;
import com.isd.ict.capstoneproject.common.exception.db.NotFoundException;
import com.isd.ict.capstoneproject.repository.ResultSetMappable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 * The {@link BikeTypeRepoImpl bikeTypeRepoImpl} class is repository of bike type.
 *
 * @author Group 3
 *
 */
public class BikeTypeRepoImpl implements BikeTypeRepo, ResultSetMappable<BikeType> {

    public BikeTypeRepoImpl() {};
    /**
     * Get all bike types
     *
     * @return {@link List<BikeType> bikeTypeList}
     * @throws DataSourceException
     */
    public List<BikeType> getAll() throws DataSourceException {
        try {
            Statement stm = DATA_SOURCE.getConnection().createStatement();
            ResultSet res = stm.executeQuery("select * from BikeType");
            List<BikeType> bikeTypes = new ArrayList<>();
            while (res.next()) {
                bikeTypes.add(mapFromResultSet(res));
            }
            if (bikeTypes.isEmpty()) throw new NotFoundException();
            return bikeTypes;
        } catch (SQLException ex) {
            throw new DataSourceException(ex);
        }
    }

    /**
     * Get bike type by id
     *
     * @param id
     * @return {@link BikeType bikeType}
     * @throws DataSourceException
     */
    @Override
    public BikeType getById(Integer id) throws DataSourceException {
        try {
            Statement stm = DATA_SOURCE.getConnection().createStatement();
            ResultSet res = stm.executeQuery("select * from BikeType where id = " + id);
            if (res.next()) {
                return mapFromResultSet(res);
            }
            throw new NotFoundException();
        } catch (SQLException ex) {
            throw new DataSourceException(ex);
        }
    }

    /**
     * Convert result set to bike type
     *
     * @param res
     * @return {@link BikeType bikeType}
     * @throws SQLException
     */
    @Override
    public BikeType mapFromResultSet(ResultSet res) throws SQLException {
        BikeType bikeType = BikeType.builder()
                .bikeTypeId(res.getInt("id"))
                .bikeTypeName(res.getString("bikeTypeName"))
                .monetaryValue(res.getInt("monetaryValue"))
                .electricType(res.getInt("electricType") == 1)
                .bikeTypeImageURL(res.getString("bikeTypeImageURL"))
                .build();
        return bikeType;
    }
}
