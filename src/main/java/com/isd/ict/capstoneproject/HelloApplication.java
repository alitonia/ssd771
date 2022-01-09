package com.isd.ict.capstoneproject;

import impl.org.controlsfx.ReflectionUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


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
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

public class HelloApplication extends Application {

    private static final Logger LOGGER = Utils.getLogger(HelloApplication.class.getName());

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            System.out.println("--");
            System.out.println(ViewsConfigs.SPLASH_SCREEN_PATH);
//            Utils.sideLoader(ViewsConfigs.SPLASH_SCREEN_PATH, this.getClass().getMethod("getResource"), this);

            var x = new ViewsConfigs();

            var fs = ViewsConfigs.class.getDeclaredFields();
            var fieldToAsset = new HashMap<String, URL>();

            for (var field : fs) {
                System.out.println(field.getName());
                System.out.println(ViewsConfigs.class.getField(field.getName()));
                System.out.println(field.get(x));
                var localAssetsStr = field.get(x).toString();
                var url = this.getClass().getResource(localAssetsStr);
                fieldToAsset.put(localAssetsStr, url);
            }
            Utils.sideLoader(fieldToAsset);

            System.out.println("ml");

            System.out.println(Utils.getFXML(ViewsConfigs.RETURN_BIKE_DOCK_LIST_PATH));
            System.out.println(this.getClass().getResource(ViewsConfigs.RETURN_BIKE_DOCK_LIST_PATH));


            System.out.println(this.getClass().getResource(ViewsConfigs.SPLASH_SCREEN_PATH));
            System.out.println(this.getClass().getResource("/views/fxml/splash.fxml"));
            System.out.println(this.getClass().getClassLoader().getResource(ViewsConfigs.SPLASH_SCREEN_PATH));
            System.out.println("--");


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
                    HomeScreenHandler homeScreenHandler = new HomeScreenHandler(primaryStage, ViewsConfigs.HOME_DOCK_LIST_SCREEN_PATH);
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
