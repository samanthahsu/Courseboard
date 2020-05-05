package ui;

import javafx.scene.control.ListView;
import model.SavedCourse;

import java.util.HashMap;
import java.util.Map;

/** observes board manager and updates information based on whats in the board
 * should be more than an interface to more easily update data
 * NOTE: assumes courses have at least 1 credit attributed to each one
 * changes to @subjectMap always updates the display*/
public class StatListView extends ListView<StatListCell> {

    //    contains course subject and total number of credits
    private HashMap<String, Integer> subjectMap;

    /**constructor for blank statList*/
    StatListView(HashMap<String, Integer> subjectMap) {
        setMinWidth(200);
        this.subjectMap = subjectMap;
    }

    public HashMap<String, Integer> getSubjectMap() {
        return subjectMap;
    }

    public void setSubjectMap(HashMap<String, Integer> subjectMap) {
        this.subjectMap = subjectMap;
        updateDisplay();
    }

    /**
     * handles all operation cases, add remove
     * ASSUMES: faculty is represented by the first 4 characters of a course ID
     **/
    public void update(CourseNode courseNode, Operation operation) {
        SavedCourse savedCourse = courseNode.getSavedCourse();
        String courseFaculty = savedCourse.getSubject();
        int courseCredits = courseNode.getSavedCourse().getCredits();

        if (operation == Operation.ADD) {
            if (!subjectMap.containsKey(courseFaculty)) {
                subjectMap.put(courseFaculty, courseCredits);
            } else {
                subjectMap.replace(courseFaculty, subjectMap.get(courseFaculty) + courseCredits);
            }
        } else if (operation == Operation.REMOVE) {
            Integer storedCredit = subjectMap.get(courseFaculty);
            assert (storedCredit != null);
            subjectMap.put(courseFaculty, storedCredit - courseCredits);

            assert (subjectMap.get(courseFaculty) >= 0);
            if (subjectMap.get(courseFaculty) == 0) subjectMap.remove(courseFaculty);
        }
        updateDisplay();
    }

    //    updates ui based on @param subjectMap
    private void updateDisplay() {
        getItems().clear();
        for (Map.Entry mapElement : subjectMap.entrySet()) {
            String subject = (String) mapElement.getKey();
            int sumCredits = (int) mapElement.getValue();

            getItems().add(new StatListCell(subject, sumCredits));
        }
    }

    public void clearAll() {
        subjectMap.clear();
        getItems().clear();
    }
}
