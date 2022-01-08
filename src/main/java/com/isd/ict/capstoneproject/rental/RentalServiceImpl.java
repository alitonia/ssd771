package com.isd.ict.capstoneproject.rental;

import com.isd.ict.capstoneproject.bike.BikeService;
import com.isd.ict.capstoneproject.bike.BikeServiceImpl;
import com.isd.ict.capstoneproject.common.exception.db.DataSourceException;
import com.isd.ict.capstoneproject.common.exception.ebr.UnavailableBikeException;
import com.isd.ict.capstoneproject.rental.strategy.RentalStrategy;
import com.isd.ict.capstoneproject.rental.strategy.RentalStrategyRepo;
import com.isd.ict.capstoneproject.rental.strategy.RentalStrategyRepoImpl;
import com.isd.ict.capstoneproject.user.AppUser;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The {@link RentalRepoImpl rentalRepoImpl} class is service of rental.
 *
 * @author Group 3
 *
 */
public class RentalServiceImpl implements RentalService {

    /**
     * Rental repo
     */
    private RentalRepo rentalRepo;
    /**
     * Bike repository
     */
    private BikeService bikeService;
    /**
     * Rental strategy repository
     */
    private RentalStrategyRepo rentalStrategyRepo;

    public RentalServiceImpl() {
        this.rentalRepo = new RentalRepoImpl();
        this.bikeService = new BikeServiceImpl();
        this.rentalStrategyRepo = new RentalStrategyRepoImpl();
    }

    /**
     * Create new rental
     * @param appUser
     * @param barcode
     * @param rentalStrategy
     * @return {@link Rental rental}
     * @throws UnavailableBikeException
     * @throws DataSourceException
     */
    public Rental createNewRental(AppUser appUser, String barcode, int rentalStrategy) throws UnavailableBikeException, DataSourceException {
        Rental newRental = new Rental(appUser, bikeService.getBikeIfAvailable(barcode), rentalStrategyRepo.getById(rentalStrategy));
        return newRental;
    }

    /**
     * Start counting time for the rental
     * @param rental
     * @throws DataSourceException
     */
    public void start(Rental rental) throws DataSourceException {
        rental.startTime = LocalDateTime.now();
        rental.status = RentalStatus.STARTED;
        rentalRepo.update(rental);
    }

    /**
     * End counting time of the rental
     * @param rental
     * @throws DataSourceException
     */
    public void finish(Rental rental) throws DataSourceException {
        rental.setRentalDurationByMinutes(rental.rentalStrategy.getCountingTimeStrategy().getCurrentDurationTimeInMinutes(rental));
        rental.finishTime = LocalDateTime.now();
        rental.status = RentalStatus.FINISHED;
        rentalRepo.update(rental);
    }

    public int calculateCost(Rental rental) {
        return rental.rentalStrategy.getCostStrategy().calculateRentalCost(rental);
    }

    public int calculateDeposit(Rental rental) {
        return rental.rentalStrategy.getDepositStrategy().calculateRentalDeposit(rental);
    }

    @Override
    public Rental insert(Rental rental) throws DataSourceException {
        return rentalRepo.insert(rental);
    }

    @Override
    public Rental getCurrentRentalByUserId(int userId) throws DataSourceException {
        return rentalRepo.getCurrentRentalByUserId(userId);
    }

    @Override
    public Rental getById(int rentalId)  throws DataSourceException {
        return rentalRepo.getById(rentalId);
    }

    @Override
    public List<RentalStrategy> getAllRentalStrategies() throws DataSourceException {
        return rentalStrategyRepo.getAll();
    }
}
