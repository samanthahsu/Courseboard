package visualization;

import exceptions.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Course;

import java.util.Arrays;
import java.util.LinkedList;

// todo next thing should be saving and loading, that's important
//  term and course can be serialized, the rest need to be stuffed into a new object
// todo common to both adding and editing courses
public abstract class CourseWindow extends Stage {

    static final String REQUISITE_COURSE_ID_SPLITTOR = ", ";

    protected CourseNode courseNode;
    protected Scene scene;
    protected GridPane gridPane = new GridPane();
    protected TextField courseCodeText;
    protected TextField notes;
    protected TextField creditsText;
    protected TextField prereqText;
    protected TextField coreqText;
    protected Button submitBtn;
//    ^^ these are initialized by caller

    public CourseWindow(CourseNode cn) {
        super();
        this.courseNode = cn;
        formatAndShow();
    }

    protected void formatAndShow() {
        scene = new Scene(gridPane);
        setScene(scene);
        initTitle();
        initTextFields();
        initButton();
        formatGridpane();
        setButtonHandler();
        sizeToScene();
        show();
    }

//    post: all textfields are initialized
    abstract protected void initTextFields();
    abstract protected void initButton();
    abstract protected void initTitle();

    protected void formatGridpane() {
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(new Label("Course Code"), 0, 0);
        gridPane.add(courseCodeText, 1, 0);

        gridPane.add(new Label("Credits"), 0, 1);
        gridPane.add(creditsText, 1, 1);

        gridPane.add(new Label("Pre-reqs"), 0, 2);
        gridPane.add(prereqText, 1, 2);

        gridPane.add(new Label("Co-reqs"), 0, 3);
        gridPane.add(coreqText, 1, 3);

        gridPane.add(new Label("Notes"), 0, 4);
        gridPane.add(notes, 1, 4);

        gridPane.add(submitBtn, 0, 5);
    }

/**
 * checks for input errors before firing
 * saves entered data and closes window*/
    protected void setButtonHandler() {
        submitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    updateCourseInfo();
                    courseNode.updateDisplay();
                    close();
                } catch (BadCourseCodeException e) {
                    courseCodeText.requestFocus();
                } catch (BadCreditException e) {
                    creditsText.requestFocus();
                } catch (BadPrereqException e) {
                    prereqText.requestFocus();
                } catch (BadCoreqException e) {
                    coreqText.requestFocus();
                } catch (CourseInputException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**checks for valid input
     * capitalizes all course codes
     * highlighting if not valid
     * if ok @updateCourseInfoHelper*/
    protected boolean updateCourseInfo() throws CourseInputException {
        String courseCode = courseCodeText.getText().toUpperCase();
        String credits = creditsText.getText();
        String prereqs = prereqText.getText();
        String coreqs = coreqText.getText();

        if (courseCode.matches(".*\\W.*") && courseCode.length() > 4) {
            throw new BadCourseCodeException();
        } else if (!credits.matches("\\d\\d*")) {
            throw new BadCreditException();
        } else if (!prereqs.matches(".*(\\w| )*.*")) {
            throw new BadPrereqException();
        } else if (!coreqs.matches(".*(\\w| )*.*")) {
            throw new BadCoreqException();
        }
        updateCourseInfoHelper(courseCode, credits, prereqs, coreqs);
        return true;
    }

//    updates course inside the node using info from fields
    protected void updateCourseInfoHelper(String courseCode, String credits, String prereqs, String coreqs) {
        Course course = courseNode.getCourse();
        course.setId(courseCode);
        course.setCredits(Integer.parseInt(credits));
        course.setNotes("");
        LinkedList<String> prereqList = new LinkedList<String>(Arrays.asList(prereqs.split(REQUISITE_COURSE_ID_SPLITTOR)));
        course.setPreReq(prereqList);
        LinkedList<String> coreqList = new LinkedList<String>(Arrays.asList(coreqs.split(REQUISITE_COURSE_ID_SPLITTOR)));
        course.setCoReq(coreqList);

        updateBoardManager();
    }

    protected abstract void updateBoardManager();

}
