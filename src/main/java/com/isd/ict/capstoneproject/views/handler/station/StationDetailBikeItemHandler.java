package com.isd.ict.capstoneproject.views.handler.station;

import com.isd.ict.capstoneproject.bike.Bike;
import com.isd.ict.capstoneproject.utils.Utils;
import com.isd.ict.capstoneproject.views.handler.FXMLScreenHandler;
import com.isd.ict.capstoneproject.views.handler.ScreenElementObservable;
import com.isd.ict.capstoneproject.views.handler.ScreenElementObserver;
import com.isd.ict.capstoneproject.views.handler.ViewsConfigs;
import com.isd.ict.capstoneproject.views.handler.popup.PopupScreenHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
/**
 * The {@link StationDetailBikeItemHandler stationDetailBikeItemHandler}
 * class handle action on station detail bike item screen.
 */
public class StationDetailBikeItemHandler extends FXMLScreenHandler implements ScreenElementObservable {
    private static final Logger LOGGER = Utils.getLogger(StationDetailBikeItemHandler.class.getName());
    /**
     * bike
     */
    private final Bike bike;
    /**
     * station detail screen handler
     */
    private final ScreenElementObserver stationDetailScreenHandler;
    /**
     * label bike type
     */
    @FXML
    protected Label lbBikeType;
    /**
     * label bike license plate
     */
    @FXML
    protected Label lbBikeLicensePlate;
    /**
     * label bike battery percentage
     */
    @FXML
    protected Label lbBikeBatteryPercentage;
    /**
     * image view bike type image
     */
    @FXML
    protected ImageView ivBikeTypeImage;
    /**
     * button rent bike
     */
    @FXML
    protected Label lbBikeBarcode;
    @FXML
    protected Button btnRentThisBike;

    StationDetailBikeItemHandler(String screenPath, Bike bike, StationDetailScreenHandler observer) throws IOException {
        super(screenPath);
        this.bike = bike;
        this.stationDetailScreenHandler = observer;
        setupData();
        setupFunctionality();
    }

    /**
     * Set info for station detail screen
     */
    void setupData() {
        lbBikeType.setText(bike.getType().getBikeTypeName());
        lbBikeLicensePlate.setText(bike.getLicensePlateNumber());
        lbBikeBatteryPercentage.setText(String.valueOf(bike.getBatteryPercentage() != null ? bike.getBatteryPercentage() : ""));
        lbBikeBarcode.setText(bike.getBarcode());
        File file = new File(ViewsConfigs.IMAGE_PATH + bike.getType().getBikeTypeImageURL());
        Image image = new Image(file.toURI().toString());
        ivBikeTypeImage.setImage(image);
    }

    /**
     * Handle action click rent bike button
     */
    void setupFunctionality() {
        btnRentThisBike.setOnMouseClicked(event -> {
            try {
                rentThisBike(event);
            } catch (IOException ex) {
                LOGGER.info(ex.getMessage());
            }
        });
    }

    /**
     * Rent bike
     *
     * @param event
     * @throws IOException
     */
    void rentThisBike(MouseEvent event) throws IOException {
        notifyObservers();
    }
    /**
     * Notify observers to update station detail screen
     * @throws IOException
     */
    @Override
    public void notifyObservers() throws IOException {
        try {
            stationDetailScreenHandler.update(this);
        } catch (Exception ex) {
            ex.printStackTrace();
            PopupScreenHandler.error("Screen interaction triggers an error!");
        }
    }

    /**
     * Get bike
     * @return {@link Bike bike}
     */
    Bike getBike() {
        return this.bike;
    }
}
