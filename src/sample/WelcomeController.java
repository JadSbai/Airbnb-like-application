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
    private ComboBox<Integer> minimumPrice, maximumPrice;

    @FXML
    private Button searchButton;

    private int minPrice, maxPrice;

    private Button leftArrow, rightArrow;

    private Label priceLabel;

    @FXML
    private VBox welcomeVBox;

    @FXML
    private StackPane stackPane;

    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 500;

    private boolean isSearched;
    private boolean isNewSearch;

    private MainController mainController;


    public void initialize(Button leftArrow, Button rightArrow, Label priceLabel, MainController mainController) {

        this.leftArrow = leftArrow;
        this.rightArrow = rightArrow;
        this.priceLabel = priceLabel;

        this.mainController = mainController;

        isSearched = false;

        ArrayList<Integer> priceRange = getPriceRange();
        minimumPrice.setItems(FXCollections.observableArrayList(priceRange));
        maximumPrice.setItems(FXCollections.observableArrayList(priceRange));
    }

    private static ArrayList<Integer> getPriceRange() {
        ArrayList<Integer> priceRange = new ArrayList<>();
        for (int i = MIN_VALUE; i <= MAX_VALUE; i = (int) (i + ((MAX_VALUE - MIN_VALUE)) * 0.1)) {
            priceRange.add(i);
        }
        return priceRange;
    }

    @FXML
    private void searchAction(ActionEvent e) {

        boolean valid = (getIntFromBox(minimumPrice) && getIntFromBox(maximumPrice));
        boolean isNewPrice = false;

        if (valid)
        {
            int temp1 = minPrice;
            int temp2 = maxPrice;
            minPrice = minimumPrice.getValue();
            maxPrice = maximumPrice.getValue();

            if(temp1 != minPrice || temp2 != maxPrice){
                isNewPrice = true;
            }
            else{
                invalidOptions("Please input an new price range before searching", "Same price range !");
            }
        }
        if (valid && maxPrice > minPrice && isNewPrice)
        {
            priceLabel.setText("Price range: " + minPrice + "-" + maxPrice);
            isNewSearch = true;
            if(!isSearched){
                rightArrow.setDisable(false);
                leftArrow.setDisable(false);
                setSearched(true);
            }
            mainController.rightButtonAction(e);

        }
        else if(valid && isNewPrice)
        {
            invalidOptions("Minimum price may not exceed or equal maximum price.", "Invalid price range!");
        }
        else if(!valid)
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

    public boolean isSearched() {
        return isSearched;
    }

    public void setSearched(boolean searched) {
        isSearched = searched;
    }


    public boolean isNewSearch() {
        return isNewSearch;
    }

    public void setNewSearch(boolean newSearch) {
        isNewSearch = newSearch;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }
}

