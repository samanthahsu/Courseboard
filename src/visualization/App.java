package visualization;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Course;
import model.GeneralManager;

public class App extends Application {

    GeneralManager generalManager = new GeneralManager();

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox();
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        MenuItem newItem = new MenuItem("New...");
        MenuItem openItem = new MenuItem("Open...");
        MenuItem saveItem = new MenuItem("Save");
        MenuItem saveAsItem = new MenuItem("Save As...");
        menuFile.getItems().addAll(newItem, openItem, saveAsItem, saveItem);
        menuBar.getMenus().add(menuFile);

        Pane pane = new Pane();
        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount() == 2) {
                    generalManager.addCourse(pane, event);
                }
            }
        });

//      todo figure out how to place the different nodes using calculations
        CourseNode node1 = new CourseNode(new Course("hi", "edefdsfd", 3, null, null));
        node1.relocate(50,50);

        CourseNode node2 = new CourseNode(new Course("double hi", "wow do i exuisetnkjsd", 666, null, null));
        node2.relocate(150,250);

        Connection conn = new Connection(node1, node2);

        CourseNode node3 = new CourseNode(new Course("CPSC221", "actually a useful course", 4, null, null));
        node3.relocate(450, 400);

        pane.getChildren().addAll(conn, node1, node2, node3);

        root.getChildren().addAll(menuBar, pane);

        primaryStage.setResizable(true);
        primaryStage.setScene(new Scene(root, 1024, 768));
        primaryStage.setTitle("Testing...");
        primaryStage.sizeToScene();
        primaryStage.show();
    }

}
