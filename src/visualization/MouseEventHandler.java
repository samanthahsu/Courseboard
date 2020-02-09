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

/**Does all the click handling for the cork board
 * todo: move into the pane instead?*/
public class MouseEventHandler {

    final DragOffset dragContext = new DragOffset(0,0);
    static final Color RECT_FILL = new Color(0.6784314f, 0.84705883f, 0.9019608f, 0.5f);

    Rectangle selectionBox;

//    the obj this is operating on
    Pane pane;

//    info needed for dragging
    DragStatus dragStatus = DragStatus.START_ON_CANVAS;
    DragOffset deltaSingle;
    CourseNode anchorNode;
    boolean hasBeenDragged = false;
    List<DragOffset> dragOffsetList;
    SelectionModel selectionModel = new SelectionModel();

    public MouseEventHandler(Pane pane) {
        this.pane = pane;

//        init selection box
        selectionBox = new Rectangle( 0,0,0,0);
        selectionBox.setStroke(Color.BLUE);
        selectionBox.setStrokeWidth(1);
        selectionBox.setStrokeLineCap(StrokeLineCap.ROUND);
        selectionBox.setFill(RECT_FILL);

//        set all the handling
        pane.addEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressedEventHandler);
        pane.addEventHandler(MouseEvent.MOUSE_DRAGGED, onMouseDraggedEventHandler);
        pane.addEventHandler(MouseEvent.MOUSE_RELEASED, onMouseReleasedEventHandler);
    }


    /**
     * affects first node it sees in the group thats at x y (oldest child) and saves drag info
     * if none are at event x y do a canvas selection drag*/
    EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
//            reset drag indicator
            hasBeenDragged = false;

            if (!event.isControlDown()) {
                for (Node node : pane.getChildren()) {
                    if (node instanceof CourseNode &&
                            node.localToScene(node.getBoundsInLocal()).contains(event.getSceneX(), event.getSceneY())) {

                        pane.getScene().setCursor(Cursor.CLOSED_HAND);

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

            selectionBox.setX(dragContext.dx);
            selectionBox.setY(dragContext.dy);
            selectionBox.setWidth(0);
            selectionBox.setHeight(0);

            pane.getChildren().add(selectionBox);

            event.consume();
        }
    };

    /**
     * clear selection by clicking on canvas*/
    EventHandler<MouseEvent> onMouseReleasedEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {

            pane.getScene().setCursor(Cursor.DEFAULT);

            if (!hasBeenDragged && dragStatus == DragStatus.START_ON_CANVAS) {
                selectionModel.clear();
            }

            if (dragStatus == DragStatus.START_ON_CANVAS) {
                for (Node node : pane.getChildren()) {
                    if (node instanceof CourseNode && node.getBoundsInParent().intersects(selectionBox.getBoundsInParent())) {
                        selectionModel.add((CourseNode) node);
                    }
                }
            }
            selectionModel.log();
            pane.getChildren().remove(selectionBox);
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
                    selectionBox.setWidth(offsetX);
                else {
                    selectionBox.setX(event.getSceneX());
                    selectionBox.setWidth(dragContext.dx - selectionBox.getX());
                }

                if (offsetY > 0) {
                    selectionBox.setHeight(offsetY);
                } else {
                    selectionBox.setY(event.getSceneY());
                    selectionBox.setHeight(dragContext.dy - selectionBox.getY());
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
        final DropShadow borderGlow = new DropShadow(10, Color.DARKBLUE);


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