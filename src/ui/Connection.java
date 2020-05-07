package ui;

import javafx.scene.layout.Region;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;

/** class representing lines connecting associated course nodes
 * IMPORTANT: source=requirer, destination=required*/
public class Connection extends Region implements Observer {
    /*source: node that needs this
    * destination: node that is needed (requisite speaking)*/
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

    public CourseNode getSource() {
        return source;
    }

    public CourseNode getDestination() {
        return destination;
    }

    /** draws an arc from source to destination
     * todo make it also draw arrowhead and in different colours, symbolizing different statuses*/
    public void draw() {
        if (path != null) {
            getChildren().remove(path);
        }


        path = new Path();
        moveTo = new MoveTo();
        moveTo.setX(source.getTranslateX());
        moveTo.setY(source.getTranslateY());

        arcTo = new ArcTo();
        arcTo.setX(destination.getTranslateX());
        arcTo.setY(destination.getTranslateY());
        arcTo.setRadiusX(Math.abs(source.getTranslateX() - destination.getTranslateX()));
        arcTo.setRadiusY(Math.abs(source.getTranslateY() - destination.getTranslateY()));
        path.getElements().addAll(moveTo, arcTo);
        path.setMouseTransparent(true);
        getChildren().add(path);

//        Rectangle end = new Rectangle(10, 10);
//        end.setX(destination.getTranslateX());
//        end.setY(destination.getTranslateY());
//        getChildren().add(end);
    }

    public void connect() {
        connected = true;
    }

    public void disconnect() {
        connected = false;
    }

    /** redraws path if isDraw true, otherwise removes path*/
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
