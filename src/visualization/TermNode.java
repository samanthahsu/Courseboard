package visualization;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.Term;

//todo make this work????
public class TermNode extends BoardComponent {

    GridPane gridPane;
    Text title;
    VBox vBox;
    Term term;
    BoardManager boardManager;

    TermNode(String name, BoardManager boardManager) {
        this.boardManager = boardManager;
        term = new Term(name);
        vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        title = new Text(name);
        gridPane = new GridPane();
        makeDraggable();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                Rectangle filler = new Rectangle(100, 100, Color.BEIGE);
                filler.setStroke(Color.DARKRED);
                gridPane.setGridLinesVisible(true);
                gridPane.add(filler, i, j, 1, 1);

            }
        }

        createContextMenu();

        vBox.getChildren().addAll(title, gridPane);
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);
        vBox.setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
        getChildren().add(vBox);
//        todo make the course node on top of the term ones
//        todo when a course is dragged in,
    }

    public void setTitle(String newName) {
        term.setTitle(newName);
        title.setText(newName);
    }

    private void createContextMenu() {
        ContextMenu contextMenu = new ContextMenu();

        TermNode thisTerm = this;
        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boardManager.removeTerm(thisTerm);
            }
        });
        MenuItem editItem = new MenuItem("Edit");
        editItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new EditTermWindow(thisTerm, boardManager);
            }
        });
        contextMenu.getItems().addAll(editItem, deleteItem);
        setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                contextMenu.show(thisTerm, event.getScreenX(), event.getScreenY());
            }
        });
    }



}
