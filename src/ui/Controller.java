package ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.GeneralManager;
import model.Term;


public class Controller {

    @FXML
    TextField txtAddCrsCode, txtAddCrsCred, txtAddCrsPre, txtAddCrsCo, txtAddCrsTerm;
    @FXML
    TextField txtDltCrsCode, txtAddTermName, txtDltTermName;
    @FXML
    VBox termContainer;
    @FXML
    Pane pane;

    GeneralManager gm = new GeneralManager();

//    post: course is added based on the user entered txt fields
    public void addCourse() {


    }
    public void deleteCourse() {}

//    checks if name is taken, if it is give an error, if not create term that has the given name
//    and corresponding layout in the view
    public void addTerm() {
//        if (termContainer.getChildren().contains()) {
//            throw error;
//        }
        Term newTerm = null;
        termContainer.getChildren().add(newTerm);

    }
    public void deleteTerm() {}

}
