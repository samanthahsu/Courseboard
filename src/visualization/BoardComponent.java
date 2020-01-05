package visualization;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/*
either a term node or course node
make used for selection
*/
abstract class BoardComponent extends Region implements Subject {

    private List<Observer> observers = new ArrayList<>();
    protected DropShadow borderGlow;

    public void makeDraggable() {
        Node thisNode = this;
        final double[] delta = new double[2];
        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                getScene().setCursor(Cursor.HAND);
            }
        });
        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                getScene().setCursor(Cursor.DEFAULT);
            }
        });
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                getScene().setCursor(Cursor.CLOSED_HAND);
                delta[0] = getLayoutX() - event.getSceneX();
                delta[1] = getLayoutY() - event.getSceneY();
                notifyObservers(false);
            }
        });
        setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setLayoutX(event.getSceneX() + delta[0]);
                setLayoutY(event.getSceneY() + delta[1]);
                setMouseTransparent(true);
                addCustomOnMouseDragged(event);
            }
        });
        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                getScene().setCursor(Cursor.HAND);
                notifyObservers(true);
                setMouseTransparent(false);
            }
        });
    }

    protected void addCustomOnMouseDragged(MouseEvent event) {
//        to be overridden
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void deleteObserver(Observer o) {
        observers.remove(o);
    }

    //    called whenever need to update other elements in response to dragging
    @Override
    public void notifyObservers(boolean isDraw) {
        for (Observer o : observers) {
            o.update(isDraw);
        }
    }

//    todo create own constructor to make sure this is initialized
    public void buildSelectionGlow() {
        borderGlow = new DropShadow();
        borderGlow.setColor(Color.LAWNGREEN);
        borderGlow.setOffsetX(0f);
        borderGlow.setOffsetY(0f);
        int depth = 50;
        borderGlow.setHeight(depth);
        borderGlow.setWidth(depth);
    }

    public void selectionGlowOn() {
        this.setEffect(borderGlow);
    }
    public void selectionGlowOff() {
        this.setEffect(null);
    }


}
