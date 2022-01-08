package com.isd.ict.capstoneproject.views.handler;

import com.isd.ict.capstoneproject.common.exception.db.DataSourceException;
import com.isd.ict.capstoneproject.common.exception.ebr.InternalServerException;
import com.isd.ict.capstoneproject.utils.Utils;
import com.isd.ict.capstoneproject.views.handler.home.HomeScreenHandler;
import com.isd.ict.capstoneproject.views.handler.popup.PopupScreenHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.logging.Logger;
/**
 * The {@link BaseScreenHandler baseScreenHandler}
 * class handle common action on each screen.
 * @author Group 3
 */
public abstract class BaseScreenHandler extends FXMLScreenHandler {

    private static final Logger LOGGER = Utils.getLogger(BaseScreenHandler.class.getName());
    /**
     * authentication token
     */
    protected static String authenticationToken = "1_" + new Date().getTime();
    /**
     * scene
     */
    private Scene scene;
    /**
     * previous screen
     */
    private BaseScreenHandler prev;
    /**
     * stage
     */
    protected final Stage stage;
    /**
     * home screen handler
     */
    protected HomeScreenHandler homeScreenHandler;

    private BaseScreenHandler(String screenPath) throws IOException {
        super(screenPath);
        this.stage = new Stage();
    }

    /**
     * Set previous screen
     * @param prev
     */
    public void setPreviousScreen(BaseScreenHandler prev) {
        this.prev = prev;
    }

    /**
     * Get previous screen
     * @return {@link BaseScreenHandler baseScreenHandler}
     */
    public BaseScreenHandler getPreviousScreen() {
        return this.prev;
    }

    public BaseScreenHandler(Stage stage, String screenPath) throws IOException {
        super(screenPath);
        this.stage = stage;
    }

    /**
     * Constructor
     * @param stage
     * @param screenPath
     * @param dto
     * @throws IOException
     */
    public BaseScreenHandler(Stage stage, String screenPath, Object dto) throws IOException {
        super(screenPath);
        this.stage = stage;
        try {
            init(dto);
        } catch (DataSourceException ex) {
            LOGGER.info(ex.getMessage());
            PopupScreenHandler.error("Error when loading data.");
        } catch (InternalServerException ex) {
            LOGGER.info(ex.getMessage());
            PopupScreenHandler.error("Error when processing data.");
        } catch (IOException ex) {
            LOGGER.info(ex.getMessage());
            PopupScreenHandler.error("Error when loading resources.");
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            PopupScreenHandler.error(ex.getMessage());
        }
    }

    public BaseScreenHandler(Stage stage, URL screenPath, Object dto) throws IOException {
        super(screenPath);
        this.stage = stage;
        try {
            init(dto);
        } catch (DataSourceException ex) {
            LOGGER.info(ex.getMessage());
            PopupScreenHandler.error("Error when loading data.");
        } catch (InternalServerException ex) {
            LOGGER.info(ex.getMessage());
            PopupScreenHandler.error("Error when processing data.");
        } catch (IOException ex) {
            LOGGER.info(ex.getMessage());
            PopupScreenHandler.error("Error when loading resources.");
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            PopupScreenHandler.error(ex.getMessage());
        }
    }

    protected void init(Object dto) throws Exception {
        return;
    }

    /**
     * Show scene
     */
    public void show() {
        if (this.scene == null) {
            this.scene = new Scene(this.content);
        }
        this.stage.setScene(this.scene);
        this.stage.show();
    }

    /**
     * Set screen title
     *
     * @param string
     */
    public void setScreenTitle(String string) {
        this.stage.setTitle(string);
    }

    public void setHomeScreenHandler(HomeScreenHandler HomeScreenHandler) {
        this.homeScreenHandler = HomeScreenHandler;
    }

    /**
     * Show previous screen
     */
    @FXML
    void goBack() {
        this.prev.show();
    }
    /**
     * Show home screen
     */
    @FXML
    void goHome() {
        this.homeScreenHandler.setScreenTitle("Home");
        this.homeScreenHandler.show();
    }

}
