package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;

import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;

import javax.swing.text.LabelView;
import java.util.ArrayList;

import java.lang.reflect.Array;

public class Controller {

    @FXML
    private ComboBox minimumPrice;
    @FXML
    private ComboBox maximumPrice;
    @FXML
    private Button searchButton;

    private int minPrice;
    private int maxPrice;

    @FXML
    private Label currentPriceRangeLabel;

    @FXML
    private Button leftButton;
    @FXML
    private Button rightButton;

    @FXML
    private VBox welcomeVBox;

    @FXML
    private StackPane mainPane;

    public static final int MAX_VALUE = 600;
    public static final int MIN_VALUE = 100;

    @FXML
    private Button HAMM;

    //TODO styling, if statements

    public void initialize() {

        leftButton.setDisable(true);
        rightButton.setDisable(true);

        minimumPrice.setItems(FXCollections.observableArrayList(getPriceRange(MIN_VALUE, MAX_VALUE)));
        maximumPrice.setItems(FXCollections.observableArrayList(getPriceRange(MIN_VALUE, MAX_VALUE)));

    }

    public ArrayList<Integer> getPriceRange(int min, int max) {
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
            minPrice = (int) minimumPrice.getValue();
            maxPrice = (int) maximumPrice.getValue();
        }
        if (valid && maxPrice >= minPrice)
        {
            currentPriceRangeLabel.setText("Price range: " + minPrice + "-" + maxPrice);
            rightButton.setDisable(false);
            leftButton.setDisable(false);
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

    private boolean getIntFromBox(ComboBox box)
    {
        return (box.getValue() instanceof Integer);
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

            mainPane.getChildren().clear();
            mainPane.getChildren().add(infoBox);
        }

        private void okAction(ActionEvent e)
        {
            mainPane.getChildren().clear();
            mainPane.getChildren().add(welcomeVBox);
        }

    public void boroughSearch(ActionEvent event){
        String boroughAbreviation = ((Button) event.getSource()).getText();
    }
}

