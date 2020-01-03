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

//    todo add another observer to edit the coursenode contents according to course contents
    private static final double H_PAD = 20;
    private static final double V_PAD = 20;
    private static final double V_SPAC = 5;
    static final int MIN_WIDTH = 200;
    static final int MIN_HEIGHT = 150;

    private Text courseIdTxt;
    private Text notesTxt;
    private Text creditsTxt;
    private Text preReqsText;
    private Text coReqsText;
    private Button editBtn;

    private Color fillColor = Color.BEIGE;
    private Color borderColor = Color.GRAY;
    private Set<CourseNode> dependencies;
    private VBox vBox;
    private Course course;

    private BoardManager boardManager;


    public CourseNode(Course c, BoardManager boardManager) {
        this.boardManager = boardManager;
        course = c;
        updateColors();
        formatVbox();
        makeDraggable();
        updateDisplay();
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
        preReqsText.setStroke(Color.RED);
        coReqsText = new Text("Co-reqs here");
        coReqsText.setStroke(Color.ORANGE);
        formatButton();

        vBox.setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
        vBox.getChildren().addAll(courseIdTxt, creditsTxt, notesTxt, preReqsText, coReqsText, editBtn);
    }

    private void formatButton() {
        editBtn = new Button("Edit");
        CourseNode me = this;
        editBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                new EditCourseWindow(me);
            }
        });
    }

    private void formatCreditsTxt() {
        creditsTxt = new Text("Credits: " + Integer.toString(course.getCredits()));
    }

    private void initCourseCodeTxt() {
        courseIdTxt = new Text("ID: " + course.getId());
    }

    private void formatDescripFlow() {
        notesTxt = new Text(course.getNotes());
        TextFlow tf = new TextFlow(notesTxt);
        tf.maxWidthProperty().bind(vBox.widthProperty().subtract(H_PAD*2));
    }

    public Course getCourse() {
        return course;
    }

    public BoardManager getBoardManager() {
        return boardManager;
    }

    public String getCourseId() {
        return courseIdTxt.getText();
    }

    public void setCourseId(String courseCodeTxt) {
        this.courseIdTxt.setText(courseCodeTxt);
    }

    public void updateMissingCourses(String miss) {
//        todo appends a text onto course with name of MISSING ONLY and colorcoded by type (coreq/prereq)
//            so if the courses in this list are not on the list of the general manager, we save those and display them with text
//          otherwise, update the connections to connect this node to other required ones
//        todo MOVE THAT PART HIGHER UP TO CONNECTION MANAGER
        preReqsText.setText(course.getPrereqDisplayString());
        coReqsText.setText(course.getCoreqDisplayString());
    }

    public void updateDisplay() {
//        todo updates fields according to course data
        courseIdTxt.setText(course.getId());
        creditsTxt.setText(Integer.toString(course.getCredits()));
        preReqsText.setText(course.getPrereqDisplayString());
        coReqsText.setText(course.getCoreqDisplayString());
        updateBoard();
    }

    public void updateBoard() {
//        todo notifies cm that things are a changing, and they need to update visuals to match changes in this courseNode
        boardManager.addCourseUpdate(this);
    }
}
