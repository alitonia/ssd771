package com.isd.ict.capstoneproject.controller;

import com.isd.ict.capstoneproject.bike.BikeService;
import com.isd.ict.capstoneproject.bike.BikeServiceImpl;
import com.isd.ict.capstoneproject.common.exception.db.DataSourceException;
import com.isd.ict.capstoneproject.common.exception.db.NotFoundException;
import com.isd.ict.capstoneproject.common.exception.ebr.InternalServerException;
import com.isd.ict.capstoneproject.common.exception.ebr.InvalidBarcodeException;
import com.isd.ict.capstoneproject.common.exception.ebr.UnavailableBikeException;
import com.isd.ict.capstoneproject.common.exception.ebr.UserRentalNotFinishedException;
import com.isd.ict.capstoneproject.controller.dto.DepositBikeInvoiceDTO;
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
import com.isd.ict.capstoneproject.rental.strategy.RentalStrategy;
import com.isd.ict.capstoneproject.repository.DataSource;
import com.isd.ict.capstoneproject.station.DockStationService;
import com.isd.ict.capstoneproject.station.DockStationServiceImpl;
import com.isd.ict.capstoneproject.user.AppUser;
import com.isd.ict.capstoneproject.user.AppUserService;
import com.isd.ict.capstoneproject.user.AppUserServiceImpl;
import com.isd.ict.capstoneproject.utils.Utils;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The {@link RentBikeController rentBikeController} class provides functionalities for rent bike.
 *
 */
public class RentBikeController {
    private static final Logger LOGGER = Utils.getLogger(RentBikeController.class.getName());
    /**
     * singleton instance
     */
    private static RentBikeController INSTANCE;
    /**
     * Data source component
     */
    private final DataSource dataSource;

    /**
     * Payment controller
     */
    private final PaymentService paymentService;

    /**
     * Rental service
     */
    private final RentalService rentalService;

    /**
     * Bike service
     */
    private final BikeService bikeService;

    /**
     * Dock station service
     */
    private final DockStationService dockStationService;

    /**
     * User service
     */
    private final AppUserService appUserService;

    public static RentBikeController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RentBikeController();
        }
        return INSTANCE;
    }

    private RentBikeController() {
        this.dataSource = DataSource.getInstance();
        this.paymentService = new PaymentServiceImpl();
        this.rentalService = new RentalServiceImpl();
        this.bikeService = new BikeServiceImpl();
        this.dockStationService = new DockStationServiceImpl();
        this.appUserService = new AppUserServiceImpl();
    }

    /**
     * Validate the bike barcode from input
     *
     * @param barcode - the barcode of the bike
     * @throws InvalidBarcodeException - if the input barcode is not valid
     */
    private boolean validateBikeBarcode(String barcode) throws InvalidBarcodeException {
        if (Objects.isNull(barcode)) {
            return false;
        }
        try {
            barcode = barcode.trim();
            long d = Long.parseLong(barcode);
        } catch (NumberFormatException e) {
            LOGGER.info("Error occurred: " + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Rent bike, then return the corresponding rental
     *
     * @param barcode - the barcode of the bike
     * @return {@link Rental}     - if the renting is successful
     */
    public Rental requestRentBike(String authenticationToken, String barcode, int rentalStrategy) throws InvalidBarcodeException, UnavailableBikeException, InternalServerException, UserRentalNotFinishedException {
        if (validateBikeBarcode(barcode)) {
            try {
                int userId = appUserService.decodeAuthenticationToken(authenticationToken);
                AppUser appUser = new AppUser(userId);
                try {
                    rentalService.getCurrentRentalByUserId(userId);
                } catch (NotFoundException e) {
                    return rentalService.createNewRental(appUser, barcode, rentalStrategy);
                }
                throw new UserRentalNotFinishedException();
            } catch (DataSourceException e) {
                LOGGER.info(e.getMessage());
                e.printStackTrace();
                throw new InternalServerException();
            }
        } else throw new InvalidBarcodeException();
    }

    /**
     * Make invoice to display for customer review before pay
     *
     * @param rental    - current rental
     * @return  {@link DepositBikeInvoiceDTO}   - Deposit bike invoice data
     */
    public DepositBikeInvoiceDTO creatInvoice(Rental rental) {
        return DepositBikeInvoiceDTO.builder()
                .rental(rental)
                .amount(rentalService.calculateDeposit(rental))
                .build();
    }

    /**
     * Confirm deposit bike
     *
     * @param invoiceDTO            - Invoice data
     * @param cardCode              - Card code
     * @param owner                 - Owner
     * @param dateExpired           - Date expired
     * @param cvvCode               - Cvv code
     * @return {@link Message}      - success or fail
     */
    public Message confirmDepositBike(DepositBikeInvoiceDTO invoiceDTO, String cardCode, String owner, String dateExpired, String cvvCode) {

        PaymentTransaction transaction;
        System.out.println("______");
        // pay deposit
        try {
            String content = "rental_deposit";
            transaction = paymentService.payRent(invoiceDTO.getAmount(), content, cardCode, owner, dateExpired, cvvCode);
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            System.out.println("___1___");

            return new FailureMessage("Failed\n" + ex.getMessage(), "Please try again!");
        }

        // persist data
        try {
            try {
                // begin transaction
                dataSource.enableTransaction();
                // save rental
                System.out.println("___2_1__");

                Rental rental = invoiceDTO.getRental();
                System.out.println("___2_2__");

                rental = rentalService.insert(rental);
                // save invoice
                System.out.println("___2_3__");

                paymentService.insertInvoice(transaction, rental, Invoice.TYPE_PAY);
                // update facility
                System.out.println("___2_4__");

                String rentedBikeBarcode = rental.getBike().getBarcode();
                System.out.println("___2_5__");

                int locationToUpdate = rental.getBike().getLocation().getDockStationId();
                System.out.println("___2_6__");

                bikeService.updateRentedBikeLocation(rentedBikeBarcode);
                System.out.println("___2_7__");

                dockStationService.updateCapacity(locationToUpdate);
                System.out.println("___2_8__");

                // start rental and commit transaction
                rentalService.start(rental);
                System.out.println("___2_9__");

                dataSource.getConnection().commit();
                return new SuccessMessage("Succeeded", "Enjoy your ride!");
            } catch (DataSourceException ex) {
                System.out.println("___2___");

                LOGGER.info(ex.getMessage());
                String content = "rental_refund_ebr_internal_server_error";
                // rollback transaction
                dataSource.getConnection().rollback();
                // refund
                paymentService.refundRent(invoiceDTO.getAmount(), content, transaction.getCard());
                return new FailureMessage(new InternalServerException().getMessage(), "Please try again!");
            } finally {
                dataSource.disableTransaction();
            }
        } catch (SQLException ex) {
            System.out.println("___3___");

            LOGGER.info(ex.getMessage());
            return new FailureMessage(new InternalServerException().getMessage(), "Please try again!");
        }
    }

    public List<RentalStrategy> getAllRentalStrategies() throws DataSourceException {
        return rentalService.getAllRentalStrategies();
    }
}
