package visualization;

import java.util.*;

public class BoardManager {

    Set<Connection> connectionSet = new HashSet<>(); // todo this keeps track of the valid connections (fulfilled requisites) we have
    Map<CourseNode, List<String>> missingCourseIds = new HashMap<>(); // map of the course wish-list of each course

    Board board;

    BoardManager(Board board) {
        this.board = board;
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
        board.getChildren().add(newNode); //TODO ADDED HERE
    }

    private void addNewNodeToMap(CourseNode newNode) {
        //        todo same thing for coreqs
//        makes new list of courses required by newNode, then add newNode and it's list to the missing courses list
        List<String> missingPrereqs = new LinkedList<>(newNode.getCourse().getPrereqs());
        for (String req : newNode.getCourse().getPrereqs()) {
            for (Map.Entry element : missingCourseIds.entrySet()) {
                CourseNode existingNode = (CourseNode) element.getKey();

                if (req.equals(existingNode.getCourse().getId())) {
                    addNewConnection(newNode, existingNode);
//                    updateNodeRequisiteCourses(existingNode); //todo make list on existing course display only missing courses
                    missingPrereqs.remove(req);
                    break;
                }
            }
        }
        missingCourseIds.put(newNode, missingPrereqs); // add new course to missing course map
    }

    private void addNewConnection(CourseNode newNode, CourseNode existingNode) {
        Connection newConnection = new Connection(newNode, existingNode);
        connectionSet.add(newConnection);
        board.getChildren().add(newConnection);
    }

    public void editCourseUpdate(CourseNode editedNode) {
//        todo
    }

    private void removeFulfilledAddConnections(CourseNode newNode) {
        for (Map.Entry element : missingCourseIds.entrySet()) {
            List<String> reqList = (List<String>) element.getValue();
            String id = newNode.getCourse().getId();
            if (reqList.contains(id)) {
                Connection newConnection = new Connection((CourseNode) element.getKey(), newNode);
                connectionSet.add(newConnection);
                board.getChildren().add(newConnection);
                reqList.remove(id);
                ((CourseNode) element.getKey()).updateDisplay();
            }
        }
    }

    //    TODO here maps missing courses onto course nodes: tells them what to display
//      also maps on connections as well and draws those
    public void updateNodeRequisiteCourses(CourseNode existingNode) {
        existingNode.updateDisplay();
    }

}