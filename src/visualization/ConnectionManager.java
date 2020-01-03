package visualization;

import javafx.scene.layout.Pane;
import model.Course;

import java.util.*;

public class ConnectionManager {

    Set<Connection> connectionSet = new HashSet<>(); // todo this keeps track of the valid reqs we have
    Map<CourseNode, List<String>> missingCourseIds = new HashMap<>(); // map of the course wishlist of each course
//    todo list of course string, which is obj that extends string and has whether
    Set<CourseNode> allCrsNodes = new HashSet<>(); // todo not necc (already contained inside wishlist)

    Pane pane;

    ConnectionManager(Pane pane) {
        this.pane = pane;
    }

    public void drawAll() {
//        todo draws all the connections
        pane.getChildren().addAll(connectionSet);
    }

    public void removeCourseUpdate() {
    }

//   updates set based on removal or addition of a course
//    todo add called area
    public void addCourseUpdate(CourseNode newNode) {

//      find and remove the missing courses that are no longer missing because addition of newNode
//            also adds new connection representing the dependency on newNode
        for (Map.Entry element : missingCourseIds.entrySet()) {
            List<String> m = (List<String>) element.getValue();
            if (m.contains(newNode.getCourse().getId())) {
                Connection e = new Connection((CourseNode) element.getKey(), newNode);
                connectionSet.add(e);
                m.remove(newNode.getCourse().getId());
                pane.getChildren().add(e);
            }
        }

//        todo add same thing for coreqs
//        makes new list of courses required by newNode, then add newNode and it's list to the missing courses list
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
        missingCourseIds.put(newNode, newPrereqs); // add new course to missing course map
    }

//    TODO here maps missing courses onto course nodes: tells them what to display
//      also maps on connections as well and draws those
    public void updateNodeRequisiteCourses() {
        for (Map.Entry element : missingCourseIds.entrySet()) {
            CourseNode key = (CourseNode) element.getKey();
            String miss = (String) element.getValue();
            key.updateMissingCourses(miss);
        }
    }

    public void addEmptyCourse() {
        CourseNode newNode = new CourseNode(new Course(""));
        addCourseUpdate(newNode);
        allCrsNodes.add(newNode);
        pane.getChildren().add(newNode);
    }

    public void addCourse(Course course) {
        CourseNode newNode = new CourseNode(course);
        addCourseUpdate(newNode);
        allCrsNodes.add(newNode);
        pane.getChildren().add(newNode);
    }
}
