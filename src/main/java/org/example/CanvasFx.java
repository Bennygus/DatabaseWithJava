package org.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CanvasFx extends Application {

    // ctrl+q för info och finns lankar till info

    // får bara köras från denna tråden
    ExecutorService executors = Executors.newSingleThreadExecutor();
    Canvas canvas;
    private ColorPicker colorPicker;
    private ShapeType shapeType = ShapeType.CIRCLE ;

    @Override
    public void stop() throws Exception {
        super.stop();
        executors.shutdown();
    }

    @Override
    public void start(Stage primarystage) throws Exception {

        canvas = new Canvas(400,400);
        canvas.setOnMouseClicked(event -> canvasClicked(event) );


        BorderPane root = new BorderPane();
        root.setCenter(canvas);

        Button circleButton = new Button("Circle");
        circleButton.setOnAction(event -> shapeType=ShapeType.CIRCLE);
        Button rectButton = new Button("Rectangle");
        rectButton.setOnAction(event -> shapeType=ShapeType.RECT);
        Button lineButton = new Button("Line");
        lineButton.setOnAction(event -> shapeType= ShapeType.LINE);

        colorPicker = new ColorPicker();
        // sätta default färg
        colorPicker.setValue(Color.BLACK);

        // kolla på flowplane bilden med top,laft,right,center,bottom
        FlowPane flowPane = new FlowPane();
        flowPane.getChildren().add(circleButton);
        flowPane.getChildren().add(rectButton);
        flowPane.getChildren().add(lineButton);
        flowPane.getChildren().add(colorPicker);

        root.setTop(flowPane);



        var gc = canvas.getGraphicsContext2D();
        gc.setFill(new Color(0,0,1,1));
        gc.fillOval(0,0,100,100);
        gc.setFill(Color.LIME);
        gc.fillRect(200,200,100,100);




        Scene scene = new Scene(root);
        primarystage.setScene(scene);
        primarystage.show();



    }

    private void canvasClicked(MouseEvent event) {



        // kör en executor för nåt som tar tid
        // ctrl+space för lambda
        executors.submit(() -> {
        try {
            //long running task, network not responding
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

            // kör med platform för att uppdatera
            Platform.runLater(() -> {

                var gc = canvas.getGraphicsContext2D();
                gc.setFill(colorPicker.getValue());

                switch (shapeType) {
                    case CIRCLE -> gc.fillOval(event.getX() - 10, event.getY() - 10, 20, 20);
                    case RECT -> gc.fillRect(event.getX() - 10, event.getY() - 10, 20, 20);
                    case LINE -> gc.strokeLine(event.getX(),event.getY(),20,0);


                }

            });




    });


}

}




















