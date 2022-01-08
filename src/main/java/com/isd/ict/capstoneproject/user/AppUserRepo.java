package com.isd.ict.capstoneproject.user;

import com.isd.ict.capstoneproject.common.exception.db.DataSourceException;
import com.isd.ict.capstoneproject.repository.BaseRepo;
/**
 * The {@link AppUserRepo appUserRepo} interface provides functionalities of user's repository.
 *
 *
 */
public interface AppUserRepo extends BaseRepo<AppUser, Integer>{
    /**
     * Insert user to DB
     *
     * @param user
     * @return {@link AppUser user}
     * @throws DataSourceException
     */
    AppUser insert(AppUser user) throws DataSourceException;
}
