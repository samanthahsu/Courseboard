package visualization;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Course;
import model.Term;


public class TermNode extends Region {

    GridPane gridPane;
    TextField title;
    VBox vBox;
    Term term;
    Rectangle background = new Rectangle();

    TermNode() {
        term = new Term("");
        vBox = new VBox();
        title = new TextField();
        gridPane = new GridPane();
        for (int i = 0; i < 5; i++) {
            gridPane.add(new CourseNode(
                    new Course("", "", 0, null, null)), i, 0);

        }
        gridPane.setMinSize(200, 150);

        vBox.getChildren().addAll(title, gridPane);
        vBox.setPadding(new Insets(10));

        background.setFill(Color.GRAY);
        background.setWidth(vBox.getHeight());
        background.setHeight(vBox.getWidth());

        getChildren().addAll(background, vBox);

    }

    public void setHandlers() {
        title.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                term.setName(title.getText());
            }
        });
        gridPane.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != gridPane && event.getDragboard().hasString()) {

                }
            }
        });
        gridPane.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
            }
        });
    }

    public void addCourse() {

    }
}
