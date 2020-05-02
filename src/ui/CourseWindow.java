package ui;

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

 /** common to both adding and editing courses */
//todo separate window for prereq adding
public abstract class CourseWindow extends Stage {

    static final String REQUISITE_COURSE_ID_SPLITTOR = ", ";

    protected CourseNode courseNode;
    protected Scene scene;
    protected GridPane gridPane;
    protected TextField subjectCodeText;
    protected TextField courseCodeText;
    protected TextField notes;
    protected TextField creditsText;
    protected TextField preReqText;
    protected TextField coReqText;
    protected Button submitBtn;
//    ^ these are initialized by caller

    public CourseWindow(CourseNode cn) {
        super();
        this.courseNode = cn;
        gridPane = new GridPane();
        formatAndShow();
    }

    protected void formatAndShow() {
        scene = new Scene(gridPane);
        setScene(scene);
        initTitle();
        initTextFields();
        initButton();
        formatGridPane();
        setButtonHandler();
        sizeToScene();
        show();
    }

//    post: all textfields are initialized
    abstract protected void initTextFields();
    abstract protected void initButton();
    abstract protected void initTitle();

    protected void formatGridPane() {
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(5);
        gridPane.add(new Label("Subject Code"), 0, 0);
        gridPane.add(subjectCodeText, 1, 0);

        gridPane.add(new Label("Course Code"), 0, 1);
        gridPane.add(courseCodeText, 1, 1);

        gridPane.add(new Label("Credits"), 0, 2);
        gridPane.add(creditsText, 1, 2);

        gridPane.add(new Label("Pre-reqs"), 0, 3);
        gridPane.add(preReqText, 1, 3);

        gridPane.add(new Label("Co-reqs"), 0, 4);
        gridPane.add(coReqText, 1, 4);

        gridPane.add(new Label("Notes"), 0, 5);
        gridPane.add(notes, 1, 5);

        gridPane.add(submitBtn, 0, 6);
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
                } catch (BadSubjectCodeException e) {
                    subjectCodeText.requestFocus();
                } catch (BadCourseCodeException e) {
                    courseCodeText.requestFocus();
                } catch (BadCreditException e) {
                    creditsText.requestFocus();
                } catch (BadPrereqException e) {
                    preReqText.requestFocus();
                } catch (BadCoreqException e) {
                    coReqText.requestFocus();
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
        String subjectCode = subjectCodeText.getText().toUpperCase();
        String courseCode = courseCodeText.getText();
        String credits = creditsText.getText();
        String prereqs = preReqText.getText().toUpperCase();
        String coreqs = coReqText.getText().toUpperCase();

        if (subjectCode.matches(".*\\W.*")) {
            throw new BadSubjectCodeException();
        } else if (!courseCode.matches("\\d\\d*")) {
            throw new BadCourseCodeException();
        } else if (!credits.matches("\\d\\d*")) {
            throw new BadCreditException();
        } else if (!prereqs.matches(".*(\\w| )*.*")) {
            throw new BadPrereqException();
        } else if (!coreqs.matches(".*(\\w| )*.*")) {
            throw new BadCoreqException();
        }
        updateCourseInfoHelper(subjectCode, courseCode, credits, prereqs, coreqs);
        return true;
    }

/**
 * updates course inside the node using info from fields
*/
    protected void updateCourseInfoHelper(String subjectCode, String courseCode, String credits, String prereqs, String coreqs) {
        Course course = courseNode.getCourse();
        course.setcIDSubject(subjectCode);
        course.setcIDNumber(Integer.parseInt(courseCode));
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
