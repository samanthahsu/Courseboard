package ui;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.SavedCourse;

public class EditCourseWindow extends CourseWindow {

    EditCourseWindow(CourseNode cn) {
        super(cn);
    }

    @Override
    protected void initTextFields() {
        SavedCourse savedCourse = courseNode.getSavedCourse();
        courseCodeText = new TextField(savedCourse.getcID().toString());
        notes = new TextField(savedCourse.getNotes());
        creditsText = new TextField(Integer.toString(savedCourse.getCredits()));
        preReqText = new TextField(savedCourse.getAllPreReqDisplayString());
        coReqText = new TextField(savedCourse.getAllCoReqDisplayString());
    }

    @Override
    protected void initButton() {
        submitBtn = new Button("Save Edits");
        submitBtn.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    submitBtn.fire();
                }
            }
        });
    }

    @Override
    protected void initTitle() {
        setTitle("Editing Course");
    }

    @Override
    protected void updateBoardManager() {
        courseNode.getBoardManager().editCourseUpdate(courseNode);
    }
}

