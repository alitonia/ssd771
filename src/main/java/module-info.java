module com.isd.ict.capstoneproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.logging;
    requires java.sql;
    requires static lombok;

    opens com.isd.ict.capstoneproject to javafx.fxml;
    opens com.isd.ict.capstoneproject.views.handler to javafx.fxml;
    opens com.isd.ict.capstoneproject.views.handler.home to javafx.fxml;
    opens com.isd.ict.capstoneproject.views.handler.station to javafx.fxml;
    opens com.isd.ict.capstoneproject.views.handler.popup to javafx.fxml;
    opens com.isd.ict.capstoneproject.views.handler.rental to javafx.fxml;
    opens com.isd.ict.capstoneproject.views.handler.payment to javafx.fxml;
    opens com.isd.ict.capstoneproject.views.handler.result to javafx.fxml;

    exports com.isd.ict.capstoneproject;

}
