package com.isd.ict.capstoneproject.user;

import com.isd.ict.capstoneproject.common.exception.db.DataSourceException;
/**
 * The {@link AppUserServiceImpl appUserServiceImpl} class is service of user.
 *
 * @author Group 3
 *
 */
public class AppUserServiceImpl implements AppUserService {
    /**
     * App user repository
     */
    private final AppUserRepo appUserRepo;

    public AppUserServiceImpl() {
        this.appUserRepo = new AppUserRepoImpl();
    }

    public int decodeAuthenticationToken(String authenticationToken) {
        return Integer.parseInt(authenticationToken.split("_")[0]);
    }

    @Override
    public AppUser getById(int userId) throws DataSourceException {
        return appUserRepo.getById(userId);
    }
}
