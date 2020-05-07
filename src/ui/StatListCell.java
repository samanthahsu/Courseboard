package ui;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class StatListCell extends HBox {

    final Text creditLabel = new Text("Credits: ");
    Text name;
    Text credits;
    Color color = Color.RED;
    Circle circle;

    StatListCell(String name, int credits) {
        this.name = new Text(name);
        this.credits = new Text(Integer.toString(credits));
        circle = new Circle(8, color);
        getChildren().addAll(circle, this.name, creditLabel, this.credits);
        setSpacing(10);
        setPadding(new Insets(10));
    }
}