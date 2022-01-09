package com.isd.ict.capstoneproject.rental;

import com.isd.ict.capstoneproject.bike.BikeService;
import com.isd.ict.capstoneproject.bike.BikeServiceImpl;
import com.isd.ict.capstoneproject.common.exception.db.DataSourceException;
import com.isd.ict.capstoneproject.common.exception.db.NotFoundException;
import com.isd.ict.capstoneproject.common.exception.db.NotUpdatedException;
import com.isd.ict.capstoneproject.rental.strategy.RentalStrategyRepo;
import com.isd.ict.capstoneproject.rental.strategy.RentalStrategyRepoImpl;
import com.isd.ict.capstoneproject.repository.ResultSetMappable;
import com.isd.ict.capstoneproject.user.AppUserService;
import com.isd.ict.capstoneproject.user.AppUserServiceImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.isd.ict.capstoneproject.utils.DateTimeUtils.parseLocalDateTime;
/**
 * The {@link RentalRepoImpl rentalRepoImpl} class is repository of rental.
 *
 *
 */
public class RentalRepoImpl implements RentalRepo, ResultSetMappable<Rental> {

    /**
     * app user repository
     */
    private AppUserService appUserService;
    /**
     * bike repository
     */
    private BikeService bikeService;
    /**
     * rental strategy repository
     */
    private RentalStrategyRepo rentalStrategyRepo;

    RentalRepoImpl() {
        this.appUserService = new AppUserServiceImpl();
        this.bikeService = new BikeServiceImpl();
        this.rentalStrategyRepo = new RentalStrategyRepoImpl();
    }

    /**
     * Insert rental to DB
     *
     * @param rental
     * @return {@link Rental rental}
     * @throws DataSourceException
     */
    @Override
    public Rental insert(Rental rental) throws DataSourceException {
        try {
            String sql = "insert into Rental(userId, bikeBarCode, startTime, strategy, status) values " +
                    "(" + "'" + rental.getAppUser().getAppUserId()  + "'" + "," +
                          "'" + rental.getBike().getBarcode()       + "'" + "," +
                          "'" + rental.getStartTime()               + "'" + "," +
                          "'" + rental.getRentalStrategy().getId()  + "'" + "," +
                          "'" + rental.getStatus().getValue()       + "'" + ")";

            System.out.println("222222----222");
            System.out.println(rental.toString());
            System.out.println(rental.getRentalStrategy());
            System.out.println(sql);


            PreparedStatement stm = DATA_SOURCE.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.execute();
            ResultSet res = stm.getGeneratedKeys();
            if (res.next()) return getById(res.getInt(1));
            else throw new NotUpdatedException("Not Inserted Rental");
        } catch (SQLException ex) {
            throw new DataSourceException(ex);
        }
    }

    /**
     * Get current rental by user's id
     *
     * @param userId
     * @return {@link Rental rental}
     * @throws DataSourceException
     */
    @Override
    public Rental getCurrentRentalByUserId(int userId) throws DataSourceException {
        try {
            String sql = "select * from Rental where userId = " + userId + " and status = " + RentalStatus.STARTED.getValue();
            ResultSet res = DATA_SOURCE.getConnection().createStatement().executeQuery(sql);
            if (res.next()) {
                return mapFromResultSet(res);
            }
            throw new NotFoundException();
        } catch (SQLException ex) {
            throw new DataSourceException(ex);
        }
    }

    /**
     * Update rental
     *
     * @param rental
     * @return {@link Rental rental}
     * @throws DataSourceException
     */
    @Override
    public Rental update(Rental rental) throws DataSourceException {
        try {
            String sql = "update Rental set " +
                    "startTime = "         + "'" + rental.getStartTime()              + "'" + "," +
                    "rentalDurationByMinutes = "    + "'" + rental.getRentalDurationByMinutes() + "'" + "," +
                    "finishTime = "         + "'" + rental.getFinishTime()              + "'" + "," +
                    "status = "             + "'" + rental.getStatus().getValue()                  + "'" + " " +
                    "where id = " + rental.getRentId();

            int noOfChanged = DATA_SOURCE.getConnection().createStatement().executeUpdate(sql);
            if (noOfChanged == 0) throw new NotUpdatedException("Not Updated Rental with id = " + rental.getRentId());
            return getById(rental.getRentId());
        } catch (SQLException ex) {
            throw new DataSourceException(ex);
        }
    }

    /**
     * Get rental by id
     *
     * @param id
     * @return {@link Rental rental}
     * @throws DataSourceException
     */
    @Override
    public Rental getById(Integer id) throws DataSourceException {
        try {
            String sql = "select * from Rental where id = " + id;
            ResultSet res = DATA_SOURCE.getConnection().createStatement().executeQuery(sql);
            if (res.next()) {
                return mapFromResultSet(res);
            }
            throw new NotFoundException();
        } catch (SQLException ex) {
            throw new DataSourceException(ex);
        }
    }

    /**
     * Convert result set to rental
     *
     * @param res
     * @return {@link Rental rental}
     * @throws SQLException
     * @throws DataSourceException
     */
    @Override
    public Rental mapFromResultSet(ResultSet res) throws SQLException, DataSourceException {
        Rental rental = Rental.builder()
                .rentId(res.getInt("id"))
                .appUser(appUserService.getById(res.getInt("userId")))
                .bike(bikeService.getById(res.getString("bikeBarcode")))
                .rentalDurationByMinutes(res.getInt("rentalDurationByMinutes"))
                .startTime(parseLocalDateTime(res.getString("startTime")))
                .finishTime(parseLocalDateTime(res.getString("finishTime")))
                .status(RentalStatus.mapFromInt(res.getInt("status")))
                .rentalStrategy(rentalStrategyRepo.getById(res.getInt("strategy")))
                .build();
        return rental;
    }
}
