//package visualization.term;
//
//import javafx.scene.control.Button;
//import javafx.scene.control.TextField;
//import visualization.BoardManager;
//
//public class EditTermWindow extends TermWindow {
//
//    TermNode termNode;
//
//    public EditTermWindow(TermNode thisTerm, BoardManager boardManager) {
//        super(boardManager);
//        this.termNode = thisTerm;
//    }
//
//    @Override
//    Button initBtn() {
//        return new Button("Save");
//    }
//
//    @Override
//    void buttonAction(TextField termName) {
//        termNode.setTitle(termName.getText());
//    }
//}
