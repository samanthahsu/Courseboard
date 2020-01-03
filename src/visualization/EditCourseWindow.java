package visualization;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    }

    @Override
    protected void initTitle() {
        setTitle("Editing Course");
    }
}

