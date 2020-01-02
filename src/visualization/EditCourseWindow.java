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


public class EditCourseWindow extends Stage {

//    todo make edit box already have course info stuff from before
    Course course = new Course("");
    GridPane gridPane = new GridPane();
    TextField courseCodeText;
    TextField notes = new TextField();
    TextField creditsText = new TextField();
    TextField prereqText = new TextField();
    TextField coreqText = new TextField();
    Button submitBtn = new Button("Save");

    EditCourseWindow(CourseNode cn) {
        super();
        course = cn.getCourse();
        courseCodeText = new TextField(course.getId());
        notes = new TextField(course.getNotes());
        creditsText = new TextField(Integer.toString(course.getCredits()));
        setTitle("Editing Course");
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
        submitBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
//                todo check for valid inputs
                try {
                    updateCourseInfo();
                    cn.updateDisplay();
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
        setScene(new Scene(gridPane, 500, 500));
        sizeToScene();
        show();
    }

    private boolean updateCourseInfo() throws CourseInputException {
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

        course.setId(courseCode);
        course.setCredits(Integer.parseInt(credits));
        course.setNotes("");
        LinkedList<String> prereqList = new LinkedList<String>(Arrays.asList(prereqs.split(" ")));
        course.setPreReq(prereqList);
        LinkedList<String> coreqList = new LinkedList<String>(Arrays.asList(coreqs.split(" ")));
        course.setCoReq(coreqList);
        return true;
    }
}

