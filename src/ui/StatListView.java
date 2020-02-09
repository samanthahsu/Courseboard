package ui;

import javafx.scene.control.ListView;
import model.Stats;

import java.util.HashMap;
import java.util.Map;

//todo
//observes board manager and updates information based on whats in the board
//should be more than an interface to more easily update data
public class StatListView extends ListView<StatListCell> {

    Map<String, Integer> facultyMap = new HashMap<>();

    StatListView(Stats stats) {
        setMaxWidth(200);
    }

    /**
     * handles all operation cases, add remove
     * ASSUMES: faculty is represented by the first 4 characters of a course ID
     **/
    public void update(CourseNode courseNode, Operation operation) {
        String courseFaculty = courseNode.getCourse().getCode().substring(0, 4);
        int courseCredits = courseNode.getCourse().getCredits();

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
        }
        UpdateDisplay();
    }

    //    updates ui based on faculties
    private void UpdateDisplay() {
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
