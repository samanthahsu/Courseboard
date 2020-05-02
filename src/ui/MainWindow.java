package ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;

public class MainWindow extends Application {

    File currFile;
    BoardManager boardManager;
    WriterReader writerReader;
    Stage mainStage; // used for file chooser

    @Override
    public void start(Stage stage) throws Exception {

        BorderPane root = new BorderPane();
        MenuBar menuBar = initMenuBar();

        StatListView statListView = new StatListView();

        Board board = new Board();
        BoardNodeGestures nodeGestures = new BoardNodeGestures(board); // have to add event filter from this to every draggable thing

        Label label1 = new Label("Draggable node 1");
        label1.setTranslateX(10);
        label1.setTranslateY(10);
        label1.addEventFilter( MouseEvent.MOUSE_PRESSED, nodeGestures.getOnMousePressedEventHandler());
        label1.addEventFilter( MouseEvent.MOUSE_DRAGGED, nodeGestures.getOnMouseDraggedEventHandler());

        board.getChildren().add(label1);

        Pane pane = new Pane();
        pane.getChildren().add(board);

//        gestures are applied to the scene
        BoardGestures boardGestures = new BoardGestures(board);
        pane.addEventFilter(ScrollEvent.ANY, boardGestures.getOnScrollEventHandler());
        pane.addEventFilter(MouseEvent.MOUSE_DRAGGED, boardGestures.getOnMouseDraggedEventHandler());
        pane.addEventFilter(MouseEvent.MOUSE_PRESSED, boardGestures.getOnMousePressedEventHandler());

        boardManager = new BoardManager(board, statListView, nodeGestures);

//        place all in border pane
        root.setCenter(pane);
        root.setTop(menuBar);
        root.setRight(statListView);

        stage.setScene(new Scene(root, 1024, 800));
        stage.setTitle("CourseBoard");
//        stage.sizeToScene();
        stage.setMaximized(true);
        stage.show();


//        closes all offending windows when main window is closed
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
            }
        });


        mainStage = stage;
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
        addCourse.setOnAction(event -> {
            new AddCourseWindow(boardManager);
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
