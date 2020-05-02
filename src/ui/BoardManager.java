package ui;

import javafx.scene.input.MouseEvent;

import java.util.*;
import java.util.Map.Entry;

public class BoardManager  {

    Set<Connection> connectionSet = new HashSet<>(); // this keeps track of the valid connections (fulfilled requisites) we have
    Map<CourseNode, List<String>> missingCourseIds = new HashMap<>(); // map of the course wish-list of each course

    Board board; // the pane everything is being attached to
    CourseNode draggedCourseNode;
    StatListView statListView; // call update on this as needed
    BoardNodeGestures nodeGestures;

    BoardManager(Board pane, StatListView statListView, BoardNodeGestures nodeGestures) {
        this.board = pane;
        this.statListView = statListView;
        this.nodeGestures = nodeGestures;
    }

/**
   update set on removal or addition of a course
    post:
      missingCourseIds: added new key with corresponding list
      connectionSet: contains all new connections from and to newNode
      board: holds and displays newNode and its connections
      newNode: given proper event filters for dragging
*/
    public void addCourseUpdate(CourseNode newNode) {
//      find and remove the missing courses that are no longer missing because addition of newNode
//            also adds new connection representing the dependency on newNode
//        make layout of newNode not null
        board.getChildren().add(newNode);
        removeFulfilledAddConnections(newNode);
        addNewNodeToMap(newNode);
        newNode.addEventFilter( MouseEvent.MOUSE_PRESSED, nodeGestures.getOnMousePressedEventHandler());
        newNode.addEventFilter( MouseEvent.MOUSE_DRAGGED, nodeGestures.getOnMouseDraggedEventHandler());

        statListView.update(newNode, Operation.ADD); //todo
    }

    private void addNewNodeToMap(CourseNode newNode) {
        //        todo same thing for coreqs
//        makes new list of courses required by newNode, then add newNode and it's list to the missing courses list
        List<String> missingPrereqs = new LinkedList<>(newNode.getCourse().getPrereqs()); // will hold missing prereqs of new node after loop

        for (String reqString : newNode.getCourse().getPrereqs()) {
            for (Entry element : missingCourseIds.entrySet()) {
                CourseNode existingNode = (CourseNode) element.getKey(); // gives access to each node on the board

                if (reqString.equals(existingNode.getCourse().getCourseIDAsString())) {
                    addConnection(newNode, existingNode);
//                    updateNodeRequisiteCourses(existingNode); //todo make list on existing course display only missing courses
                    missingPrereqs.remove(reqString);
                    break;
                }
            }
        }
        missingCourseIds.put(newNode, missingPrereqs); // add new course to missing course map
    }

//    todo: connection being added, but not visible on display.
    private void addConnection(CourseNode newNode, CourseNode existingNode) {
        Connection newConnection = new Connection(newNode, existingNode);
        connectionSet.add(newConnection);
        board.getChildren().add(newConnection);
        System.out.println("New connection added!");
    }


    private void removeFulfilledAddConnections(CourseNode newNode) {
        for (Entry element : missingCourseIds.entrySet()) {
            List<String> reqList = (List<String>) element.getValue();
            String id = newNode.getCourse().getcID().toString();
            if (reqList.contains(id)) {
                Connection newConnection = new Connection((CourseNode) element.getKey(), newNode);
                connectionSet.add(newConnection);
                board.getChildren().add(newConnection);
                reqList.remove(id);
                ((CourseNode) element.getKey()).updateDisplay();
            }
        }
    }


    /** handles removal of a single node
     * called from node context menu */
    public void removeCourseUpdate(CourseNode deletedNode) {

        removeBrokenConnectionsAndAddMissing(deletedNode);
        missingCourseIds.remove(deletedNode);
        board.getChildren().remove(deletedNode);

//        statListView.removeRecord(deletedNode.getCourse().getcID());
        statListView.update(deletedNode, Operation.REMOVE);
    }

/**
          get all connections on @board which point to @deletedNode
          for each connection update the other corresponding node's missing courses list
          get all connections pointed to by the deleted node and kill them dead
          delete all the connections
*/
    private void removeBrokenConnectionsAndAddMissing(CourseNode deletedNode) {
        Set<Connection> newConnectionSet = new HashSet<>(connectionSet);
        for (Connection connection : connectionSet) {
            if (connection.destination == deletedNode) {
//                modify the source
                CourseNode existingNode = connection.getSource();
                List<String> list = missingCourseIds.get(existingNode);
                list.add(deletedNode.getCourseId());

                newConnectionSet.remove(connection);
                board.getChildren().remove(connection);
            } else if (connection.source == deletedNode) {
//                kill connection
                newConnectionSet.remove(connection);
                board.getChildren().remove(connection);
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
//        stats.removeRecord(editedNode.getCourse().getcID());
//        todo
//        stats.addNewRecord();
    }

//        everything in board dies
    public void clearAll() {
//        todo alert before doing this
        statListView.clearAll();
        connectionSet.clear();
        missingCourseIds.clear();
        board.getChildren().clear();
    }

    public void onDragDetectedCourseNode(CourseNode courseNode, MouseEvent event) {
//        Dragboard db = courseNode.startDragAndDrop(TransferMode.COPY_OR_MOVE);
//        ClipboardContent cc = new ClipboardContent();
//        cc.putString("Something");
//        db.setContent(cc);
        draggedCourseNode = courseNode;
    }
 }