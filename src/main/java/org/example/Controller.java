package org.example;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Controller {


    public ColorPicker colorPicker;
    public Canvas canvas;
    public Button circleButton;
    public Button rectButton;
    public TextField textField;

    private ShapeType shapeType = ShapeType.CIRCLE;
    public BooleanProperty enableOnlyOneButton = new SimpleBooleanProperty();
    public StringProperty textFieldString = new SimpleStringProperty();

    public  void initialize(){

        colorPicker.setValue(Color.BLACK);
        circleButton.disableProperty().bind(enableOnlyOneButton);
        rectButton.disableProperty().bind(enableOnlyOneButton.not());


    }

    public void circleClicked(ActionEvent actionEvent) {
       shapeType = ShapeType.CIRCLE;
       enableOnlyOneButton.setValue(true);

    }


    public void rectClicked(ActionEvent actionEvent) {
        shapeType = ShapeType.RECT;
        enableOnlyOneButton.setValue(false);
    }


    public void lineClicked(ActionEvent actionEvent) {

        shapeType = ShapeType.LINE;
        enableOnlyOneButton.setValue(false);
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
