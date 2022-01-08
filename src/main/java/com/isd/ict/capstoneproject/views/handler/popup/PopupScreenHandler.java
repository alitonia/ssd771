package com.isd.ict.capstoneproject.views.handler.popup;

import com.isd.ict.capstoneproject.views.handler.BaseScreenHandler;
import com.isd.ict.capstoneproject.views.handler.ViewsConfigs;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
/**
 * The {@link PopupScreenHandler popupScreenHandler} class handle action on pop up screen.
 * @author Group 3
 */
public class PopupScreenHandler extends BaseScreenHandler {
    /**
     * icon
     */
    @FXML
    ImageView icon;
    /**
     * message
     */
    @FXML
    Label message;

    public PopupScreenHandler(Stage stage) throws IOException{
        super(stage, ViewsConfigs.POPUP_PATH);
    }

    /**
     * Popup message
     *
     * @param message
     * @param imagePath
     * @param undecorated
     * @return {@link PopupScreenHandler popupScreenHandler}
     * @throws IOException
     */
    private static PopupScreenHandler popup(String message, String imagePath, Boolean undecorated) throws IOException{
        PopupScreenHandler popup = new PopupScreenHandler(new Stage());
        if (undecorated) popup.stage.initStyle(StageStyle.UNDECORATED);
        popup.message.setText(message);
        popup.setImage(imagePath);
        return popup;
    }

    /**
     * Success message
     *
     * @param message
     * @throws IOException
     */
    public static void success(String message) throws IOException{
        popup(message, ViewsConfigs.IMAGE_PATH + "/" + "success.png", true).show(true);
    }

    /**
     * Error message
     *
     * @param message
     * @throws IOException
     */
    public static void error(String message) throws IOException{
        popup(message, ViewsConfigs.IMAGE_PATH + "/" + "fail.png", false).show(false);
    }

    /**
     * Loading
     *
     * @param message
     * @return {@link PopupScreenHandler popupScreenHandler}
     * @throws IOException
     */
    public static PopupScreenHandler loading(String message) throws IOException{
        return popup(message, ViewsConfigs.IMAGE_PATH + "/" + "loading2.gif", true);
    }

    /**
     * Set image
     *
     * @param path
     */
    public void setImage(String path) {
        super.setImage(icon, path);
    }

    /**
     * Show popup
     *
     * @param autoclose
     */
    public void show(Boolean autoclose) {
        super.show();
        if (autoclose) close(0.8);
    }

    /**
     * Show popup
     *
     * @param time
     */
    public void show(double time) {
        super.show();
        close(time);
    }

    /**
     * Close popup
     *
     * @param time
     */
    public void close(double time) {
        PauseTransition delay = new PauseTransition(Duration.seconds(time));
        delay.setOnFinished( event -> stage.close() );
        delay.play();
    }
}
