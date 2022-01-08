package com.isd.ict.capstoneproject;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    public void goHome(MouseEvent event) throws IOException {
        System.out.println("Go home");
    }

    @FXML
    public void viewRent(MouseEvent event) throws IOException {
        System.out.println("Go home rent");
    }

    @FXML
    public void filterDockStation(MouseEvent event) throws IOException {
        System.out.println("Go home filer");
    }
}
