package ui;

import javafx.scene.Node;
import model.SavedCourse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** the master serialized object
 * contains Stats, list of Courses containing position info
 * connections are drawn from looping over all the courses on load*/
public class SavedBoard implements Serializable {

//    all the courses and their locations
    List<SavedCourse> savedCourseList;

//    all the statistics on the all courses
    HashMap<String, Integer> subjectMap;

    public SavedBoard(BoardManager manager) {
        savedCourseList = new ArrayList<>();

//        save all the courses
        List<Node> nodes = manager.mainBoard.getChildren();
        for (Node node : nodes) {
            if (node instanceof CourseNode) { // todo keep separate list of all courseNodes in board manager to prevent extra looping
                CourseNode cn = (CourseNode) node;
                SavedCourse sc = cn.getSavedCourse();
                sc.setPosition(cn.getTranslateX(), cn.getTranslateY());
//                all prereq and coreq courses should already be saved
                savedCourseList.add(sc);
            }
        }

        subjectMap = manager.getSubjectMap();
    }

    /** fills boardManager with new info, resetting the board to the saved state
     * updates both lists in manager
     * notifies manager of the change -> manager handles ui differences*/
    public void populate(BoardManager manager) {
        manager.setSubjectMap(subjectMap);

//        clears all, then populates with connections as well
        manager.clearAll();
        for (SavedCourse sc : savedCourseList) {
            manager.addCourseUpdate(new CourseNode(manager, sc));
        }
    }
}
