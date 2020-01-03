package visualization;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddCourseWindow extends CourseWindow {

    AddCourseWindow(CourseNode cn) {
        super(cn);
    }

    @Override
    protected void initTextFields() {
        courseCodeText = new TextField("");
        notes = new TextField("");
        creditsText = new TextField("");
        prereqText = new TextField("");
        coreqText = new TextField("");
    }

    @Override
    protected void initButton() {
        submitBtn = new Button("Add Course");
    }

    @Override
    protected void initTitle() {
        setTitle("Adding Course");
    }
}
