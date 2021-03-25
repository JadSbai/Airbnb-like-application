package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Polygon;

import static sample.Controller.MAX_VALUE;

public class MapController {


    private GridPane map;

    public MapController(){}


    public void initialize(){

    }

    public GridPane createHoneyComb(int rows, int columns, double size) {
        double[] points = new double[12];
        for (int i = 0; i < 12; i += 2) {
            double angle = Math.PI * (0.5 + i / 6d);
            points[i] = Math.cos(angle);
            points[i + 1] = Math.sin(angle);
        }
        Polygon polygon = new Polygon(points);

        GridPane result = new GridPane();
        RowConstraints rc1 = new RowConstraints(size / 4);
        rc1.setFillHeight(true);
        RowConstraints rc2 = new RowConstraints(size / 2);
        rc2.setFillHeight(true);

        double width = Math.sqrt(0.75) * size;
        ColumnConstraints cc = new ColumnConstraints(width/2);
        cc.setFillWidth(true);

        for (int i = 0; i < columns; i++) {
            result.getColumnConstraints().addAll(cc, cc);
        }

        for (int r = 0; r < rows; r++) {
            result.getRowConstraints().addAll(rc1, rc2);
            int offset = r % 2;
            int count = columns - offset;
            for (int c = 0; c < count; c++) {
                Button b = new Button();
                b.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
                b.setMinSize(0,0);
                b.setMaxSize(10000, 10000);
                b.setShape(polygon);
                b.setVisible(false);
                result.add(b, 2 * c + offset, 2 * r, 2, 3);
            }
        }
        result.getRowConstraints().add(rc1);
        result.setPrefSize(1000, 1000);
        result.setMinSize(0,0);
        result.setMaxSize(10000, 10000);
        return result;
    }
}
