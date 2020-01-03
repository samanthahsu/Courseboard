package visualization;

import Exceptions.*;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Course;

import java.util.Arrays;
import java.util.LinkedList;

//todo common to both adding and editing courses
public class CourseWindow extends Stage {

    static final String REQUISITE_COURSE_ID_SPLITTOR = ", ";

    protected CourseNode courseNode;
    protected GridPane gridPane = new GridPane();
    protected TextField courseCodeText;
    protected TextField notes;
    protected TextField creditsText;
    protected TextField prereqText;
    protected TextField coreqText;
    protected Button submitBtn;

    public CourseWindow(CourseNode cn) {
        super();
        this.courseNode = cn;
    }

    protected void formatAndShow() {
        setScene(new Scene(gridPane, 500, 500));
        sizeToScene();
        show();

    }

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

    protected void setButtonHandler() {
        submitBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    updateCourseInfoGuard();
                    courseNode.updateDisplay();
                    close();
                } catch (BadCourseCodeException e) {
                    courseCodeText.requestFocus();
//                   todo highlight corresponding text things
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

    protected boolean updateCourseInfoGuard() throws CourseInputException {
        String courseCode = courseCodeText.getText();
        String credits = creditsText.getText();
        String prereqs = prereqText.getText();
        String coreqs = coreqText.getText();

        if (courseCode.matches(".*\\W.*")) {
            throw new BadCourseCodeException();
        } else if (!credits.matches("[0-9]*")) {
            throw new BadCreditException();
        } else if (!prereqs.matches(".*(\\w| )*.*")) {
            throw new BadPrereqException();
        } else if (!coreqs.matches(".*(\\w| )*.*")) {
            throw new BadCoreqException();
        }
        updateCourseInfo(courseCode, credits, prereqs, coreqs);
//        updateConnections();TODO
        return true;
    }

    protected void updateCourseInfo(String courseCode, String credits, String prereqs, String coreqs) {
        Course course = courseNode.getCourse();
        course.setId(courseCode);
        course.setCredits(Integer.parseInt(credits));
        course.setNotes("");
        LinkedList<String> prereqList = new LinkedList<String>(Arrays.asList(prereqs.split(REQUISITE_COURSE_ID_SPLITTOR)));
        course.setPreReq(prereqList);
        LinkedList<String> coreqList = new LinkedList<String>(Arrays.asList(coreqs.split(REQUISITE_COURSE_ID_SPLITTOR)));
        course.setCoReq(coreqList);
    }

}