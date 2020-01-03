package visualization;

import javafx.scene.layout.Pane;

import java.util.*;

public class BoardManager {

    Set<Connection> connectionSet = new HashSet<>(); // todo this keeps track of the valid connections (fulfilled requisites) we have
    Map<CourseNode, List<String>> missingCourseIds = new HashMap<>(); // map of the course wish-list of each course

    Pane pane;

    BoardManager(Pane pane) {
        this.pane = pane;
    }

//   updates set based on removal or addition of a course
//    post:
//      missingCourseIds: added new key with corresponding list
//      connectionSet: contains all new connections from and to newNode
//      pane: holds and displays newNode and its connections
    public void addCourseUpdate(CourseNode newNode) {
//      find and remove the missing courses that are no longer missing because addition of newNode
//            also adds new connection representing the dependency on newNode
        removeFulfilledAddConnections(newNode);
        addNewNodeToMap(newNode);
//        pane.getChildren().add(newNode); //TODO ADDED HERE
    }

    private void addNewNodeToMap(CourseNode newNode) {
        //        todo add same thing for coreqs
//        makes new list of courses required by newNode, then add newNode and it's list to the missing courses list
        List<String> newPrereqs = new LinkedList<>(newNode.getCourse().getPrereqs());
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

    private void removeFulfilledAddConnections(CourseNode newNode) {
        for (Map.Entry element : missingCourseIds.entrySet()) {
            List<String> m = (List<String>) element.getValue();
            if (m.contains(newNode.getCourse().getId())) {
                Connection newConnection = new Connection((CourseNode) element.getKey(), newNode);
                connectionSet.add(newConnection);
                m.remove(newNode.getCourse().getId());
                pane.getChildren().add(newConnection);
            }
        }
    }

    //    TODO here maps missing courses onto course nodes: tells them what to display
//      also maps on connections as well and draws those
    public void updateNodeRequisiteCourses() {
    }

}