package visualization;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.Course;

import java.util.Set;

public class CourseNode extends BoardComponent {

    private static final double H_PAD = 20;
    private static final double V_PAD = 20;
    private static final double V_SPAC = 5;
    static final int MIN_WIDTH = 200;
    static final int MIN_HEIGHT = 150;

    private TextField courseCodeTxt;
    private TextArea descripArea;
    private Label creditsLabel = new Label("Credits:"); // todo use label
    private TextField creditsTxt;
    private Label preReqsLabel;
    private Text preReqsText;
    private Label coReqsLabel;
    private Text coReqText;
    private Button editBtn;

    Color fillColor = Color.BEIGE;
    Color borderColor = Color.GRAY;
    Set<CourseNode> dependencies;
    VBox vBox;
    private Rectangle rectangle;
    private Course data;


    public CourseNode(Course c) {
        data = c;
        setId(data.getCode());
        updateColors();
        formatRectangle();
        formatVbox();
        makeDraggable();


        getChildren().addAll(rectangle, vBox);
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
        formatDescripArea();
        formatCreditsTxt();

        editBtn = new Button("Edit");
        editBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                EditCourseWindow editCourseWindow = new EditCourseWindow(data);
            }
        });


        vBox.getChildren().addAll(courseCodeTxt, creditsTxt, descripArea, editBtn);
    }

    private void formatCreditsTxt() {
        creditsTxt = new TextField(Integer.toString(data.getCredits()));
        EventHandler<KeyEvent> me = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    requestFocus();
                    String ui = creditsTxt.getText();
                    if (ui.matches("[0-9]*")) {
                        data.setCredits(Integer.parseInt(ui));
                    } else {
                        creditsTxt.setText(Integer.toString(data.getCredits()));
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("Please enter an integer");
                        alert.showAndWait();
                    }
                }
            }
        };
        creditsTxt.addEventHandler(KeyEvent.KEY_PRESSED, me);
    }

    private void initCourseCodeTxt() {
        courseCodeTxt = new TextField(data.getCode());
        EventHandler<KeyEvent> eventHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    requestFocus();
                    String uiText = courseCodeTxt.getText();
                    if(!uiText.matches(".*\\W.*")) {
//                    if is legal course code
                        data.setCode(uiText.toUpperCase());
                        courseCodeTxt.setText(data.getCode());
                    } else {
                        courseCodeTxt.setText(data.getCode());
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("Please enter a valid Course Code eg. 'CPSC221'");
                        alert.showAndWait();
                    }
                }
            }
        };
        courseCodeTxt.addEventHandler(KeyEvent.KEY_PRESSED, eventHandler);
    }

    private void formatDescripArea() {
        descripArea = new TextArea(data.getDescription());
        descripArea.maxWidthProperty().bind(rectangle.widthProperty().subtract(H_PAD*2));
        descripArea.setMaxHeight(75);

        EventHandler<KeyEvent> exitDescripTextEH = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER && !event.isShiftDown()) {
                    requestFocus();
                    data.setDescription(descripArea.getText());
                } else if (event.getCode() == KeyCode.ENTER) {
                    descripArea.setText(descripArea.getText() + "\n");
                    descripArea.setWrapText(true);
                }
            }
        };
        descripArea.addEventHandler(KeyEvent.KEY_PRESSED, exitDescripTextEH);
    }

    private void formatRectangle() {
        rectangle = new Rectangle();
        rectangle.setFill(fillColor);
        rectangle.setStroke(borderColor);
        rectangle.setHeight(MIN_HEIGHT + V_PAD*2);
        rectangle.setWidth(MIN_WIDTH + H_PAD*2);
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



    public String getCourseCodeTxt() {
        return courseCodeTxt.getText();
    }

    public void setCourseCodeTxt(String courseCodeTxt) {
        this.courseCodeTxt.setText(courseCodeTxt);
    }

}
