package com.isd.ict.capstoneproject.views.handler.home;

import com.isd.ict.capstoneproject.station.DockStation;
import com.isd.ict.capstoneproject.utils.Utils;
import com.isd.ict.capstoneproject.views.handler.FXMLScreenHandler;
import com.isd.ict.capstoneproject.views.handler.ScreenElementObservable;
import com.isd.ict.capstoneproject.views.handler.ScreenElementObserver;
import com.isd.ict.capstoneproject.views.handler.popup.PopupScreenHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.logging.Logger;
/**
 * The {@link HomeStationListItemHandler homeStationListItemHandler} class handle action on home station list item.
 * @author Group 3
 */
public class HomeStationListItemHandler extends FXMLScreenHandler implements ScreenElementObservable {
    private static final Logger LOGGER = Utils.getLogger(HomeStationListItemHandler.class.getName());
    /**
     * dock station
     */
    private final DockStation dockStation;
    /**
     * home screen handler
     */
    private final ScreenElementObserver homeScreenHandler;
    /**
     * label station name
     */
    @FXML
    protected Label lbStationName;
    /**
     * label station address
     */
    @FXML
    protected Label lbStationAddress;
    /**
     * lable station number of bikes
     */
    @FXML
    protected Label lbStationNoBikes;
    /**
     * label station distance
     */
    @FXML
    protected Label lbStationDistance;
    /**
     * button view station detail
     */
    @FXML
    protected Button btnViewStationDetail;

    HomeStationListItemHandler(String screenPath, DockStation dockStation, HomeScreenHandler homeScreenHandler) throws IOException {
        super(screenPath);
        this.dockStation = dockStation;
        this.homeScreenHandler = homeScreenHandler;
        setupData();
        setupFunctionality();
    }

    /**
     * Set data to home station list item
     */
    void setupData() {
        lbStationName.setText(dockStation.getName());
        lbStationAddress.setText(dockStation.getAddress());
        lbStationDistance.setText(String.valueOf(dockStation.getDistance()));
        lbStationNoBikes.setText(String.valueOf(dockStation.getCapacity()));
    }

    /**
     * Handle action for view station detail button
     */
    void setupFunctionality() {
        btnViewStationDetail.setOnMouseClicked(event -> {
            try {
                viewStationDetail(event);
            } catch (IOException ex) {
                LOGGER.info(ex.getMessage());
            }
        });
    }

    /**
     * View station detail
     *
     * @param event
     * @throws IOException
     */
    void viewStationDetail(MouseEvent event) throws IOException {
        notifyObservers();
    }

    /**
     * Notify observers to update home screen
     * @throws IOException
     */
    @Override
    public void notifyObservers() throws IOException{
        try {
            homeScreenHandler.update(this);
        } catch (Exception ex) {
            ex.printStackTrace();
            PopupScreenHandler.error("Screen interaction triggers an error!");
        }
    }

    /**
     * Get dock station
     * @return {@link DockStation dockStation}
     */
    DockStation getDockStation() {
        return this.dockStation;
    }
}
