package visualization;

import javafx.scene.shape.*;

import java.util.Observable;

public class Connection extends Path implements Observer {

    CourseNode source;
    CourseNode destination;
    boolean connected;

    Connection(CourseNode source, CourseNode destination) {
        if (source == null || destination == null) {
            System.out.println("Illegal connection");
            return;
        }
        this.source = source;
        this.destination = destination;
        connected = true;
        draw();
    }

    public void draw() {
        MoveTo moveTo = new MoveTo();
        moveTo.setX(source.getLayoutX());
        moveTo.setY(source.getLayoutY());

        ArcTo arcTo = new ArcTo();
        arcTo.setX(destination.getLayoutX());
        arcTo.setY(destination.getLayoutY());
        arcTo.setRadiusX(Math.abs(source.getLayoutX() - destination.getLayoutX()));
        arcTo.setRadiusY(Math.abs(source.getLayoutY() - destination.getLayoutY()));
        getElements().addAll(moveTo, arcTo);
    }

    public void connect() {
        connected = true;
    }

    public void disconnect() {
        connected = false;
    }

    @Override
    public void update(Subject s) {
        draw();
    }
}
