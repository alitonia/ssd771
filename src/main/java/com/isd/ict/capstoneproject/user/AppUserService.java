package com.isd.ict.capstoneproject.user;

import com.isd.ict.capstoneproject.common.exception.db.DataSourceException;
/**
 * The {@link AppUserService appUserService} interface provides functionalities of user service.
 *
 *
 */
public interface AppUserService {
    /**
     * Decode authentication token
     * @param authenticationToken
     * @return - decoded authentication token
     */
    int decodeAuthenticationToken(String authenticationToken);

    /**
     * Get user by id
     * @param userId
     * @return {@link AppUser appUser}
     * @throws DataSourceException
     */
    AppUser getById(int userId) throws DataSourceException;
}
