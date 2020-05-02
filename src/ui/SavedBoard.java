package ui;

import javafx.scene.Node;
import model.SavedCourse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** todo the master serialized object
 * contains Stats, list of Courses containing position info
 * connections are drawn from looping over all the courses on load*/
public class SavedBoard implements Serializable {

    List<SavedCourse> savedCourseList;

    public SavedBoard(BoardManager manager) {
        savedCourseList = new ArrayList<>();

        List<Node> nodes = manager.mainBoard.getChildren();
        for (Node node : nodes) {
            if (node instanceof CourseNode) { // todo keep separate list of all coursenodes in board manager to prevent extra looping
                CourseNode cn = (CourseNode) node;
//                todo remove savedcourse obj from coursenodes, however retain separate courseID field
//                todo check if getLayoutX is the right coordinates
                SavedCourse savedCourse = new SavedCourse(cn.getCourseId(), cn.getDescription, cn.getCredits, cn.getPreReqs, cn.getCoreqs, cn.getLayoutX(), cn.getLayoutY());
                savedCourseList.add(savedCourse);
            }
        }
    }

    public List<SavedCourse> getSavedCourseList() {
        return savedCourseList;
    }

    /** todo fills boardmanager with new info, resetting the board to saved state*/
    public void populate(BoardManager boardManager) {
    }
}
