package visualization;

import javafx.scene.layout.Region;
import javafx.scene.shape.*;

import java.util.Observable;

public class Connection extends Region implements Observer {
//    TODO make connection update with change of requisites

    CourseNode source;
    CourseNode destination;
    boolean connected;

    MoveTo moveTo;
    ArcTo arcTo;
    Path path;

    Connection(CourseNode source, CourseNode destination) {
        if (source == null || destination == null) {
            System.out.println("Illegal connection");
            return;
        }
        this.source = source;
        source.addObserver(this);
        this.destination = destination;
        destination.addObserver(this);
        connected = true;
        draw();
    }

    public void draw() {
        path = new Path();
        moveTo = new MoveTo();
        moveTo.setX(source.getLayoutX());
        moveTo.setY(source.getLayoutY());

        arcTo = new ArcTo();
        arcTo.setX(destination.getLayoutX());
        arcTo.setY(destination.getLayoutY());
        arcTo.setRadiusX(Math.abs(source.getLayoutX() - destination.getLayoutX()));
        arcTo.setRadiusY(Math.abs(source.getLayoutY() - destination.getLayoutY()));
        path.getElements().addAll(moveTo, arcTo);
        getChildren().add(path);
    }

    public void listen() {

    }

    public void connect() {
        connected = true;
    }

    public void disconnect() {
        connected = false;
    }

    @Override
    public void update(boolean isDraw) {
        if (isDraw) {
            draw();
        } else {
            getChildren().remove(path);
            path = null;
        }
    }
}
