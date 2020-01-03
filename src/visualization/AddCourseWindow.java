package visualization;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddCourseWindow extends CourseWindow {
//todo make it actually add a thing to pane

    AddCourseWindow(CourseNode cn) {
        super(cn);
        cn.getBoardManager(); // key to the door?
        courseCodeText = new TextField("");
        notes = new TextField("");
        creditsText = new TextField("");
        prereqText = new TextField("");
        coreqText = new TextField("");
        submitBtn = new Button("Add Course");
        setTitle("Adding Course");
        formatGridpane();
        setButtonHandler();
        formatAndShow();
    }
}
