package ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Course;
import model.Stats;

public class MainWindow extends Application {

    public final static char PRE_REQ_TYPE = 'p';
    public final static char CO_REQ_TYPE = 'c';

    BoardManager boardManager;
//    todo enable saving and loading for display config and data classes: so course, term, course manager, nodes and stuff
//    todo also probably best to save the pane as well, to avoid recompiling

//    todo zoom in and out of pane

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane root = new BorderPane();
        MenuBar menuBar = initMenuBar();

        Stats stats = new Stats();
        StatListView facList = new StatListView(stats);
        Board board = new Board(stats);
//        boardManager = new BoardManager(board, facList);

//        place all in border pane
        root.setTop(menuBar);
        root.setCenter(board);
        root.setRight(facList);


        primaryStage.setScene(new Scene(root, 1024, 800));
        primaryStage.setTitle("CourseBoard");
        primaryStage.sizeToScene();
        primaryStage.show();

//        init selection
//        new MouseEventHandler(board);

    }


    //    todo keep menuBar on top of everything
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
//        MenuItem addTerm = new MenuItem("Add Term");
//        addTerm.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                new AddTermWindow(boardManager);
//            }
//        });
        MenuItem addCourse = new MenuItem("Add Course");
        addCourse.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new AddCourseWindow(new CourseNode(new Course(""), boardManager));
            }
        });
        MenuItem clearAll = new MenuItem("Clear All");
        clearAll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boardManager.clearAll();
            }
        });
        menuEdit.getItems().addAll(/*addTerm, */addCourse, clearAll);
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