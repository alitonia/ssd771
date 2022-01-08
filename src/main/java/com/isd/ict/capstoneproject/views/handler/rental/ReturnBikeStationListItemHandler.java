package com.isd.ict.capstoneproject.views.handler.rental;

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
 * The {@link ReturnBikeStationListItemHandler returnBikeStationListItemHandler}
 * class handle action on return bike station list item screen.
 * @author Group 3
 */
public class ReturnBikeStationListItemHandler extends FXMLScreenHandler implements ScreenElementObservable {
    private static final Logger LOGGER = Utils.getLogger(ReturnBikeStationListItemHandler.class.getName());
    /**
     * dock station
     */
    private final DockStation dockStation;
    /**
     * return bike screen handler
     */
    private final ScreenElementObserver returnBikeScreenHandler;
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
     * label number of empty station
     */
    @FXML
    protected Label lbStationNoEmpty;
    /**
     * label station distance
     */
    @FXML
    protected Label lbStationDistance;
    /**
     * button send bike
     */
    @FXML
    protected Button btnSendBike;

    public ReturnBikeStationListItemHandler(String screenPath, DockStation dockStation, ReturnBikeScreenHandler returnBikeScreenHandler) throws IOException {
        super(screenPath);
        this.dockStation = dockStation;
        this.returnBikeScreenHandler = returnBikeScreenHandler;
        setDockStationInfo();
    }

    /**
     * Set info for dock station
     */
    void setDockStationInfo() {
        lbStationName.setText(dockStation.getName());
        lbStationAddress.setText(dockStation.getAddress());
        lbStationDistance.setText(String.valueOf(dockStation.getDistance()));
        lbStationNoEmpty.setText(String.valueOf(dockStation.getMaxCapacity() - dockStation.getCapacity()));
        btnSendBike.setOnMouseClicked(event -> {
            try {
                sendBikeToDockStation(event);
            } catch (IOException ex) {
                LOGGER.info(ex.getMessage());
            }
        });
    }

    /**
     * Send bike back to dock station
     *
     * @param event
     * @throws IOException
     */
    void sendBikeToDockStation(MouseEvent event) throws IOException {
        try {
            notifyObservers();
        } catch (Exception ex) {
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
    /**
     * Notify observers to update return bike screen
     * @throws IOException
     */
    @Override
    public void notifyObservers() throws Exception {
        returnBikeScreenHandler.update(this);
    }
}
