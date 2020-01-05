package visualization;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Board extends Pane {
//    todo make custom zoomable
//    make pane in pane
//    inner pane holds all the course info
//    outer pane makes sure that coordinates remain consistent
//      the drags are calculated using the outer pane

    Pane innerPane = new Pane();
    final static double MIN_SCALE = 0.1;
    final static double MAX_SCALE = 2;
    DoubleProperty scale = new SimpleDoubleProperty(1.0);

    public Board() {
        setPrefSize(1024, 1024);
        initInnerPane();
        getChildren().add(innerPane);
        innerPane.toBack();
    }

    private void initInnerPane() {
        innerPane.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, null, null)));
        innerPane.scaleXProperty().bind(scale);
        innerPane.scaleYProperty().bind(scale);
        innerPane.setPrefSize(1024, 1024);
        innerPane.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                double delta = 1.1;
                double sizeDelta = 1.1;
                double newScale = scale.get();
                double oldScale = newScale;
                if (event.getDeltaY() < 0) {
                    newScale /= delta;
//                    innerPane.resize(innerPane.getWidth() * sizeDelta, innerPane.getHeight() * sizeDelta);
                } else {
                    newScale *= delta;
                }
                newScale = restrict(newScale, MIN_SCALE, MAX_SCALE);
                scale.set(newScale);
            }
        });
    }

    private double restrict(double newScale, double minScale, double maxScale) {
        if (newScale < minScale) return minScale;
        if (newScale > maxScale) return maxScale;
        return newScale;
    }

    public void setScale(double scale) {
        this.scale.set(scale);
    }

    public void translate(double x, double y) {
        innerPane.setTranslateX(getTranslateX()-x);
        innerPane.setTranslateY(getTranslateY()-y);
    }
}
