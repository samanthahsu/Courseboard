package visualization;


import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Course;


public class EditCourseWindow extends CourseWindow {

//    todo keep all fields filled in
    EditCourseWindow(CourseNode cn) {
        super(cn);
        Course course = cn.getCourse();
        courseCodeText = new TextField(course.getId());
        notes = new TextField(course.getNotes());
        creditsText = new TextField(Integer.toString(course.getCredits()));
        prereqText = new TextField(course.getPrereqDisplayString());
        coreqText = new TextField(course.getCoreqDisplayString());
        submitBtn = new Button("Save Edits");
        setTitle("Editing Course");
        formatGridpane();
        setButtonHandler();
        formatAndShow();
    }


}

