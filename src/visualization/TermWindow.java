package visualization;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public abstract class TermWindow extends Stage {

    BoardManager boardManager;

    TermWindow(BoardManager boardManager) {
        this.boardManager = boardManager;
        Label label = new Label("Term Name");
        TextField termNameTxt = new TextField();
        Button submitBtn = initBtn();
        submitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                buttonAction(termNameTxt);
                close();
            }
        });
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(20, 10, 20, 10));
        vBox.setSpacing(10);
        vBox.getChildren().addAll(label, termNameTxt, submitBtn);
        setScene(new Scene(vBox));
        sizeToScene();
        show();
    }

    abstract Button initBtn();

    abstract void buttonAction(TextField termName);
}
