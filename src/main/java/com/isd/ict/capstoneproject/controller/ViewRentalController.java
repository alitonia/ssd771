package com.isd.ict.capstoneproject.controller;

import com.isd.ict.capstoneproject.common.exception.db.DataSourceException;
import com.isd.ict.capstoneproject.common.exception.db.NotFoundException;
import com.isd.ict.capstoneproject.common.exception.ebr.InternalServerException;
import com.isd.ict.capstoneproject.common.exception.ebr.NotFoundUserRentalException;
import com.isd.ict.capstoneproject.controller.dto.RentalDetailDTO;
import com.isd.ict.capstoneproject.rental.Rental;
import com.isd.ict.capstoneproject.rental.RentalService;
import com.isd.ict.capstoneproject.rental.RentalServiceImpl;
import com.isd.ict.capstoneproject.user.AppUserService;
import com.isd.ict.capstoneproject.user.AppUserServiceImpl;
import com.isd.ict.capstoneproject.utils.Utils;

import java.util.logging.Logger;
/**
 * The {@link ViewRentalController viewRentalController} class provides functionalities for return bike.
 *
 * @author Group 3
 *
 */
public class ViewRentalController {
    private static final Logger LOGGER = Utils.getLogger(ViewRentalController.class.getName());
    /**
     * singleton instance
     */
    private static ViewRentalController INSTANCE;
    /**
     * User service
     */
    private AppUserService appUserService;
    /**
     * Rental repository
     */
    private RentalService rentalService;

    public static ViewRentalController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ViewRentalController();
        }
        return INSTANCE;
    }

    private ViewRentalController() {
        this.rentalService = new RentalServiceImpl();
        this.appUserService = new AppUserServiceImpl();
    }
    
    /**
     * View current rental information of user.
     * @param authenticationToken - token of the current session
     */
    public RentalDetailDTO requestToViewRental(String authenticationToken) throws NotFoundUserRentalException, InternalServerException {
        int userId = appUserService.decodeAuthenticationToken(authenticationToken);
        try {
            Rental rental = rentalService.getCurrentRentalByUserId(userId);
            return new RentalDetailDTO(rental, rentalService.calculateDeposit(rental), rentalService.calculateCost(rental));
        } catch (NotFoundException ex) {
            throw new NotFoundUserRentalException();
        } catch (DataSourceException ex) {
            LOGGER.info(ex.getMessage());
            throw new InternalServerException();
        }
    }
}
