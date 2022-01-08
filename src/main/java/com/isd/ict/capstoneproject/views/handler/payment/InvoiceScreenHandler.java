package com.isd.ict.capstoneproject.views.handler.payment;

import com.isd.ict.capstoneproject.bike.Bike;
import com.isd.ict.capstoneproject.controller.RentBikeController;
import com.isd.ict.capstoneproject.controller.dto.DepositBikeInvoiceDTO;
import com.isd.ict.capstoneproject.controller.dto.message.Message;
import com.isd.ict.capstoneproject.views.handler.BaseScreenHandler;
import com.isd.ict.capstoneproject.views.handler.ViewsConfigs;
import com.isd.ict.capstoneproject.views.handler.result.ResultScreenHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * The {@link InvoiceScreenHandler invoiceScreenHandler} class handle action on invoice screen.
 * @author Group 3
 */
public class InvoiceScreenHandler extends BaseScreenHandler {
    /**
     * Invoice
     */
    private DepositBikeInvoiceDTO invoice;
    /**
     * rent bike controller
     */
    private RentBikeController rentBikeController;
    /**
     * label bike barcode
     */
    @FXML
    private Label lbBikeBarcode;
    /**
     * label bike type
     */
    @FXML
    private Label lbBikeType;
    /**
     * label bike's license plate number
     */
    @FXML
    private Label lbBikeLicensePlateNumber;
    /**
     * label bike's battery percentage
     */
    @FXML
    private Label lbBikeBatteryPercentage;
    /**
     * label bike's location
     */
    @FXML
    private Label lbBikeLocation;
    /**
     * label rental deposit cost
     */
    @FXML
    private Label lbRentalDepositCost;
    /**
     * text card code
     */
    @FXML
    private TextField txtCardNumber;
    /**
     * text owner
     */
    @FXML
    private TextField txtCardHolderName;
    /**
     * text date expiration
     */
    @FXML
    private TextField txtCardExpiredDate;
    /**
     * text cvvCode
     */
    @FXML
    private TextField txtCardSecurityCode;
    /**
     * button confirm pay deposit rent
     */
    @FXML
    private Button btnConfirmPayDepositRent;

    public InvoiceScreenHandler(Stage stage, String screenPath, DepositBikeInvoiceDTO invoice) throws IOException {
        super(stage, screenPath, invoice);
    }

    @Override
    protected void init(Object invoice) {
        init((DepositBikeInvoiceDTO) invoice);
    }

    /**
     * Initialize for invoice screen
     *
     * @param invoice
     */
    protected void init(DepositBikeInvoiceDTO invoice) {
        this.invoice = invoice;
        this.rentBikeController = RentBikeController.getInstance();

        Bike bike = this.invoice.getRental().getBike();
        lbBikeBarcode.setText(bike.getBarcode());
        lbBikeType.setText(bike.getType().getBikeTypeName());
        lbBikeLicensePlateNumber.setText(bike.getLicensePlateNumber());
        lbBikeLocation.setText(bike.getLocation().getName());
        lbRentalDepositCost.setText(String.valueOf(invoice.getAmount()));
    }

    /**
     * Confirm to rent bike
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void confirmToRentBike(MouseEvent event) throws IOException {
        // collect data
        String cardCode = txtCardNumber.getText();
        String owner = txtCardHolderName.getText();
        String dateExpired = txtCardExpiredDate .getText();
        String cvvCode = txtCardSecurityCode.getText();
        // send payload to controller
        Message result = rentBikeController.confirmDepositBike(invoice, cardCode, owner, dateExpired, cvvCode);
        ResultScreenHandler resultScreenHandler = new ResultScreenHandler(this.stage, ViewsConfigs.RESULT_SCREEN_PATH, result);
        resultScreenHandler.setPreviousScreen(this);
        resultScreenHandler.setScreenTitle("Result");
        resultScreenHandler.setHomeScreenHandler(this.homeScreenHandler);
        resultScreenHandler.show();
    }
}
