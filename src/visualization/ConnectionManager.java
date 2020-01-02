package visualization;

import javafx.scene.layout.Pane;
import model.Course;

import java.util.*;

public class ConnectionManager {

    Set<Connection> connectionSet = new HashSet<>();
    Map<CourseNode, String> missing = new HashMap<>();
    Set<CourseNode> allCrsNodes = new HashSet<>();

    Pane pane;

    ConnectionManager(Pane pane) {
        this.pane = pane;
    }

    public void drawAll() {
//        todo draws all the connections
        pane.getChildren().addAll(connectionSet);
    }

    public void removeUpdate() {

    }
//   updates set based on removal or addition of a course
//    todo add called area
    public void addUpdate(CourseNode newNode) {

        for (Map.Entry element : missing.entrySet()) {
            String m = (String) element.getValue();
            if (m.equals(newNode.getCourse().getId())) {
                Connection e = new Connection((CourseNode) element.getKey(), newNode);
                connectionSet.add(e);
                missing.remove(element.getKey());
                pane.getChildren().add(e);
            }
        }

//        todo add same thing for coreqs later
        List<String> newPrereqs = newNode.getCourse().getPrereq();
        for (String req : newPrereqs) {
            for (CourseNode existingNode : allCrsNodes) {
                if (req.equals(existingNode.getCourse().getId())) {
                    System.out.println(req);
                    Connection e = new Connection(newNode, existingNode);
                    connectionSet.add(e);
                    pane.getChildren().add(e);
                    newPrereqs.remove(req);
                }
            }
        }

        for (String p : newPrereqs) {
            missing.put(newNode, p);
        }

    }

    public void displayMissing() {
        for (Map.Entry element : missing.entrySet()) {
            CourseNode key = (CourseNode) element.getKey();
            String miss = (String) element.getValue();
            key.displayMissing(miss);
        }

    }

    public void addEmptyCourse() {
        CourseNode newNode = new CourseNode(new Course(""));
        addUpdate(newNode);
        allCrsNodes.add(newNode);
        pane.getChildren().add(newNode);
    }

    public void addCourse(Course course) {
        CourseNode newNode = new CourseNode(course);
        addUpdate(newNode);
        allCrsNodes.add(newNode);
        pane.getChildren().add(newNode);
    }
}
