package ui;

import javafx.scene.Node;
import model.Course;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/** is the only serialized object*/
public class SavedBoard implements Serializable {

    HashMap<Course, Position> courseList;

    public SavedBoard(BoardManager manager) {
        courseList = new HashMap<>();

        List<Node> nodes = manager.board.getChildren();
        for (Node node : nodes) {
            if (node instanceof CourseNode) {
                CourseNode courseNode = (CourseNode) node;
                Course course = courseNode.getCourse();
                Position thisPos = new Position(courseNode.getLayoutX(), courseNode.getLayoutY());
                courseList.put(course, thisPos);
            }
        }
    }

    /** fills boardmanager with new info, resetting the board to saved state*/
    public void populate(BoardManager boardManager) {
//        todo
    }

    private static class Position implements Serializable {
        double sceneX;
        double sceneY;

        public Position(double x, double y) {
            this.sceneX = x;
            this.sceneY = y;
        }
    }
}
