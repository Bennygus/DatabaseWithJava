package org.example;

import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;

public class Controller {


    public ColorPicker colorPicker;
    public Canvas canvas;
    private ShapeType shapeType = ShapeType.CIRCLE;

    public void circleClicked(ActionEvent actionEvent) {
       shapeType = ShapeType.CIRCLE;

    }


    public void rectClicked(ActionEvent actionEvent) {
        shapeType = ShapeType.RECT;
    }


    public void lineClicked(ActionEvent actionEvent) {

        shapeType = ShapeType.LINE;
    }

    public void onMouseClicked(MouseEvent event) {

        var gc = canvas.getGraphicsContext2D();
        gc.setFill(colorPicker.getValue());

        switch (shapeType) {
            case CIRCLE -> gc.fillOval(event.getX() - 10, event.getY() - 10, 20, 20);
            case RECT -> gc.fillRect(event.getX() - 10, event.getY() - 10, 20, 20);
            case LINE -> gc.strokeLine(event.getX(),event.getY(),20,0);


        }
    }
}
