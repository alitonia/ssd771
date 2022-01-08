package com.isd.ict.capstoneproject.views.handler.home;

import com.isd.ict.capstoneproject.common.exception.db.DataSourceException;
import com.isd.ict.capstoneproject.common.exception.ebr.InternalServerException;
import com.isd.ict.capstoneproject.common.exception.ebr.NotFoundUserRentalException;
import com.isd.ict.capstoneproject.controller.ViewDockController;
import com.isd.ict.capstoneproject.controller.ViewRentalController;
import com.isd.ict.capstoneproject.controller.dto.RentalDetailDTO;
import com.isd.ict.capstoneproject.station.DockStation;
import com.isd.ict.capstoneproject.utils.Utils;
import com.isd.ict.capstoneproject.views.handler.BaseScreenHandler;
import com.isd.ict.capstoneproject.views.handler.ScreenElementObservable;
import com.isd.ict.capstoneproject.views.handler.ScreenElementObserver;
import com.isd.ict.capstoneproject.views.handler.ViewsConfigs;
import com.isd.ict.capstoneproject.views.handler.popup.PopupScreenHandler;
import com.isd.ict.capstoneproject.views.handler.rental.RentBikeScreenHandler;
import com.isd.ict.capstoneproject.views.handler.rental.ViewRentScreenHandler;
import com.isd.ict.capstoneproject.views.handler.station.StationDetailScreenHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
/**
 * The {@link HomeScreenHandler homeScreenHandler} class handle action on home screen.
 * @author Group 3
 */
public class HomeScreenHandler extends BaseScreenHandler implements ScreenElementObserver {
    private static final Logger LOGGER = Utils.getLogger(HomeScreenHandler.class.getName());
    /**
     * view dock controller
     */
    public ViewDockController viewDockController;
    /**
     * view rental controller
     */
    public ViewRentalController viewRentalController;
    /**
     * button view rent
     */
    @FXML
    private Button btnViewRent;
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
     * text keyword to filter dock station
     */
    @FXML
    private TextField txtFilterDock;
    /**
     * image view filter dock station
     */
    @FXML
    private ImageView ivFilterDock;
    /**
     * list of home station
     */
    private List<HomeStationListItemHandler> homeStationList;

    public HomeScreenHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath, null);
    }

    public HomeScreenHandler(Stage stage, URL screenPath) throws IOException {
        super(stage, screenPath, null);
    }

    public void getPath(){
        System.out.println(this.loader.getLocation());
    }

    /**
     * Initialize home screen
     *
     * @param dto
     * @throws DataSourceException
     * @throws IOException
     */
    @Override
    protected void init(Object dto) throws DataSourceException, IOException {
        this.viewDockController = ViewDockController.getInstance();
        this.viewRentalController = ViewRentalController.getInstance();
        this.homeStationList = new ArrayList<>();

        List<DockStation> list = viewDockController.getAllDockList();
        for (DockStation dockStation : list) {
            HomeStationListItemHandler homeStationListItemHandler = new HomeStationListItemHandler(ViewsConfigs.HOME_STATION_ITEM, dockStation, this);
            homeStationList.add(homeStationListItemHandler);
        }
        vbStationList.getChildren().setAll(homeStationList.stream().map(station -> station.getContent()).collect(Collectors.toList()));
        pnStationList.setContent(vbStationList);
        pnStationList.setPannable(true);
    }


    /**
     * View info of home screen
     *
     * @throws IOException
     */
    @FXML
    public void viewRent() throws IOException {
        try {
            RentalDetailDTO rental = viewRentalController.requestToViewRental(authenticationToken);
            ViewRentScreenHandler viewRentScreenHandler = new ViewRentScreenHandler(this.stage, ViewsConfigs.VIEW_RENTAL_SCREEN_PATH, rental);
            viewRentScreenHandler.setPreviousScreen(this);
            viewRentScreenHandler.setHomeScreenHandler(this.homeScreenHandler);
            viewRentScreenHandler.setScreenTitle("View Rent");
            viewRentScreenHandler.show();
        } catch (NotFoundUserRentalException ex) {
            LOGGER.info(ex.getMessage());
            RentBikeScreenHandler rentBikeScreenHandler = new RentBikeScreenHandler(this.stage, ViewsConfigs.RENT_BIKE_SCREEN_PATH, "");
            rentBikeScreenHandler.setPreviousScreen(this);
            rentBikeScreenHandler.setHomeScreenHandler(this.homeScreenHandler);
            rentBikeScreenHandler.setScreenTitle("Rent New Bike");
            rentBikeScreenHandler.show();
        } catch (InternalServerException ex) {
            PopupScreenHandler.error(ex.getMessage());
        }
    }

    /**
     * Update home station list handler
     * @param observedObject
     * @throws IOException
     */
    @Override
    public void update(ScreenElementObservable observedObject) throws IOException {
        update((HomeStationListItemHandler) observedObject);
    }

    /**
     * Update home station list handler
     * @param homeStationListItemHandler
     * @throws IOException
     */
    void update(HomeStationListItemHandler homeStationListItemHandler) throws IOException {
        StationDetailScreenHandler stationDetailScreenHandler = new StationDetailScreenHandler(this.stage, ViewsConfigs.DOCK_DETAIL_SCREEN_PATH, homeStationListItemHandler.getDockStation().getDockStationId());
        stationDetailScreenHandler.setHomeScreenHandler(this.homeScreenHandler);
        stationDetailScreenHandler.setScreenTitle("Station Detail");
        stationDetailScreenHandler.setPreviousScreen(this);
        stationDetailScreenHandler.show();
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
            homeStationList = new ArrayList<>();
            List<DockStation> list = viewDockController.getDockListByKeyword(txtFilterDock.getText());
            for (DockStation dockStation : list) {
                HomeStationListItemHandler homeStationListItemHandler = new HomeStationListItemHandler(ViewsConfigs.HOME_STATION_ITEM, dockStation, this);
                homeStationList.add(homeStationListItemHandler);
            }
            vbStationList.getChildren().clear();
            vbStationList.getChildren().setAll(homeStationList.stream().map(station -> station.getContent()).collect(Collectors.toList()));
            pnStationList.setContent(vbStationList);
            pnStationList.setPannable(true);
        } catch (DataSourceException ex) {
            PopupScreenHandler.error("Not found!");
        }
    }


    @FXML
    public void goHome(MouseEvent event) throws IOException {
        LOGGER.info("Go Home");
        System.out.println("Go home");
    }
}
