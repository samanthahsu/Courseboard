package visualization;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.HashMap;
import java.util.Map;

//todo
//observes board manager and updates information based on whats in the board
//should be more than an interface to more easily update data
public class FacultyListView extends ListView<FacultyListCell> {

    Map<String, Integer> facultyMap = new HashMap<>();

    FacultyListView() {
        setMaxWidth(200);

//        get deleted after first update
        getItems().add(new FacultyListCell("test1", 1));
        getItems().add(new FacultyListCell("test2", 4));
        getItems().add(new FacultyListCell("test3", 56));
    }

    /**
     * handles all operation cases, add remove
     * ASSUMES: faculty is represented by the first 4 characters of a course ID
     **/
    public void update(CourseNode courseNode, Operation operation) {
        String courseFaculty = courseNode.getCourse().getId().substring(0, 4);
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

            getItems().add(new FacultyListCell(key, value));

        }
    }

    public void clearAll() {
        facultyMap.clear();
        getItems().clear();
    }
}
