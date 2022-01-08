package com.isd.ict.capstoneproject.controller;

import com.isd.ict.capstoneproject.bike.BikeService;
import com.isd.ict.capstoneproject.bike.BikeServiceImpl;
import com.isd.ict.capstoneproject.common.exception.db.DataSourceException;
import com.isd.ict.capstoneproject.common.exception.interbank.InternalServerErrorException;
import com.isd.ict.capstoneproject.controller.dto.message.FailureMessage;
import com.isd.ict.capstoneproject.controller.dto.message.Message;
import com.isd.ict.capstoneproject.controller.dto.message.SuccessMessage;
import com.isd.ict.capstoneproject.payment.PaymentService;
import com.isd.ict.capstoneproject.payment.PaymentServiceImpl;
import com.isd.ict.capstoneproject.payment.invoice.Invoice;
import com.isd.ict.capstoneproject.payment.transaction.PaymentTransaction;
import com.isd.ict.capstoneproject.rental.Rental;
import com.isd.ict.capstoneproject.rental.RentalService;
import com.isd.ict.capstoneproject.rental.RentalServiceImpl;
import com.isd.ict.capstoneproject.repository.DataSource;
import com.isd.ict.capstoneproject.station.DockStationService;
import com.isd.ict.capstoneproject.station.DockStationServiceImpl;
import com.isd.ict.capstoneproject.user.AppUserService;
import com.isd.ict.capstoneproject.user.AppUserServiceImpl;
import com.isd.ict.capstoneproject.utils.Utils;

import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * The {@link ReturnBikeController returnBikeController} class provides functionalities for return bike.
 *
 */
public class ReturnBikeController {

    private static final Logger LOGGER = Utils.getLogger(ReturnBikeController.class.getName());

    /**
     * singleton instance
     */
    private static ReturnBikeController INSTANCE;
    /**
     * Data source
     */
    private final DataSource dataSource;

    /**
     * Authentication controller
     */
    private final AppUserService appUserService;
    /**
     * Payment transaction repository
     */
    private final PaymentService paymentService;
    /**
     * Rental repository
     */
    private final RentalService rentalService;
    /**
     * Bike repository
     */
    private final BikeService bikeService;
    /**
     * Dock station repository
     */
    private final DockStationService dockStationService;

    public static ReturnBikeController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ReturnBikeController();
        }
        return INSTANCE;
    }
    
    private ReturnBikeController() {
        this.dataSource = DataSource.getInstance();
        this.paymentService = new PaymentServiceImpl();
        this.rentalService = new RentalServiceImpl();
        this.bikeService = new BikeServiceImpl();
        this.dockStationService = new DockStationServiceImpl();
        this.appUserService = new AppUserServiceImpl();
    }

    /**
     * Return bike
     * 
     * @param authenticationToken   - encoded string of user authentication
     * @param dockStationId         - dock station's id
     * @return {@link Message}
     */
    public Message returnBike(String authenticationToken, int dockStationId) {
        int userId = appUserService.decodeAuthenticationToken(authenticationToken);
        Rental rental;
        PaymentTransaction refundTransaction;
        // finish rental
        try {
            rental = rentalService.getCurrentRentalByUserId(userId);
            rentalService.finish(rental);
        } catch (DataSourceException ex) {
            LOGGER.info(ex.getMessage());
            return new FailureMessage(ex.getMessage(), "Please try again!");
        }
        // refund rental
        try {
            int rentalId = rental.getRentId();
            int amount = rentalService.calculateDeposit(rental) - rentalService.calculateCost(rental);
            String content = "rental_refund_" + rentalId;
            refundTransaction = paymentService.refundRent(amount, content, paymentService.getCreditCardToRefund(rentalId));
        } catch (DataSourceException ex) {
            LOGGER.info(ex.getMessage());
            return new FailureMessage(ex.getMessage(), "Please try again!");
        }
        // persist data
        try {
            try {
                //begin transaction
                dataSource.enableTransaction();
                // update payment information
                paymentService.insertInvoice(refundTransaction, rental, Invoice.TYPE_REFUND);
                // update facility information
                String bikeBarcode = rental.getBike().getBarcode();
                bikeService.updateReturnedBikeLocation(bikeBarcode, dockStationId);
                dockStationService.updateCapacity(dockStationId);
                // commit transaction
                dataSource.getConnection().commit();
                return new SuccessMessage("Succeeded", "See you soon next time!");
            } catch (DataSourceException ex) {
                LOGGER.info(ex.getMessage());
                // rollback transaction
                dataSource.getConnection().rollback();
                return new FailureMessage(new InternalServerErrorException().getMessage(), "Please try again!");
            }
        } catch (SQLException ex) {
            LOGGER.info(ex.getMessage());
            return new FailureMessage(ex.getMessage(), "Please try again!");
        } finally {
            dataSource.disableTransaction();
        }
    }
}