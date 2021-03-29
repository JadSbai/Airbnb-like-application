package sample;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;

public class WelcomeController {

    @FXML
    private ComboBox<Integer> minimumPrice;
    @FXML
    private ComboBox<Integer> maximumPrice;

    @FXML
    private Button searchButton;

    private int minPrice;
    private int maxPrice;

    private Button rightArrow;
    private Button leftArrow;
    private Label priceLabel;

    @FXML
    private BorderPane welcomeBorderPane;

    @FXML
    private VBox welcomeVBox;

    @FXML
    private StackPane stackPane;

    private static final int MAX_VALUE = 500;
    private static final int MIN_VALUE = 0;


    public void initialize(Button leftArrow, Button rightArrow, Label priceLabel) {

        this.leftArrow = leftArrow;
        this.rightArrow = rightArrow;
        this.priceLabel = priceLabel;

        leftArrow.setDisable(true);
        rightArrow.setDisable(true);

        minimumPrice.setItems(FXCollections.observableArrayList(getPriceRange(MIN_VALUE, MAX_VALUE)));
        maximumPrice.setItems(FXCollections.observableArrayList(getPriceRange(MIN_VALUE, MAX_VALUE)));

    }

    private ArrayList<Integer> getPriceRange(int min, int max) {
        ArrayList<Integer> priceRange = new ArrayList<>();
        for (int i = min; i <= max; i = (int) (i + ((max - min)) * 0.1)) {
            priceRange.add(i);
        }
        return priceRange;
    }

    @FXML
    private void searchAction(ActionEvent e) {

        boolean valid = (getIntFromBox(minimumPrice) && getIntFromBox(maximumPrice));
        if (valid)
        {
            minPrice = minimumPrice.getValue();
            maxPrice = maximumPrice.getValue();
        }
        if (valid && maxPrice >= minPrice)
        {
            priceLabel.setText("Price range: " + minPrice + "-" + maxPrice);
            rightArrow.setDisable(false);
            leftArrow.setDisable(false);
        }
        else if(valid)
        {
            invalidOptions("Minimum price may not exceed maximum.", "Invalid price range!");
        }
        else
        {
            invalidOptions("Please input a price range first.", "Invalid price range!");
        }
    }

    private boolean getIntFromBox(ComboBox<Integer> box) {
        return (box.getValue() != null);
    }


    private void invalidOptions(String error, String errorTitle)
    {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(errorTitle);
        alert.setHeaderText(null);
        alert.setContentText(error);

        alert.showAndWait();
    }

    @FXML
    private void printInfo(String info, String title)
        {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(info);

            alert.showAndWait();
        }

    @FXML
    private void printInstructions(ActionEvent e)
    {
        Label label = new Label("Instructions...");
        Button okButton = new Button("OK");
        okButton.setOnAction(this::okAction);
        okButton.getStyleClass().add("buttons");

        VBox infoBox = new VBox(label, okButton);
        infoBox.getStyleClass().add("vboxes");

        stackPane.getChildren().clear();
        stackPane.getChildren().add(infoBox);
    }

    private void okAction(ActionEvent e)
    {
        stackPane.getChildren().clear();
        stackPane.getChildren().add(welcomeVBox);
    }

    public BorderPane getBorderPane()
    {
        return welcomeBorderPane;
    }

    public int getMaxPrice() {
        return maxPrice;
    }
    public int getMinPrice() {
        return minPrice;
    }
}

