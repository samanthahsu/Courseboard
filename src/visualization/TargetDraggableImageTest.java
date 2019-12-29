package visualization;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TargetDraggableImageTest extends Application
{

    Image img1 = new Image("https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png");

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        ImageView source = new ImageView();
        ImageView target = new ImageView();
        source.setImage(img1);

        StackPane stackPaneLeft = new StackPane(source);
        stackPaneLeft.setStyle("-fx-background-color: yellow");
        stackPaneLeft.setPrefSize(400, 800);

        stackPaneLeft.setOnDragDetected((MouseEvent event)
                -> {
            Dragboard db = source.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putImage(source.getImage());
            db.setContent(content);
            event.consume();
        });

        stackPaneLeft.setOnDragDone((DragEvent event) -> {
            if (event.getTransferMode() == TransferMode.MOVE) {
                source.setImage(null);
            }
            event.consume();
        });

        StackPane stackPaneRight = new StackPane(target);
        stackPaneRight.setStyle("-fx-background-color: blue");
        stackPaneRight.setPrefSize(400, 800);

        stackPaneRight.setOnDragOver((DragEvent event)
                -> {
            if (event.getGestureSource() != target
                    && event.getDragboard().hasImage()) {
                event.acceptTransferModes(TransferMode.ANY);

            }
            event.consume();
        });

        stackPaneRight.setOnDragEntered((DragEvent event)
                -> {
            if (event.getGestureSource() != target
                    && event.getDragboard().hasImage()) {
                target.setImage(source.getImage());
            }
            event.consume();
        });

        stackPaneRight.setOnDragExited((DragEvent event)
                -> {
            if (target.getImage() != null && !event.isDropCompleted()) {
                target.setImage(null);
            }
            event.consume();
        });

        stackPaneRight.setOnDragDropped((DragEvent event)
                -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasImage()) {
                target.setImage(source.getImage());
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });

        HBox root = new HBox(stackPaneLeft, stackPaneRight);
        Scene scene = new Scene(root, 800, 800, Color.WHITE);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }
}