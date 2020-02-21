package ui;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddCourseWindow extends CourseWindow {

    AddCourseWindow(CourseNode cn) {
        super(cn);
    }

    @Override
    protected void initTextFields() {
        subjectCodeText = new TextField("");
        courseCodeText = new TextField("");
        notes = new TextField("");
        creditsText = new TextField("");
        preReqText = new TextField("");
        coReqText = new TextField("");
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

//    todo do a lot of decoupling here
    @Override
    protected void updateBoardManager() {
        courseNode.getBoardManager().addCourseUpdate(courseNode);
    }
}
