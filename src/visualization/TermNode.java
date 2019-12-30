package visualization;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Term;


public class TermNode extends BoardComponent {

    GridPane gridPane;
    TextField title;
    VBox vBox;
    Term term;

    TermNode() {
        term = new Term("");
        vBox = new VBox();
        initTitle();
        gridPane = new GridPane();
        makeDraggable();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                Rectangle filler = new Rectangle(100, 100, Color.BEIGE);
                filler.setStroke(Color.GRAY);
                gridPane.setGridLinesVisible(true);
                gridPane.add(filler, i, j, 1, 1);

            }
        }

        vBox.getChildren().addAll(title, gridPane);
        vBox.setPadding(new Insets(20));
        vBox.setSpacing(10);
        vBox.setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
        getChildren().add(vBox);
//        todo make the course node on top of the term ones
//        todo when a course is dragged in,
    }

    private void initTitle() {
        title = new TextField();
        title.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    requestFocus();
                    term.setTitle(title.getText());
                }
            }
        });
    }

    public void addCourse() {

    }
}
