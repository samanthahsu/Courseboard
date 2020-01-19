package visualization;

import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;

//todo
//observes board manager and updates information based on whats in the board
//should be more than an interface to more easily update data
public class FacultyListView extends ListView<String> {

    Map<String, Integer> facultyMap = new HashMap<>();

// updates all info

    FacultyListView() {
        getChildren().add(new Text("\n Here you will see a list of all the faculties \n in your courses and their corresponding total credits."));
        setMaxWidth(200);
    }

    /**
     * handles all operation cases, add remove
     **/
    public void update(CourseNode courseNode, Operation operation) {
        String courseFaculty = courseNode.getCourseId().substring(0, 3);
        int courseCredits = courseNode.getCourse().getCredits();

        if (operation == Operation.ADD) {
            if (!facultyMap.containsKey(courseFaculty)) {
                facultyMap.put(courseFaculty, courseCredits);
            } else {
                facultyMap.put(courseFaculty, facultyMap.get(courseFaculty) + courseCredits);
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
        for (Map.Entry mapElement : facultyMap.entrySet()) {
            String key = (String) mapElement.getKey();
            int value = (int) mapElement.getValue();

            getChildren().add(new Text(key + " " + value));

        }


    }
}

