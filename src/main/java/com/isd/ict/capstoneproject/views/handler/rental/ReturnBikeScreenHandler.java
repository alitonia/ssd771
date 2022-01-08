package com.isd.ict.capstoneproject.views.handler.rental;

import com.isd.ict.capstoneproject.common.exception.db.DataSourceException;
import com.isd.ict.capstoneproject.controller.ReturnBikeController;
import com.isd.ict.capstoneproject.controller.ViewDockController;
import com.isd.ict.capstoneproject.controller.dto.message.Message;
import com.isd.ict.capstoneproject.station.DockStation;
import com.isd.ict.capstoneproject.utils.Utils;
import com.isd.ict.capstoneproject.views.handler.BaseScreenHandler;
import com.isd.ict.capstoneproject.views.handler.ScreenElementObservable;
import com.isd.ict.capstoneproject.views.handler.ScreenElementObserver;
import com.isd.ict.capstoneproject.views.handler.ViewsConfigs;
import com.isd.ict.capstoneproject.views.handler.popup.PopupScreenHandler;
import com.isd.ict.capstoneproject.views.handler.result.ResultScreenHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
/**
 * The {@link ReturnBikeScreenHandler returnBikeScreenHandler} class handle action on return bike screen.
 */
public class ReturnBikeScreenHandler extends BaseScreenHandler implements ScreenElementObserver {
    private static final Logger LOGGER = Utils.getLogger(ReturnBikeScreenHandler.class.getName());
    /**
     * view dock controller
     */
    private ViewDockController viewDockController;
    /**
     * return bike controller
     */
    private ReturnBikeController returnBikeController;
    /**
     * scroll pane station list
     */
    @FXML
    private ScrollPane pnStationList;
    /**
     * vbox station list
     */
    @FXML
    private VBox vbStationList;
    /**
     * text filter dock
     */
    @FXML
    private TextField txtFilterDock;
    /**
     * button filter dock
     */
    @FXML
    private ImageView ivFilterDock;
    /**
     * list of return bike station item
     */
    private List<ReturnBikeStationListItemHandler> returnBikeStationList;

    public ReturnBikeScreenHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath, null);
    }

    /**
     * Initialize for return bike screen
     *
     * @param dto
     * @throws DataSourceException
     * @throws IOException
     */
    @Override
    protected void init(Object dto) throws DataSourceException, IOException {
        this.viewDockController = ViewDockController.getInstance();
        this.returnBikeController = ReturnBikeController.getInstance();
        this.returnBikeStationList = new ArrayList<>();

        List<DockStation> dockStationList = viewDockController.getEmptyDockList();
        for (DockStation dockStationItem : dockStationList) {
            ReturnBikeStationListItemHandler homeStationListItemHandler = new ReturnBikeStationListItemHandler(ViewsConfigs.RETURN_BIKE_STATION_ITEM, dockStationItem, this);
            this.returnBikeStationList.add(homeStationListItemHandler);
        }
        vbStationList.getChildren().setAll(returnBikeStationList.stream().map(station -> station.getContent()).collect(Collectors.toList()));
        pnStationList.setContent(vbStationList);
        pnStationList.setPannable(true);
    }

    /**
     * Update return bike station list item
     * @param observedObject
     * @throws Exception
     */
    @Override
    public void update(ScreenElementObservable observedObject) throws Exception {
        update((ReturnBikeStationListItemHandler) observedObject);
    }

    /**
     * Update return bike station list item
     * @param returnBikeStationListItemHandler
     * @throws Exception
     */
    void update(ReturnBikeStationListItemHandler returnBikeStationListItemHandler) throws Exception {
        Message message = returnBikeController.returnBike(authenticationToken, returnBikeStationListItemHandler.getDockStation().getDockStationId());

        ResultScreenHandler resultScreenHandler = new ResultScreenHandler(this.stage, ViewsConfigs.RESULT_SCREEN_PATH, message);
        resultScreenHandler.setHomeScreenHandler(this.homeScreenHandler);
        resultScreenHandler.setPreviousScreen(this);
        resultScreenHandler.setScreenTitle("Result");
        resultScreenHandler.show();
    }

    /**
     * Filter dock station
     * @throws DataSourceException
     * @throws IOException
     */
    @FXML
    public void filterDockStation(MouseEvent event) throws IOException {
        LOGGER.info("Search Icon clicked.");
        try {
            returnBikeStationList = new ArrayList<>();
            List<DockStation> list = viewDockController.getDockListByKeyword(txtFilterDock.getText());
            for (DockStation dockStation : list) {
                ReturnBikeStationListItemHandler returnBikeStationListItemHandler = new ReturnBikeStationListItemHandler(ViewsConfigs.RETURN_BIKE_STATION_ITEM, dockStation, this);
                returnBikeStationList.add(returnBikeStationListItemHandler);
            }
            vbStationList.getChildren().clear();
            vbStationList.getChildren().setAll(returnBikeStationList.stream().map(station -> station.getContent()).collect(Collectors.toList()));
            pnStationList.setContent(vbStationList);
            pnStationList.setPannable(true);
        } catch (DataSourceException ex) {
            PopupScreenHandler.error("Not found!");
        }
    }
}
