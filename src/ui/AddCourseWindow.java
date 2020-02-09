package ui;

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
        submitBtn.setDefaultButton(true);

    }

    @Override
    protected void initTitle() {
        setTitle("Adding Course");
    }

    @Override
    protected void updateBoardManager() {
        courseNode.getBoardManager().addCourseUpdate(courseNode);
    }
}
