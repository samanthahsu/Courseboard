package visualization;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Course;

public class EditCourseWindow extends CourseWindow {

    EditCourseWindow(CourseNode cn) {
        super(cn);
    }

    @Override
    protected void initTextFields() {
        Course course = courseNode.getCourse();
        courseCodeText = new TextField(course.getId());
        notes = new TextField(course.getNotes());
        creditsText = new TextField(Integer.toString(course.getCredits()));
        prereqText = new TextField(course.getAllPrereqDisplayString());
        coreqText = new TextField(course.getAllCoreqDisplayString());
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

