package ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import model.SavedCourse;

import java.util.Set;

public class CourseNode extends BoardComponent {

//    todo add another observer to edit the courseNode contents according to course contents
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

    private Color fillColor = Color.BEIGE;
    private Color borderColor = Color.GRAY;
    private VBox mainBody;

//    some data needed to be displayed
    private SavedCourse savedCourse;

    private BoardManager boardManager;
    private Set<Connection> connectionsSet;
    // todo used to update when the coursenode gets dragged around


    /** constructor of courseNode from savedCourse
     * courseNode should always have non NULL savedCourse
     * boardManager essential for event filtering*/
    public CourseNode(BoardManager boardManager, SavedCourse savedCourse) {
        this.boardManager = boardManager;
        this.savedCourse = savedCourse;
        updateColors();
        formatVbox();
        createContextMenu();
        getChildren().add(mainBody);
    }

    @Override
    public void addCustomOnMouseDragged(MouseEvent event) {
        boardManager.onDragDetectedCourseNode(this, event);
        setMouseTransparent(true);
    }

    @Override
    public void addCustomOnMouseReleased(MouseEvent event) {
        setMouseTransparent(false);
    }


    private void updateColors() {
//        todo changes fill and border colors based on course properties
    }

    private void formatVbox() {
        mainBody = new VBox(V_SPAC);
        mainBody.setAlignment(Pos.CENTER);
        mainBody.setPadding(new Insets(V_PAD, H_PAD, V_PAD, H_PAD));
        mainBody.setMinSize(MIN_WIDTH, MIN_HEIGHT);

        initCourseCodeTxt();
        formatDesFlow();
        formatCreditsTxt();
        preReqsText = new Text("Pre-reqs here");
        preReqsText.setStroke(Color.RED);
        coReqsText = new Text("Co-reqs here");
        coReqsText.setStroke(Color.ORANGE);

        mainBody.setBackground(new Background(new BackgroundFill(Color.BURLYWOOD, null, null)));
        mainBody.getChildren().addAll(courseIdTxt, creditsTxt, notesTxt, preReqsText, coReqsText);
    }

    private void formatCreditsTxt() {
        creditsTxt = new Text("Credits: " + Integer.toString(savedCourse.getCredits()));
    }

    private void initCourseCodeTxt() {
        courseIdTxt = new Text("ID: " + savedCourse.getcID().toString());
    }

    private void formatDesFlow() {
        notesTxt = new Text(savedCourse.getNotes());
        TextFlow tf = new TextFlow(notesTxt);
        tf.maxWidthProperty().bind(mainBody.widthProperty().subtract(H_PAD * 2));
    }

    public SavedCourse getSavedCourse() {
        return savedCourse;
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

    public void addConnection(Connection c) {
        connectionsSet.add(c);
    }

    public void removeConnection(Connection c) {
        connectionsSet.remove(c);
    }

/**
    REQUIRE: @missingCourseIds has key @this already
      @missingCourseIds has been suitably updated
    Populates displayed node with data from @savedCourse
    assumes @savedCourse has already been updated accordingly
*/
    public void updateDisplay() {
        courseIdTxt.setText("ID: " + savedCourse.getcID().toString());
        creditsTxt.setText("Credits: " + savedCourse.getCredits());
//        requirements are all stored in boardManager todo same for coreq
        CourseList preReqList = new CourseList(boardManager.missingCourseIds.get(this), CourseList.PRE_REQ);
        preReqsText.setText("PreReqs: " + preReqList.toDisplayString());
        coReqsText.setText(savedCourse.getAllCoReqDisplayString());
    }

    private void createContextMenu() {
        ContextMenu contextMenu = new ContextMenu();

        CourseNode thisNode = this;
        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boardManager.removeCourseUpdate(thisNode);
            }
        });
        MenuItem editItem = new MenuItem("Edit");
        editItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new EditCourseWindow(thisNode);
            }
        });
        contextMenu.getItems().addAll(editItem, deleteItem);
        setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                contextMenu.show(thisNode, event.getScreenX(), event.getScreenY());
            }
        });
    }

/** Pre: courseNode position has changed
 * Post: redraws all connections to match position*/
    public void redrawConnections() {
        for(Connection c : connectionsSet) {
            c.draw();
        }

    }
}
