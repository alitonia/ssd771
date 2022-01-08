package com.isd.ict.capstoneproject.views.handler.rental;

import com.isd.ict.capstoneproject.bike.Bike;
import com.isd.ict.capstoneproject.bike.type.BikeType;
import com.isd.ict.capstoneproject.controller.ReturnBikeController;
import com.isd.ict.capstoneproject.controller.ViewRentalController;
import com.isd.ict.capstoneproject.controller.dto.RentalDetailDTO;
import com.isd.ict.capstoneproject.rental.Rental;
import com.isd.ict.capstoneproject.utils.Utils;
import com.isd.ict.capstoneproject.views.handler.BaseScreenHandler;
import com.isd.ict.capstoneproject.views.handler.ViewsConfigs;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Logger;
/**
 * The {@link ViewRentScreenHandler viewRentScreenHandler}
 * class handle action on view rent screen.
 * @author Group 3
 */
public class ViewRentScreenHandler extends BaseScreenHandler {

    private static final Logger LOGGER = Utils.getLogger(ViewRentScreenHandler.class.getName());
    /**
     * rental
     */
    private RentalDetailDTO rentalDetailDTO;
    /**
     * view rent controller
     */
    private ViewRentalController viewRentalController;
    /**
     * return bike controller
     */
    private ReturnBikeController returnBikeController;
    /**
     * button return bike
     */
    @FXML
    private Button btnReturnBike;
    /**
     * label bike type
     */
    @FXML
    private Label lbBikeType;
    /**
     * label license plate
     */
    @FXML
    private Label lbLicensePlate;
    /**
     * label battery
     */
    @FXML
    private Label lbBattery;
    /**
     * label start time
     */
    @FXML
    private Label lbStartTime;
    /**
     * label deposit amount
     */
    @FXML
    private Label lbDepositAmount;
    /**
     * label rent amount
     */
    @FXML
    private Label lbRentAmount;
    /**
     * label valid rent time
     */
    @FXML
    private Label lbValidRentTime;

    public ViewRentScreenHandler(Stage stage, String screenPath, RentalDetailDTO rental) throws IOException {
        super(stage, screenPath, rental);
    }

    @Override
    protected void init(Object rental) throws IOException {
        this.returnBikeController = ReturnBikeController.getInstance();
        this.viewRentalController = ViewRentalController.getInstance();
        init((RentalDetailDTO) rental);
    }

    /**
     * Initialize for view rent screen
     *
     * @param rentalDetailDTO
     * @throws IOException
     */
    protected void init(RentalDetailDTO rentalDetailDTO) throws IOException {
        this.rentalDetailDTO = rentalDetailDTO;
        Rental rental = rentalDetailDTO.getRental();
        Bike bike = rental.getBike();
        BikeType bikeType = bike.getType();
        lbBikeType.setText(bikeType.getBikeTypeName());
        lbLicensePlate.setText(bike.getLicensePlateNumber());
        lbBattery.setText(String.valueOf(bike.getBatteryPercentage() != null ? bike.getBatteryPercentage() : ""));
        lbStartTime.setText(String.valueOf(rental.getStartTime()));
        lbDepositAmount.setText(String.valueOf(rentalDetailDTO.getDepositAmount()));
        lbRentAmount.setText(String.valueOf(rentalDetailDTO.getCostAmount()));
    }

    /**
     * Return bike and go to return bike screen
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void returnBike(MouseEvent event) throws IOException {
        ReturnBikeScreenHandler returnBikeScreenHandler = new ReturnBikeScreenHandler(this.stage, ViewsConfigs.RETURN_BIKE_DOCK_LIST_PATH);
        returnBikeScreenHandler.setHomeScreenHandler(this.homeScreenHandler);
        returnBikeScreenHandler.setPreviousScreen(this);
        returnBikeScreenHandler.setScreenTitle("Return Bike");
        returnBikeScreenHandler.show();
    }

}
