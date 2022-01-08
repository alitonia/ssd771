package com.isd.ict.capstoneproject.views.handler.station;

import com.isd.ict.capstoneproject.bike.Bike;
import com.isd.ict.capstoneproject.common.exception.db.DataSourceException;
import com.isd.ict.capstoneproject.controller.ViewDockController;
import com.isd.ict.capstoneproject.controller.dto.DockDetailDTO;
import com.isd.ict.capstoneproject.station.DockStation;
import com.isd.ict.capstoneproject.utils.Utils;
import com.isd.ict.capstoneproject.views.handler.BaseScreenHandler;
import com.isd.ict.capstoneproject.views.handler.ScreenElementObservable;
import com.isd.ict.capstoneproject.views.handler.ScreenElementObserver;
import com.isd.ict.capstoneproject.views.handler.ViewsConfigs;
import com.isd.ict.capstoneproject.views.handler.rental.RentBikeScreenHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
/**
 * The {@link StationDetailScreenHandler stationDetailScreenHandler}
 * class handle action on station detail screen.
 */
public class StationDetailScreenHandler extends BaseScreenHandler implements ScreenElementObserver {
    private static final Logger LOGGER = Utils.getLogger(StationDetailScreenHandler.class.getName());
    /**
     * view dock controller
     */
    private ViewDockController viewDockController;
    /**
     * scroll pane bike list
     */
    @FXML
    private ScrollPane pnBikeList;
    /**
     * vbox bike list
     */
    @FXML
    private VBox vbBikeList;
    /**
     * label dock name
     */
    @FXML
    private Label lbDockName;
    /**
     * label dock address
     */
    @FXML
    private Label lbDockAddress;
    /**
     * label dock empty
     */
    @FXML
    private Label lbDockEmpty;
    /**
     * label dock distance
     */
    @FXML
    private Label lbDockDistance;
    /**
     * vbox bike type list
     */
    @FXML
    private Label lbDockWalkingTimeByMinutes;

    @FXML
    private VBox bikeTypeList;
    /**
     * list of station detail bike item handler
     */
    private List<StationDetailBikeItemHandler> stationDetailBikeItemHandlerList;
    /**
     * list of number of bike each type
     */
    private List<HBox> noOfBikeEachTypeList;

    public StationDetailScreenHandler(Stage stage, String screenPath, int dockStationId) throws IOException {
        super(stage, screenPath, dockStationId);
    }

    @Override
    protected void init(Object dockStationId) throws DataSourceException, IOException {
        this.viewDockController = ViewDockController.getInstance();
        init((int) dockStationId);
    }

    /**
     * Initialize for dock station
     * @param dockStationId
     * @throws IOException
     * @throws DataSourceException
     */
    private void init(int dockStationId) throws IOException, DataSourceException {
        stationDetailBikeItemHandlerList = new ArrayList<>();
        noOfBikeEachTypeList = new ArrayList<HBox>();
        DockDetailDTO dto = viewDockController.getDockStationDetails(dockStationId);
        DockStation dockStation = dto.getDockStation();

        for (Bike bike : dto.getBikeList()) {
            StationDetailBikeItemHandler stationDetailBikeItemHandler = new StationDetailBikeItemHandler(ViewsConfigs.STATION_DETAIL_BIKE_ITEM, bike, this);
            stationDetailBikeItemHandlerList.add(stationDetailBikeItemHandler);
        }
        vbBikeList.getChildren().setAll(stationDetailBikeItemHandlerList.stream().map(station -> station.getContent()).collect(Collectors.toList()));
        pnBikeList.setContent(vbBikeList);
        pnBikeList.setPannable(true);

        lbDockName.setText(dockStation.getName());
        lbDockAddress.setText(dockStation.getAddress());
        lbDockEmpty.setText(Integer.toString(dockStation.getNoOfEmptySlots()));
        lbDockDistance.setText(Integer.toString(dockStation.getDistance()));
        lbDockWalkingTimeByMinutes.setText(Integer.toString(dockStation.getWalkingTimeByMinutes()));

        Font bikeTypeFont = Font.font("Segoe UI", FontPosture.REGULAR, 18);
        dto.getBikeTypeCounts().entrySet().forEach(bikeTypeQuantity -> {
            HBox hbBikeTypeDetail = new HBox();

            Label lbBikeTypeName = new Label(bikeTypeQuantity.getKey());
            lbBikeTypeName.setFont(bikeTypeFont);

            Label lbBikeTypeQuantity = new Label(bikeTypeQuantity.getValue().toString());
            lbBikeTypeQuantity.setFont(bikeTypeFont);

            Region filler = new Region();
            HBox.setHgrow(filler, Priority.ALWAYS);
            hbBikeTypeDetail.getChildren().addAll(lbBikeTypeName, filler, lbBikeTypeQuantity);

            noOfBikeEachTypeList.add(hbBikeTypeDetail);
        });

        bikeTypeList.getChildren().setAll(noOfBikeEachTypeList);
    }

    @Override
    public void update(ScreenElementObservable observedObject) throws Exception {
        update((StationDetailBikeItemHandler) observedObject);
    }

    /**
     * Update station detail bike item and move to rent bike screen
     * @param stationDetailBikeItemHandler
     * @throws IOException
     */
    void update(StationDetailBikeItemHandler stationDetailBikeItemHandler) throws IOException {
        RentBikeScreenHandler rentBikeScreenHandler = new RentBikeScreenHandler(this.stage, ViewsConfigs.RENT_BIKE_SCREEN_PATH, stationDetailBikeItemHandler.getBike().getBarcode());
        rentBikeScreenHandler.setHomeScreenHandler(this.homeScreenHandler);
        rentBikeScreenHandler.setPreviousScreen(this);
        rentBikeScreenHandler.setScreenTitle("Rent New Bike");
        rentBikeScreenHandler.show();
    }

}
