package visualization;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddTermWindow extends TermWindow {

    public AddTermWindow(BoardManager boardManager) {
        super(boardManager);
    }

    @Override
    Button initBtn() {
        return new Button("Add Term");
    }

    @Override
    void buttonAction(TextField termName) {
        boardManager.addTerm(termName.getText());
    }
}

