package visualization;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class FacultyListCell extends HBox {

    final Text credlbl = new Text("Credits: ");
    Text name;
    Text credits;
    Color color = Color.RED;
    Circle circle;

    FacultyListCell(String name, int credits) {
        this.name = new Text(name);
        this.credits = new Text(Integer.toString(credits));
        circle = new Circle(8, color);
        getChildren().addAll(circle, this.name, credlbl, this.credits);
        setSpacing(10);
        setPadding(new Insets(10));
    }


}
