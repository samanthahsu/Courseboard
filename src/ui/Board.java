package ui;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class Board extends Pane {

    DoubleProperty myScale = new SimpleDoubleProperty(1.0);

    public Board() {
        super();
        setPrefSize(400, 300);
        setStyle("-fx-background-color: lightgrey; -fx-border-color: red;");

        // add scale transform
        scaleXProperty().bind(myScale);
        scaleYProperty().bind(myScale);

        // logging
        addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            System.out.println(
                    "canvas event: " + ( ((event.getSceneX() - getBoundsInParent().getMinX()) / getScale()) + ", scale: " + getScale())
            );
            System.out.println( "canvas bounds: " + getBoundsInParent());
        });

    }

    public double getScale() {
        return myScale.get();
    }

    /**
     * Set x/y scale
     */
    public void setScale( double scale) {
        myScale.set(scale);
    }

    /**
     * Set x/y pivot points
     * @param x
     * @param y
     */
    public void setPivot( double x, double y) {
        setTranslateX(getTranslateX()-x);
        setTranslateY(getTranslateY()-y);
    }
}
