package ui;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.Stats;

public class Board extends StackPane {

    static final int SCALE_DIVISOR = 400;
    AnchorPane anchorPane = new AnchorPane();
    final static double MIN_SCALE = 0.1;
    final static double MAX_SCALE = 2;
    Delta boardPanDelta;
    Delta childDrag;
    Node draggedChild;

    BoardManager manager;
    InnerPaneEventHandler eventHandler;


    public Board(Stats stats) {
        setPrefSize(1024, 1024);
        getChildren().add(anchorPane);

//        todo: add notice of how much its been scaled
        setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                double scrollY = event.getDeltaY();
                double newScale = anchorPane.getScaleX() + scrollY / SCALE_DIVISOR;
                if (newScale >= MIN_SCALE && newScale <= MAX_SCALE) {
                    anchorPane.setScaleX(newScale);
                    anchorPane.setScaleY(newScale);
                }
            }
        });

        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                boardPanDelta = new Delta(event.getX(), event.getY());
            }
        });

        setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                anchorPane.setTranslateX(anchorPane.getTranslateX() + event.getX() - boardPanDelta.x);
                anchorPane.setTranslateY(anchorPane.getTranslateY() + event.getY() - boardPanDelta.y);
                boardPanDelta.x = event.getX();
                boardPanDelta.y = event.getY();
            }
        });

        initInnerPane();
        eventHandler = new InnerPaneEventHandler(anchorPane);
        manager = new BoardManager(anchorPane, stats);
    }

    private void initInnerPane() {
        anchorPane.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, null, null)));
    }

    private static class Delta {
        double x;
        double y;

        Delta(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}
