package com.isd.ict.capstoneproject.views.handler.rental;

import com.isd.ict.capstoneproject.common.exception.db.DataSourceException;
import com.isd.ict.capstoneproject.common.exception.ebr.InternalServerException;
import com.isd.ict.capstoneproject.common.exception.ebr.InvalidBarcodeException;
import com.isd.ict.capstoneproject.common.exception.ebr.UnavailableBikeException;
import com.isd.ict.capstoneproject.common.exception.ebr.UserRentalNotFinishedException;
import com.isd.ict.capstoneproject.controller.RentBikeController;
import com.isd.ict.capstoneproject.controller.ViewRentalController;
import com.isd.ict.capstoneproject.controller.dto.DepositBikeInvoiceDTO;
import com.isd.ict.capstoneproject.rental.Rental;
import com.isd.ict.capstoneproject.rental.strategy.RentalStrategy;
import com.isd.ict.capstoneproject.utils.Utils;
import com.isd.ict.capstoneproject.views.handler.BaseScreenHandler;
import com.isd.ict.capstoneproject.views.handler.ViewsConfigs;
import com.isd.ict.capstoneproject.views.handler.payment.InvoiceScreenHandler;
import com.isd.ict.capstoneproject.views.handler.popup.PopupScreenHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.logging.Logger;
/**
 * The {@link RentBikeScreenHandler rentBikeScreenHandler} class handle action on rent bike screen.
 */
public class RentBikeScreenHandler extends BaseScreenHandler {
    private static final Logger LOGGER = Utils.getLogger(RentBikeScreenHandler.class.getName());
    /**
     * view rent controller
     */
    private ViewRentalController viewRentalController;
    /**
     * rent bike controller
     */
    private RentBikeController rentBikeController;
    /**
     * text barcode
     */
    @FXML
    private TextField txtBarcode;
    /**
     * button rent bike
     */
    @FXML
    private Button btnRentBike;
    /**
     * button reset barcode
     */
    @FXML
    private Button btnResetBarcode;
    /**
     * combobox rental strategy
     */
    @FXML
    private ComboBox<RentalStrategy> cbRentalStrategy;

    public RentBikeScreenHandler(Stage stage, String screenPath, String barcode) throws IOException{
        super(stage, screenPath, barcode);

    }

    @Override
    protected void init(Object barcode) throws DataSourceException {
        this.viewRentalController = ViewRentalController.getInstance();
        this.rentBikeController = RentBikeController.getInstance();
        init((String) barcode);
    }

    /**
     * Initialize for rent bike screen
     *
     * @param barcode
     * @throws DataSourceException
     */
    private void init(String barcode) throws DataSourceException {
        txtBarcode.setText(barcode);
        Callback<ListView<RentalStrategy>, ListCell<RentalStrategy>> cellFactory = rentalStrategyListView -> new ListCell<>() {
            @Override
            protected void updateItem(RentalStrategy rentalStrategy, boolean empty) {
                super.updateItem(rentalStrategy, empty);
                setText(empty ? "" : new StringBuilder().append(rentalStrategy.getName()).append(": ").append(rentalStrategy.getDescription()).toString());
            }
        };
        cbRentalStrategy.setCellFactory(cellFactory);
        cbRentalStrategy.setButtonCell(cellFactory.call(null));
        cbRentalStrategy.getItems().setAll(rentBikeController.getAllRentalStrategies());
        cbRentalStrategy.getSelectionModel().selectFirst();
    }

    /**
     * Request to rent bike
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void requestRentBike(MouseEvent event) throws IOException {
        try {
            Rental rental = rentBikeController.requestRentBike(authenticationToken, txtBarcode.getText(), cbRentalStrategy.getSelectionModel().getSelectedItem().getId());
            DepositBikeInvoiceDTO invoice = rentBikeController.creatInvoice(rental);

            InvoiceScreenHandler invoiceScreenHandler = new InvoiceScreenHandler(this.stage, ViewsConfigs.INVOICE_SCREEN_PATH, invoice);
            invoiceScreenHandler.setPreviousScreen(this);
            invoiceScreenHandler.setHomeScreenHandler(this.homeScreenHandler);
            invoiceScreenHandler.setScreenTitle("Invoice");
            invoiceScreenHandler.show();
        } catch (InternalServerException | InvalidBarcodeException | UnavailableBikeException | UserRentalNotFinishedException ex) {
            LOGGER.info(ex.getMessage());
            PopupScreenHandler.error(ex.getMessage());
        }
    }

    /**
     * Reset barcode input
     *
     * @param event
     */
    @FXML
    void resetBarcodeInput(MouseEvent event) {
        txtBarcode.setText("");
    }
}
