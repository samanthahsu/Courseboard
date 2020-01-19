package visualization;

import javafx.scene.input.DataFormat;
import javafx.scene.input.MouseEvent;

import java.util.*;

public class BoardManager  {

//  todo make custom global variable for drag board
//      need separate the drag stuff from board component
    static final DataFormat COURSE_INFO = new DataFormat("Course");

    Set<Connection> connectionSet = new HashSet<>(); // todo this keeps track of the valid connections (fulfilled requisites) we have
    Map<CourseNode, List<String>> missingCourseIds = new HashMap<>(); // map of the course wish-list of each course

    BoardPane boardPane;
    CourseNode draggedCourseNode;
    FacultyListView facultyListView; // call update on this as needed

    BoardManager(BoardPane boardPane, FacultyListView facList) {
        this.boardPane = boardPane;
        this.facultyListView = facList;
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
        boardPane.getChildren().add(newNode);
        facultyListView.update(newNode, Operation.ADD);
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
        boardPane.getChildren().add(newConnection);
    }


    private void removeFulfilledAddConnections(CourseNode newNode) {
        for (Map.Entry element : missingCourseIds.entrySet()) {
            List<String> reqList = (List<String>) element.getValue();
            String id = newNode.getCourse().getId();
            if (reqList.contains(id)) {
                Connection newConnection = new Connection((CourseNode) element.getKey(), newNode);
                connectionSet.add(newConnection);
                boardPane.getChildren().add(newConnection);
                reqList.remove(id);
                ((CourseNode) element.getKey()).updateDisplay();
            }
        }
    }


    public void removeCourseUpdate(CourseNode deletedNode) {
        removeBrokenConnectionsAndAddMissing(deletedNode);
        missingCourseIds.remove(deletedNode);
        boardPane.getChildren().remove(deletedNode);
    }

//          get all connections on @board which point to @deletedNode
//          for each connection update the other corresponding node's missing courses list
//          get all connections pointed to by the deleted node and kill them dead
//          delete all the connections
    private void removeBrokenConnectionsAndAddMissing(CourseNode deletedNode) {
        Set<Connection> newConnectionSet = new HashSet<>(connectionSet);
        for (Connection connection : connectionSet) {
            if (connection.destination == deletedNode) {
//                modify the source
                CourseNode existingNode = connection.getSource();
                List<String> list = missingCourseIds.get(existingNode);
                list.add(deletedNode.getCourseId());

                newConnectionSet.remove(connection);
                boardPane.getChildren().remove(connection);
            } else if (connection.source == deletedNode) {
//                kill connection
                newConnectionSet.remove(connection);
                boardPane.getChildren().remove(connection);
            }
        }
        connectionSet = newConnectionSet;
    }


/**
     todo OK we restrict changing the course code because thats unnecc work
      remember to eliminate option from edit window
      only consider requisites and notes and credits changing
    update on faculty is called as removal we need to know what the original node info was before it was changed
    update faculty essentially like removing than adding a completely new node
*/
    public void editCourseUpdate(CourseNode editedNode) {
        facultyListView.update(editedNode, Operation.REMOVE);
//        todo
        facultyListView.update(editedNode, Operation.ADD);
    }


    public void clearAll() {
//        everything dies
//        todo alert before doing this
        connectionSet.clear();
        missingCourseIds.clear();
        boardPane.getChildren().clear();
    }

//    public void addTerm(String termName) {
////        todo
//        TermNode newTerm = new TermNode(termName, this);
//        boardPane.getChildren().add(newTerm);
//    }

//    public void removeTerm(TermNode deletedTerm) {
////        todo set all its children courses at the current position (get current then set after removal from container and dumping into pane)
//        boardPane.getChildren().remove(deletedTerm);
//    }

    public void onDragDetectedCourseNode(CourseNode courseNode, MouseEvent event) {
//        Dragboard db = courseNode.startDragAndDrop(TransferMode.COPY_OR_MOVE);
//        ClipboardContent cc = new ClipboardContent();
//        cc.putString("Something");
//        db.setContent(cc);
        draggedCourseNode = courseNode;
    }

//    public void onDragOverTerm(TermNode termNode, MouseDragEvent event) {
//        if (event.getGestureSource() != termNode && draggedCourseNode != null) {
//            termNode.selectionGlowOn();
//            System.out.println("hi");
//        }
//        event.consume();
//    }
//    public void dragDropped(TermNode termNode, MouseDragEvent event) {
//        boolean dragCompleted = false;
////        Dragboard dragboard = event.getDragboard();
//        if (draggedCourseNode != null) {
////            Course course = (Course) dragboard.getContent(COURSE_INFO);
//            termNode.addToGrid(draggedCourseNode);
//            dragCompleted = true;
//        }
////        event.setDropCompleted(dragCompleted);
//        event.consume();
//    }
//
//    public void dragDone(TermNode termNode, MouseDragEvent event) {
//
////        TransferMode tm = event.getTransferMode();
//
//        if (tm == TransferMode.MOVE) {
////            Course course = (Course) event.getDragboard().getContent(COURSE_INFO);
//            board.getChildren().remove(new CourseNode(course, this)); // todo make equals override
//        }
//        event.consume();
    }