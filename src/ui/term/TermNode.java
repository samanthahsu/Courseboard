//package visualization.term;
//
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.control.ContextMenu;
//import javafx.scene.control.MenuItem;
//import javafx.scene.input.ContextMenuEvent;
//import javafx.scene.input.MouseDragEvent;
//import javafx.scene.layout.Background;
//import javafx.scene.layout.BackgroundFill;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.VBox;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Text;
//import model.Term;
//import visualization.BoardComponent;
//import visualization.BoardManager;
//import visualization.CourseNode;
//
//public class TermNode extends BoardComponent {
//
//    private GridPane gridPane;
//    private static final int MAX_GP_WIDTH = 3;
//    private int nextAvailRow = 0;
//    private int nextAvailCol = 0;
//    private Text title;
//    private VBox vBox;
//    private Term term;
//    private BoardManager boardManager;
//
//    TermNode(String name, BoardManager boardManager) {
//        this.boardManager = boardManager;
//        term = new Term(name);
//        vBox = new VBox();
//        vBox.setAlignment(Pos.CENTER);
//        title = new Text(name);
//        gridPane = new GridPane();
//        makeDraggable();
//        setDataTransferable();
//        createContextMenu();
//        buildSelectionGlow();
//
//        vBox.getChildren().addAll(title, gridPane);
//        vBox.setPadding(new Insets(10));
//        vBox.setSpacing(10);
//        vBox.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, null, null)));
//        getChildren().add(vBox);
////        todo make the course node on top of the term ones
////        todo when a course is dragged in,
//    }
//
//    public void setTitle(String newName) {
//        term.setTitle(newName);
//        title.setText(newName);
//    }
//
//    private void createContextMenu() {
//        ContextMenu contextMenu = new ContextMenu();
//
//        TermNode thisTerm = this;
//        MenuItem deleteItem = new MenuItem("Delete");
//        deleteItem.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                boardManager.removeTerm(thisTerm);
//            }
//        });
//        MenuItem editItem = new MenuItem("Edit");
//        editItem.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                new EditTermWindow(thisTerm, boardManager);
//            }
//        });
//        contextMenu.getItems().addAll(editItem, deleteItem);
//        setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
//            @Override
//            public void handle(ContextMenuEvent event) {
//                contextMenu.show(thisTerm, event.getScreenX(), event.getScreenY());
//            }
//        });
//    }
//
//    public void setDataTransferable() {
//        TermNode thisNode = this;
//
//        setOnMouseDragEntered(new EventHandler<MouseDragEvent>() {
//            @Override
//            public void handle(MouseDragEvent event) {
//                boardManager.onDragOverTerm(thisNode, event);
//                System.out.println("im dragged");
//
//            }
//        });
//        setOnMouseDragReleased(new EventHandler<MouseDragEvent>() {
//            @Override
//            public void handle(MouseDragEvent event) {
//                System.out.println("dragrelessees");
//                boardManager.dragDropped(thisNode, event);
//                boardManager.dragDone(thisNode, event);
//            }
//        });
//    }
//
//
//    public void addToGrid(CourseNode courseNode) {
//        gridPane.add(courseNode, nextAvailCol, nextAvailRow);
//
//        if (nextAvailCol == MAX_GP_WIDTH - 1) {
//            nextAvailCol = 0;
//            nextAvailRow++;
//        } else {
//            nextAvailCol++;
//        }
//    }
//
//    public void setBorder(Color green) {
//        vBox.setBackground(new Background(new BackgroundFill(green, null, null)));
//    }
//}
