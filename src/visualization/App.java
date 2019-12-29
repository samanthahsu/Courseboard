package visualization;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Course;

import java.util.Observable;
import java.util.Observer;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane borderPane = new BorderPane();
        VBox vBox = new VBox();
        borderPane.setLeft(vBox);

        Pane pane = new Pane();
//      todo figure out how to place the different nodes using calculations
        CourseNode node1 = new CourseNode(new Course("hi", "edefdsfd", 3, null, null));
        node1.relocate(50,50);

        CourseNode node2 = new CourseNode(new Course("double hi", "wow do i exuisetnkjsd", 666, null, null));
        node2.relocate(150,250);

        Connection conn = new Connection(node1, node2);

        CourseNode node3 = new CourseNode(new Course("CPSC221", "actually a useful course", 4, null, null));
        node3.relocate(450, 400);

        pane.getChildren().addAll(conn, node1, node2, node3);

        primaryStage.setResizable(true);
        primaryStage.setScene(new Scene(pane, 1024, 768));
        primaryStage.setTitle("testing...");
        primaryStage.sizeToScene();
        primaryStage.show();
    }

}
