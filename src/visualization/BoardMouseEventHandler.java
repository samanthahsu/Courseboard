package visualization;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;

import java.util.*;

public class BoardMouseEventHandler {

    final DragOffset dragContext = new DragOffset(0,0);
    static final Color RECT_FILL = new Color(0.6784314f, 0.84705883f, 0.9019608f, 0.5f);

    Rectangle rect;

    Pane group;
    DragStatus dragStatus = DragStatus.START_ON_CANVAS;
    DragOffset deltaSingle;
    CourseNode anchorNode;
    boolean hasBeenDragged = false;
    List<DragOffset> dragOffsetList;
    SelectionModel selectionModel = new SelectionModel();

    public BoardMouseEventHandler(Pane group) {
        this.group = group;

        rect = new Rectangle( 0,0,0,0);
        rect.setStroke(Color.BLUE);
        rect.setStrokeWidth(1);
        rect.setStrokeLineCap(StrokeLineCap.ROUND);
        rect.setFill(RECT_FILL);

//        set all the handling
        group.addEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressedEventHandler);
        group.addEventHandler(MouseEvent.MOUSE_DRAGGED, onMouseDraggedEventHandler);
        group.addEventHandler(MouseEvent.MOUSE_RELEASED, onMouseReleasedEventHandler);
    }


    /**
     * affects first node it sees in the group thats at x y (likely oldest child) and saves drag info
     * if none are at event x y do a canvas selection drag*/
    EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
//            reset drag indicator
            hasBeenDragged = false;

            if (!event.isControlDown()) {
                for (Node node : group.getChildren()) {
                    if (node instanceof CourseNode &&
                            node.localToScene(node.getBoundsInLocal()).contains(event.getSceneX(), event.getSceneY())) {

                        group.getScene().setCursor(Cursor.CLOSED_HAND);

                        if (selectionModel.contains((CourseNode) node)) {
                            dragStatus = DragStatus.START_ON_SELECTED_NODES;
                            dragOffsetList = new ArrayList<>();
//                            calc and store offsets for drag
                            for (CourseNode selectedNode : selectionModel.selection) {
                                dragOffsetList.add(new DragOffset(selectedNode.getLayoutX() - event.getSceneX(), selectedNode.getLayoutY() - event.getSceneY()));
                            }

                        } else {
                            dragStatus = DragStatus.START_ON_SINGLE_NODE;
                            deltaSingle = new DragOffset(node.getLayoutX() - event.getSceneX(), node.getLayoutY() - event.getSceneY());
                        }
//                        store clicked on node
                        anchorNode = (CourseNode) node;
                        return;
                    }
                }
            }

            dragStatus = DragStatus.START_ON_CANVAS;
            dragContext.dx = event.getSceneX();
            dragContext.dy = event.getSceneY();

            rect.setX(dragContext.dx);
            rect.setY(dragContext.dy);
            rect.setWidth(0);
            rect.setHeight(0);

            group.getChildren().add( rect);

            event.consume();
        }
    };

    /**
     * clear selection by clicking on canvas*/
    EventHandler<MouseEvent> onMouseReleasedEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {

            group.getScene().setCursor(Cursor.DEFAULT);

            if (!hasBeenDragged && dragStatus == DragStatus.START_ON_CANVAS) {
                selectionModel.clear();
            }

            if (dragStatus == DragStatus.START_ON_CANVAS) {
                for (Node node : group.getChildren()) {
                    if (node instanceof CourseNode && node.getBoundsInParent().intersects(rect.getBoundsInParent())) {
                        selectionModel.add((CourseNode) node);
                    }
                }
            }
            selectionModel.log();
            group.getChildren().remove(rect);
            event.consume();
        }
    };

    EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            hasBeenDragged = true;

            if (dragStatus == DragStatus.START_ON_CANVAS) {

                double offsetX = event.getSceneX() - dragContext.dx;
                double offsetY = event.getSceneY() - dragContext.dy;

                if (offsetX > 0)
                    rect.setWidth(offsetX);
                else {
                    rect.setX(event.getSceneX());
                    rect.setWidth(dragContext.dx - rect.getX());
                }

                if (offsetY > 0) {
                    rect.setHeight(offsetY);
                } else {
                    rect.setY(event.getSceneY());
                    rect.setHeight(dragContext.dy - rect.getY());
                }

            } else if (dragStatus ==  DragStatus.START_ON_SELECTED_NODES) {
                List<CourseNode> courseNodeList = selectionModel.selection;
                for (int i = 0; i < courseNodeList.size(); i++) {
                    courseNodeList.get(i).setLayoutX(event.getSceneX() + dragOffsetList.get(i).dx);
                    courseNodeList.get(i).setLayoutY(event.getSceneY() + dragOffsetList.get(i).dy);
                }
            } else if (dragStatus ==  DragStatus.START_ON_SINGLE_NODE) {
                anchorNode.setLayoutX(event.getSceneX() + deltaSingle.dx);
                anchorNode.setLayoutY(event.getSceneY() + deltaSingle.dy);
            }
            event.consume();
        }
    };


    private final class DragOffset {
        public double dx;
        public double dy;

        public DragOffset(double x, double y) {
            dx = x;
            dy = y;
        }
    }

    private enum DragStatus {
        START_ON_CANVAS, START_ON_SINGLE_NODE, START_ON_SELECTED_NODES, NO_DRAG;
    }

    private static class SelectionModel {
//        todo make private
        List<CourseNode> selection = new ArrayList<>(); // is list to keep delta list matching

        final DropShadow borderGlow = new DropShadow(20, Color.BLUE);


        public void add( CourseNode node) {
            node.setEffect(borderGlow);
            selection.add(node);
        }

        public void remove( CourseNode node) {
            node.setEffect(null);
            selection.remove(node);
        }

        public void clear() {
            while( !selection.isEmpty()) {
                remove( selection.iterator().next());
            }
        }

        public boolean contains( CourseNode node) {
            return selection.contains(node);
        }

        public void log() {
            System.out.println( "Items in model: " + Arrays.asList( selection.toArray()));
        }
    }

}