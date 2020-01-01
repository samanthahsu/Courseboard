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


public class EditCourseWindow extends Stage {

    Course course;
    GridPane gridPane = new GridPane();
    TextField courseCodeText = new TextField();
    TextField description = new TextField();
    TextField creditsText = new TextField();
    TextField prereqText = new TextField();
    TextField coreqText = new TextField();
    Button submitBtn = new Button("Save");

    EditCourseWindow(Course course) {
        super();
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
        gridPane.add(description, 1, 4);

        gridPane.add(submitBtn, 0, 5);
        submitBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
//                todo check for valid inputs
                try {
                    if (updateCourseInfo()) {
                        close();
                    } else {
                        submitBtn.setText("Error in inputs!");
                    }
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

        if ((courseCode.matches(".*\\W.*"))) {
            throw new BadCourseCodeException();
        } else if (!credits.matches("[0-9]*")) {
            throw new BadCreditException();
        } else if (!prereqs.matches(".*(\\w| ).*")) {
            throw new BadPrereqException();
        } else if (!coreqs.matches(".*(\\w| ).*")) {
            throw new BadCoreqException();
        }

//      todo if all inputs valid update and return true
        return false;
    }
}

