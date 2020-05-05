package ui;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.SavedCourse;

/** initializes window for adding a new course to the board*/
public class AddCourseWindow extends CourseWindow {

    /**creates blank courseNode*/
    AddCourseWindow(BoardManager boardManager) {
        super(new CourseNode(boardManager, new SavedCourse("NONE", 0)));
    }

    /** all text fields are initialized as empty*/
    @Override
    protected void initTextFields() {
        subjectCodeText = new TextField("");
        courseCodeText = new TextField("");
        notes = new TextField("");
        creditsText = new TextField("");
        preReqText = new TextField("");
        coReqText = new TextField("");
    }

    /** enables enter to activate submission button for ease of use
     * also labels button as Add Course*/
    @Override
    protected void initButton() {
        submitBtn = new Button("Add Course");
        submitBtn.setDefaultButton(true);

    }

    /** initializes window title as appropriate*/
    @Override
    protected void initTitle() {
        setTitle("Adding Course");
    }

//    todo remove decoupling here
    /** called when*/
    @Override
    protected void updateBoardManager() {
        courseNode.getBoardManager().addCourseUpdate(courseNode);
    }
}
