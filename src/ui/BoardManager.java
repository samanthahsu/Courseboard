package ui;

import javafx.scene.input.MouseEvent;

import java.util.*;
import java.util.Map.Entry;

/** manages all board operations display-wise*/
public class BoardManager  {

    Set<Connection> boardConnectionSet = new HashSet<>(); // this keeps track of the valid connections (fulfilled requisites)
    Map<CourseNode, List<String>> missingCourseIds = new HashMap<>(); // map of the course wish-list of each course

    Board mainBoard; // the pane everything is being attached to
    CourseNode selectedCourseNode;
    StatListView statListView; // call update on this as needed
    BoardNodeGestures nodeGestures;

    /**constructor*/
    BoardManager(Board board, StatListView statListView, BoardNodeGestures nodeGestures) {
        this.mainBoard = board;
        this.statListView = statListView;
        this.nodeGestures = nodeGestures;
    }

/**
   update connection set on removal or addition of a course
    post:
      missingCourseIds: added new key with corresponding list
      connectionSet: contains all new connections from and to newNode
      board: holds and displays newNode and its connections
      newNode: given proper event filters for dragging
        and places node according to coords inside savedCourse
*/
    public void addCourseUpdate(CourseNode newNode) {
//      find and remove the missing courses that are no longer missing because addition of newNode
//            also adds new connection representing the dependency on newNode
//        make layout of newNode not null
        mainBoard.getChildren().add(newNode);
        newNode.setTranslateX(newNode.getSavedCourse().getPosX());
        newNode.setTranslateY(newNode.getSavedCourse().getPosY());
        removeFulfilledAddConnections(newNode);
        addNewNodeToMap(newNode);
        newNode.updateDisplay();
        newNode.addEventFilter(MouseEvent.MOUSE_PRESSED, nodeGestures.getOnMousePressedEventHandler());
        newNode.addEventFilter(MouseEvent.MOUSE_DRAGGED, nodeGestures.getOnMouseDraggedEventHandler());

        statListView.update(newNode, Operation.ADD); //todo
    }

    private void addNewNodeToMap(CourseNode newNode) {
        //        todo same thing for coReqs
//        makes new list of courses required by newNode, then add newNode and it's list to the missing courses list
        List<String> missingPrereqs = new LinkedList<>(newNode.getSavedCourse().getPrereqs()); // will hold missing prereqs of new node after loop

        for (String reqString : newNode.getSavedCourse().getPrereqs()) {
            for (Entry element : missingCourseIds.entrySet()) {
                CourseNode existingNode = (CourseNode) element.getKey(); // gives access to each node on the board

                if (reqString.equals(existingNode.getSavedCourse().getCourseIDAsString())) {
                    addConnection(newNode, existingNode);
//                    updateNodeRequisiteCourses(existingNode); //todo make list on existing course display only missing courses
                    missingPrereqs.remove(reqString);
                    break;
                }
            }
        }
        missingCourseIds.put(newNode, missingPrereqs); // add new course to missing course map
    }

/**
 * adds a connection from sourceNode to destNode
 * updates @boardConnectionSet
 * @param sourceNode  and @destNode's connection sets
 * adds connection to board children
*/
    private void addConnection(CourseNode sourceNode, CourseNode destNode) {
        Connection newConnection = new Connection(sourceNode, destNode);

        boardConnectionSet.add(newConnection);
        sourceNode.addConnection(newConnection);
        destNode.addConnection(newConnection);

        mainBoard.getChildren().add(newConnection);
        System.out.println("New connection added!");
    }


    /**
     * MODIFIES: missingCourseIds, newNode
     * POST: given the @newNode added to the board
     *  related connections are added
     *  fulfilled requirements are removed*/
    private void removeFulfilledAddConnections(CourseNode newNode) {
        for (Entry element : missingCourseIds.entrySet()) {
            List<String> reqList = (List<String>) element.getValue();
            String id = newNode.getSavedCourse().getcID().toString();
            if (reqList.contains(id)) {
                CourseNode courseNode = (CourseNode) element.getKey();
                addConnection(courseNode, newNode);
                System.out.println("added connection for new added node");
//                Connection newConnection = new Connection((CourseNode) element.getKey(), newNode);
//                boardConnectionSet.add(newConnection);
//                mainBoard.getChildren().add(newConnection);
                reqList.remove(id);
                (courseNode).updateDisplay();
            }
        }
    }


    /** handles removal of a single node
     * called from node context menu
     * should update all connected node's prerequisite list.*/
    public void removeCourseUpdate(CourseNode deletedNode) {

        removeBrokenConnectionsAndAddMissing(deletedNode);
        missingCourseIds.remove(deletedNode);
        mainBoard.getChildren().remove(deletedNode);

//        statListView.removeRecord(deletedNode.getCourse().getcID());
        statListView.update(deletedNode, Operation.REMOVE);
    }

/**
          get all connections on @board which point to @deletedNode
          for each connection update the other corresponding node's
            missing-courses-list to include the now missing course @deletedNode
            and connection-set to remove connections
          all connections pointed to by the deleted node and kill them dead
          delete all the connections
*/
    private void removeBrokenConnectionsAndAddMissing(CourseNode deletedNode) {
        Set<Connection> newConnectionSet = new HashSet<>(boardConnectionSet);
        for (Connection c : boardConnectionSet) {
            if (c.destination == deletedNode) {
//                modify the source
                CourseNode existingNode = c.getSource();
                List<String> list = missingCourseIds.get(existingNode);
                list.add(deletedNode.getCourseId());
                existingNode.updateDisplay();
                existingNode.removeConnection(c);

                newConnectionSet.remove(c);
                mainBoard.getChildren().remove(c);
            } else if (c.source == deletedNode) {
//                kill connection
                newConnectionSet.remove(c);
                mainBoard.getChildren().remove(c);
            }
        }
        boardConnectionSet = newConnectionSet;
    }


/**
     todo OK we restrict changing the course code because that's unnecc work
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
        boardConnectionSet.clear();
        missingCourseIds.clear();
        mainBoard.getChildren().clear();
    }

    public void onDragDetectedCourseNode(CourseNode courseNode, MouseEvent event) {
//        Dragboard db = courseNode.startDragAndDrop(TransferMode.COPY_OR_MOVE);
//        ClipboardContent cc = new ClipboardContent();
//        cc.putString("Something");
//        db.setContent(cc);
        selectedCourseNode = courseNode;
    }

    public HashMap<String, Integer> getSubjectMap() {
        return statListView.getSubjectMap();
    }

    public void setSubjectMap(HashMap<String, Integer> subjectMap) {
        statListView.setSubjectMap(subjectMap);
    }
}