package visualization;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

import java.util.ArrayList;
import java.util.List;

/*
either a term node or course node
make used for selection
*/
class BoardComponent extends Region implements Subject {

    private List<Observer> observers = new ArrayList<>();

    public void makeDraggable() {
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
            }
        });
        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                getScene().setCursor(Cursor.HAND);
                notifyObservers(true);
            }
        });
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
}
