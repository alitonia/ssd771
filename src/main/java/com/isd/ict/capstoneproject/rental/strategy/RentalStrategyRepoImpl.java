package com.isd.ict.capstoneproject.rental.strategy;

import com.isd.ict.capstoneproject.common.exception.db.DataSourceException;
import com.isd.ict.capstoneproject.common.exception.db.NotFoundException;
import com.isd.ict.capstoneproject.repository.ResultSetMappable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 * The {@link RentalStrategyRepoImpl rentalStrategyRepoImpl} class is repository of rental strategy.
 *
 *
 */
public class RentalStrategyRepoImpl implements RentalStrategyRepo, ResultSetMappable<RentalStrategy> {

    /**
     * Get rental strategy by id
     *
     * @param id
     * @return {@link RentalStrategy rentalStrategy}
     * @throws DataSourceException
     */
    @Override
    public RentalStrategy getById(Integer id) throws DataSourceException {
        try {
            Statement stm = DATA_SOURCE.getConnection().createStatement();
            ResultSet res = stm.executeQuery("select * from RentalStrategy where id = " + id);
            if (res.next()) {
                RentalStrategy rentalStrategy = mapFromResultSet(res);
                rentalStrategy.setDepositStrategy(new RentalDepositStrategyFactory().create(res.getString("depositStrategy")));
                rentalStrategy.setCostStrategy(new RentalCostStrategyFactory().create(res.getString("costStrategy")));
                rentalStrategy.setCountingTimeStrategy(new RentalCountingTimeStrategyFactory().create(res.getString("countingTimeStrategy")));
                return rentalStrategy;
            }
            throw new NotFoundException("Not Found RentalStrategy with ID = " + id);
        } catch (SQLException ex) {
            throw new DataSourceException(ex);
        }
    }

    /**
     * Get all rental strategy
     *
     * @return {@link List<RentalStrategy> rentalStrategyList}
     * @throws DataSourceException
     */
    @Override
    public List<RentalStrategy> getAll() throws DataSourceException {
        try {
            Statement stm = DATA_SOURCE.getConnection().createStatement();
            ResultSet res = stm.executeQuery("select * from RentalStrategy");
            List<RentalStrategy> rentalStrategies = new ArrayList<>();
            while (res.next()) {
                rentalStrategies.add(mapFromResultSet(res));
            }
            if (rentalStrategies.isEmpty()) throw new NotFoundException("Not Found RentalStrategies");
            return rentalStrategies;
        } catch (SQLException ex) {
            throw new DataSourceException(ex);
        }
    }

    /**
     * Convert result set to rental strategy
     *
     * @param res
     * @return {@link RentalStrategy rentalStrategy}
     * @throws SQLException
     */
    @Override
    public RentalStrategy mapFromResultSet(ResultSet res) throws SQLException {
        RentalStrategy rentalStrategy = RentalStrategy.builder()
                .id(res.getInt("id"))
                .name(res.getString("name"))
                .description(res.getString("description"))
                .build();
        return rentalStrategy;
    }
}
