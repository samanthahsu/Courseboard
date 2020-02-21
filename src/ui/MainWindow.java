package ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Course;
import model.Stats;

import java.io.File;

public class MainWindow extends Application {

    File currFile;
    BoardManager boardManager;
    WriterReader writerReader;
    Stage mainStage; // used for file chooser

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();
        MenuBar menuBar = initMenuBar();

        Stats stats = new Stats();
        StatListView statListView = new StatListView(stats);
        Board board = new Board(stats);
        boardManager = new BoardManager(board, stats);

//        place all in border pane
        root.setTop(menuBar);
        root.setCenter(board);
        root.setRight(statListView);


        primaryStage.setScene(new Scene(root, 1024, 800));
        primaryStage.setTitle("CourseBoard");
        primaryStage.sizeToScene();
        primaryStage.show();

//        closes all offending windows when main window is closed
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
            }
        });

        mainStage = primaryStage;
        writerReader = new WriterReader(boardManager);


    }

    private MenuBar initMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu menuFile = initMenuFile();
        Menu menuEdit = initMenuEdit();

        menuBar.getMenus().addAll(menuFile, menuEdit);
        menuBar.toFront();
        return menuBar;
    }

    private Menu initMenuEdit() {
        Menu menuEdit = new Menu("Edit");

        MenuItem addCourse = new MenuItem("Add Course");
        addCourse.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new AddCourseWindow(new CourseNode(new Course("", 0), boardManager));
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
        newItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currFile = null;
                boardManager.clearAll();
            }
        });
        MenuItem openItem = new MenuItem("Open...");
        openItem.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open File...");
            fileChooser.setInitialDirectory(new File(WriterReader.FILE_ROOT));
            File selectedFile = fileChooser.showOpenDialog(mainStage);
            writerReader.read(selectedFile);
        });
        MenuItem saveItem = new MenuItem("Save");
        saveItem.setOnAction(event -> {
            if (currFile != null) {
                writerReader.write(currFile);
            } else {
                saveAs();
            }
        });
        MenuItem saveAsItem = new MenuItem("Save As...");
        saveAsItem.setOnAction(event -> saveAs());
        menuFile.getItems().addAll(newItem, openItem, saveAsItem, saveItem);
        return menuFile;
    }

    /** common to both save and saveAs
     * opens file chooser
     * calls write
     * sets currFile*/
    private void saveAs() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save As...");
        File file = fileChooser.showSaveDialog(mainStage);
        fileChooser.setInitialDirectory(new File(WriterReader.FILE_ROOT));

        writerReader.write(file);
        currFile = file;
    }

}
