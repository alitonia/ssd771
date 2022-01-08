package com.isd.ict.capstoneproject.user;

import com.isd.ict.capstoneproject.common.exception.db.DataSourceException;
import com.isd.ict.capstoneproject.common.exception.db.NotFoundException;
import com.isd.ict.capstoneproject.common.exception.db.NotUpdatedException;
import com.isd.ict.capstoneproject.repository.ResultSetMappable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * The {@link AppUserRepoImpl appUserRepoImpl} class is repository of user.
 *
 * @author Group 3
 *
 */
public class AppUserRepoImpl implements AppUserRepo, ResultSetMappable<AppUser> {

    @Override
    public AppUser insert(AppUser user) throws DataSourceException {
        try {
            String sql = "insert into AppUser(username, password) values " +
                    "(" + "'" + user.getName()      + "'" + "," +
                          "'" + user.getPassword()  + "'" + ")";
            PreparedStatement stm = DATA_SOURCE.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.execute();
            ResultSet res = stm.getGeneratedKeys();
            int newId = res.getInt(1);
            if (newId == 0) throw new NotUpdatedException("Not Inserted Invoice");
            return getById(newId);
        } catch (SQLException ex) {
            throw new DataSourceException(ex);
        }
    }

    /**
     * Get user by id
     *
     * @param id
     * @return {@link AppUser user}
     * @throws DataSourceException
     */
    @Override
    public AppUser getById(Integer id) throws DataSourceException {
        try {
            String sql = "select * from AppUser where id = " + id;
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
     * Convert result set to user
     * @param res
     * @return {@link AppUser user}
     * @throws SQLException
     * @throws DataSourceException
     */
    @Override
    public AppUser mapFromResultSet(ResultSet res) throws SQLException, DataSourceException {
        AppUser appUser = AppUser.builder()
                .appUserId(res.getInt("id"))
                .name(res.getString("username"))
                .build();
        return appUser;
    }
}
