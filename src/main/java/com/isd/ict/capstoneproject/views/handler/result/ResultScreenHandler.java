package com.isd.ict.capstoneproject.views.handler.result;

import com.isd.ict.capstoneproject.controller.dto.message.FailureMessage;
import com.isd.ict.capstoneproject.controller.dto.message.Message;
import com.isd.ict.capstoneproject.controller.dto.message.SuccessMessage;
import com.isd.ict.capstoneproject.views.handler.BaseScreenHandler;
import com.isd.ict.capstoneproject.views.handler.ViewsConfigs;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
/**
 * The {@link ResultScreenHandler resultScreenHandler}
 * class handle action on result screen.
 */
public class ResultScreenHandler extends BaseScreenHandler {
    /**
     * button confirm
     */
    @FXML
    private Button btnConfirm;
    /**
     * button back
     */
    @FXML
    private Button btnBack;
    /**
     * image view message icon
     */
    @FXML private ImageView ivMessageIcon;
    /**
     * label message type
     */
    @FXML private Label lbMessageType;
    /**
     * label message content
     */
    @FXML private Label lbMessageContent;
    /**
     * label message result
     */
    @FXML private Label lbMessageResult;

    public ResultScreenHandler(Stage stage, String screenPath, Message message) throws IOException {
        super(stage, screenPath, message);
    }

    @Override
    protected void init(Object message) {
        init((Message) message);
    }

    /**
     * Initialize for result screen
     * @param message
     */
    private void init(Message message) {
        if (message instanceof SuccessMessage) {
            handleResult((SuccessMessage) message);
        }
        else if (message instanceof FailureMessage) {
            handleResult((FailureMessage) message);
        }
        else handleResultDefault(message);
    }

    /**
     * Set info if success for result screen
     * @param successMessage
     */
    private void handleResult(SuccessMessage successMessage) {
        File file = new File(ViewsConfigs.IMAGE_PATH + "success.png");
        Image image = new Image(file.toURI().toString());
        ivMessageIcon.setImage(image);

        lbMessageType.setText("Succeeded");
        lbMessageContent.setText(successMessage.getMessage());
        lbMessageResult.setText(successMessage.getResult().toString());
    }

    /**
     * Set info if fail for result screen
     * @param failureMessage
     */
    private void handleResult(FailureMessage failureMessage) {
        File file = new File(ViewsConfigs.IMAGE_PATH + "fail.png");
        Image image = new Image(file.toURI().toString());
        ivMessageIcon.setImage(image);

        lbMessageType.setText("Failed");
        lbMessageContent.setText(failureMessage.getMessage());
        lbMessageResult.setText(failureMessage.getResult().toString());
    }

    private void handleResultDefault(Message message) {
        return;
    }

    /**
     * Confirm result
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    private void confirm(MouseEvent event) throws IOException, SQLException {
        this.homeScreenHandler.setScreenTitle("Home");
        this.homeScreenHandler.show();
    }
}
