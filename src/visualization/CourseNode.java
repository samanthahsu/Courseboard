package visualization;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import model.Course;

import java.util.Set;

public class CourseNode extends BoardComponent {

    private static final double H_PAD = 20;
    private static final double V_PAD = 20;
    private static final double V_SPAC = 5;
    static final int MIN_WIDTH = 200;
    static final int MIN_HEIGHT = 150;

    private Text courseCodeTxt;
    private Text notesTxt;
    private Label creditsLabel = new Label("Credits:"); // todo use label
    private Text creditsTxt;
    private Text preReqsText;
    private Text coReqsText;
    private Button editBtn;

    Color fillColor = Color.BEIGE;
    Color borderColor = Color.GRAY;
    Set<CourseNode> dependencies;
    VBox vBox;
    private Course courseData;


    public CourseNode(Course c) {
        courseData = c;
        setId(courseData.getCode());
        updateColors();
        formatVbox();
        makeDraggable();
        getChildren().add(vBox);
    }


    private void updateColors() {
//        todo changes fill and border colors based on course properties
    }

    private void formatVbox() {
        vBox = new VBox(V_SPAC);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(V_PAD, H_PAD, V_PAD, H_PAD));
        vBox.setMinSize(MIN_WIDTH, MIN_HEIGHT);

        initCourseCodeTxt();
        formatDescripFlow();
        formatCreditsTxt();
        preReqsText = new Text("Pre-reqs here");
        coReqsText = new Text("Co-reqs here");
        formatButton();

        vBox.setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
        vBox.getChildren().addAll(courseCodeTxt, creditsTxt, notesTxt, preReqsText, coReqsText, editBtn);
    }

    private void formatButton() {
        editBtn = new Button("Edit");
        editBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                EditCourseWindow editCourseWindow = new EditCourseWindow(courseData);
            }
        });
    }

    private void formatCreditsTxt() {
        creditsTxt = new Text("Credits: " + Integer.toString(courseData.getCredits()));
    }

    private void initCourseCodeTxt() {
        courseCodeTxt = new Text("ID: " + courseData.getCode());
    }

    private void formatDescripFlow() {
        notesTxt = new Text(courseData.getDescription());
        TextFlow tf = new TextFlow(notesTxt);
        tf.maxWidthProperty().bind(vBox.widthProperty().subtract(H_PAD*2));
    }


    public void doubleClickEditCourse() {
        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount() == 2) {
                    EditCourseWindow ew = new EditCourseWindow(null);
                }
            }
        });
    }

    public Course getCourseData() {
        return courseData;
    }


    public String getCourseCodeTxt() {
        return courseCodeTxt.getText();
    }

    public void setCourseCodeTxt(String courseCodeTxt) {
        this.courseCodeTxt.setText(courseCodeTxt);
    }

    public void displayMissing(String miss) {
//        todo appends a text onto course with name of missingCourse and colorcoded by type (coreq/prereq)
    }
}
