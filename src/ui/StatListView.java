package ui;

import javafx.scene.control.ListView;
import model.SavedCourse;

import java.util.HashMap;
import java.util.Map;

//todo: keep data floating in StatListView until serialization where it is deposited into Stats object
/** observes board manager and updates information based on whats in the board
 * should be more than an interface to more easily update data
 * NOTE: assumes courses have at least 1 credit attributed to each one*/
public class StatListView extends ListView<StatListCell> {

//    contains course subject and total number of credits
    Map<String, Integer> facultyMap = new HashMap<>();

    /**constructor for blank statlist*/
    StatListView() {
        setMaxWidth(200);
    }

    /**
     * handles all operation cases, add remove
     * ASSUMES: faculty is represented by the first 4 characters of a course ID
     * todo: remove subject completely if credits become 0
     **/
    public void update(CourseNode courseNode, Operation operation) {
        SavedCourse savedCourse = courseNode.getSavedCourse();
        String courseFaculty = savedCourse.getSubject();
        int courseCredits = courseNode.getSavedCourse().getCredits();

        if (operation == Operation.ADD) {
            if (!facultyMap.containsKey(courseFaculty)) {
                facultyMap.put(courseFaculty, courseCredits);
            } else {
                facultyMap.replace(courseFaculty, facultyMap.get(courseFaculty) + courseCredits);
            }
        } else if (operation == Operation.REMOVE) {
            Integer storedCredit = facultyMap.get(courseFaculty);
            assert (storedCredit != null);
            facultyMap.put(courseFaculty, storedCredit - courseCredits);

            assert (facultyMap.get(courseFaculty) >= 0);
            if (facultyMap.get(courseFaculty) == 0) facultyMap.remove(courseFaculty);
        }
        updateDisplay();
    }

    //    updates ui based on faculties
    private void updateDisplay() {
        getItems().clear();
        for (Map.Entry mapElement : facultyMap.entrySet()) {
            String key = (String) mapElement.getKey();
            int value = (int) mapElement.getValue();

            getItems().add(new StatListCell(key, value));
        }
    }

    public void clearAll() {
        facultyMap.clear();
        getItems().clear();
    }
}
