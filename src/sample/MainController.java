package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;


import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;

import javax.swing.text.LabelView;
import java.io.IOException;
import java.util.ArrayList;

import java.lang.reflect.Array;

public class MainController {

    private int minPrice;
    private int maxPrice;

    @FXML
    private Label currentPriceRangeLabel;

    @FXML
    private Button leftButton;
    @FXML
    private Button rightButton;

    @FXML
    private BorderPane mainPane;

    private FXMLLoader welcomeLoader;
    private FXMLLoader mapLoader;

    @FXML
    public void initialize() throws IOException
    {
         welcomeLoader = new FXMLLoader(getClass().getResource("welcome.fxml"));
         WelcomeController welcomeController = welcomeLoader.getController();
         welcomeController.initialize(leftButton, rightButton, currentPriceRangeLabel);
         //mapLoader = new FXMLLoader(getClass().getResource("map.fxml"));
         loadWelcomePanel();

        leftButton.setDisable(true);
        rightButton.setDisable(true);
    }

    public void loadWelcomePanel() throws IOException
    {
        mainPane.setCenter(welcomeLoader.load());
    }

    public void loadMapPanel() throws IOException
    {
        mainPane.setCenter(mapLoader.load());
    }

    @FXML
    private void leftButtonAction(ActionEvent e)
    {
    }

    @FXML
    private void rightButtonAction(ActionEvent e)
    {
    }
}

