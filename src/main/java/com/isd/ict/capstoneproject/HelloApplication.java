package com.isd.ict.capstoneproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


import com.isd.ict.capstoneproject.views.handler.ViewsConfigs;
import com.isd.ict.capstoneproject.utils.Utils;
import com.isd.ict.capstoneproject.views.handler.home.HomeScreenHandler;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

public class HelloApplication extends Application {

    private static final Logger LOGGER = Utils.getLogger(HelloApplication.class.getName());

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {

            // initialize the scene
            StackPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(ViewsConfigs.SPLASH_SCREEN_PATH)));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            System.out.println("b " + HelloApplication.class.getResource("views/fxml/splash.fxml").getPath());
            System.out.println("a " + HelloApplication.class.getResource("views/fxml/hello-view.fxml").getPath());
            System.out.println("a " + HelloApplication.class.getResource("views/fxml/home-station-list.fxml").getPath());


            // Load splash screen with fade in effect
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), root);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            // Finish splash with fade out effect
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), root);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);

            // After fade in, start fade out
            fadeIn.play();
            fadeIn.setOnFinished((e) -> {
                fadeOut.play();
            });

            // After fade out, load actual content
            fadeOut.setOnFinished((e) -> {
                try {
                    System.out.println("c " + HelloApplication.class.getResource("views/fxml/splash.fxml").getPath());
                    System.out.println("d " + HelloApplication.class.getResource("views/fxml/hello-view.fxml").getPath());
                    System.out.println("e " + HelloApplication.class.getResource("views/fxml/home-station-list.fxml").getPath());

                    System.out.println("OK");
                    HomeScreenHandler homeScreenHandler = new HomeScreenHandler(primaryStage, HelloApplication.class.getResource("views/fxml/home-station-list.fxml"));
                    homeScreenHandler.getPath();
                    homeScreenHandler.setHomeScreenHandler(homeScreenHandler);
                    homeScreenHandler.setPreviousScreen(homeScreenHandler);
                    homeScreenHandler.setScreenTitle("Home");
                    homeScreenHandler.show();
                } catch (IOException ex) {
                    LOGGER.info(ex.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() throws Exception {
        Platform.exit();
    }

    public static void main(String[] args) {
        launch();
    }
}
