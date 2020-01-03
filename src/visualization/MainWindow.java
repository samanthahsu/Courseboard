package visualization;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Course;
import model.GeneralManager;

import java.util.LinkedList;

public class MainWindow extends Application {

    public final static char PRE_REQ_TYPE = 'p';
    public final static char CO_REQ_TYPE = 'c';

    BoardManager boardManager;
//    todo enable saving and loading for display config and data classes: so course, term, course manager, nodes and stuff
//    todo also probably best to save the pane as well, to avoid recompiling

//    todo zoom in and out of pane

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox();
        MenuBar menuBar = initMenuBar();

        Board board = new Board();
        boardManager = new BoardManager(board);

//        LinkedList<String> prereqList = new LinkedList<>();
//        prereqList.add("MATH100");
//        boardManager.addCourse(new Course("CPSC121", "", 0, prereqList, new LinkedList<>()));

//        boardManager.addCourse(new Course("MATH100"));

//        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                if(event.getClickCount() == 2) {
//                    generalManager.addCourse(pane, event);
//                }
//            }
//        });

//           todo figure out how to place the different nodes using calculations
//        CourseNode node1 = new CourseNode(new Course("hi", "edefdsfd", 3, null, null));
//        node1.relocate(50,50);
//
//        CourseNode node2 = new CourseNode(new Course("double hi", "wow do i exuisetnkjsd", 666, null, null));
//        node2.relocate(150,250);
//
//        Connection conn = new Connection(node1, node2);
//
//        CourseNode node3 = new CourseNode(new Course("CPSC221", "actually a useful course", 4, null, null));
//        node3.relocate(450, 400);
//
//        TermNode term1 = new TermNode();
//        term1.relocate(500, 250);

//        pane.getChildren().addAll(conn, node1, node2, node3, term1);

        root.getChildren().addAll(menuBar, board);

        primaryStage.setResizable(true);
        primaryStage.setScene(new Scene(root, 1024, 800));
        primaryStage.setTitle("Courseboard");
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    private MenuBar initMenuBar() {
        MenuBar menuBar = new MenuBar();
        menuBar.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, null, null)));

        Menu menuFile = initMenuFile();
        Menu menuEdit = initMenuEdit();

        menuBar.getMenus().addAll(menuFile, menuEdit);
        menuBar.toFront();
        return menuBar;
    }

    private Menu initMenuEdit() {
        Menu menuEdit = new Menu("Edit");
        MenuItem addTerm = new MenuItem("Add Term");
        MenuItem addCourse = new MenuItem("Add Course");
        addCourse.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new AddCourseWindow(new CourseNode(new Course(""), boardManager));
            }
        });
        MenuItem clearAll = new MenuItem("Clear All");
        menuEdit.getItems().addAll(addTerm, addCourse, clearAll);
        return menuEdit;
    }

    private Menu initMenuFile() {
        Menu menuFile = new Menu("File");
        MenuItem newItem = new MenuItem("New...");
        MenuItem openItem = new MenuItem("Open...");
        MenuItem saveItem = new MenuItem("Save");
        MenuItem saveAsItem = new MenuItem("Save As...");
        menuFile.getItems().addAll(newItem, openItem, saveAsItem, saveItem);
        return menuFile;
    }

}
