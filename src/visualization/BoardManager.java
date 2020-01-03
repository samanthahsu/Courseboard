package visualization;

import javafx.scene.layout.Pane;
import model.Course;

import java.util.*;

public class BoardManager {

    Set<Connection> connectionSet = new HashSet<>(); // todo this keeps track of the valid connections (fulfilled requisites) we have
    Map<CourseNode, List<String>> missingCourseIds = new HashMap<>(); // map of the course wishlist of each course

    Pane pane;

    BoardManager(Pane pane) {
        this.pane = pane;
    }

    public void drawAll() {
//        todo draws all the connections
        pane.getChildren().addAll(connectionSet);
    }

    public void removeCourseUpdate() {
    }

//   updates set based on removal or addition of a course
    public void addCourseUpdate(CourseNode newNode) {

//      find and remove the missing courses that are no longer missing because addition of newNode
//            also adds new connection representing the dependency on newNode
        removeFulfilled(newNode);

//        todo add same thing for coreqs
//        makes new list of courses required by newNode, then add newNode and it's list to the missing courses list
        List<String> newPrereqs = newNode.getCourse().getPrereqs();
        for (String req : newPrereqs) {
            for (Map.Entry element : missingCourseIds.entrySet()) {
                CourseNode existingNode = (CourseNode) element.getKey();
                if (req.equals(existingNode.getCourse().getId())) {
                    Connection e = new Connection(newNode, existingNode);
                    connectionSet.add(e);
                    pane.getChildren().add(e);

                    newPrereqs.remove(req);
                }
            }
        }
        missingCourseIds.put(newNode, newPrereqs); // add new course to missing course map
    }

    private void removeFulfilled(CourseNode newNode) {
        for (Map.Entry element : missingCourseIds.entrySet()) {
            List<String> m = (List<String>) element.getValue();
            if (m.contains(newNode.getCourse().getId())) {
                Connection e = new Connection((CourseNode) element.getKey(), newNode);
                connectionSet.add(e);
                m.remove(newNode.getCourse().getId());
                pane.getChildren().add(e);
            }
        }
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
        CourseNode newNode = new CourseNode(new Course(""), this);
        addCourseUpdate(newNode);
        pane.getChildren().add(newNode);
    }

    public void addCourse(Course course) {
        CourseNode newNode = new CourseNode(course, this);
        addCourseUpdate(newNode);
        pane.getChildren().add(newNode);
    }
}
