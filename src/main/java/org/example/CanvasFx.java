package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CanvasFx extends Application {

    @Override
    public void start(Stage primarystage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/canvasfx.fxml"));
        Scene scene = new Scene(loader.load());
        primarystage.setScene(scene);
        primarystage.show();

    }

}





















