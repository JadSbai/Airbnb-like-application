package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;


/**
 * This class is in charge of all the GUI's containers and controls related to the creation and usage of an AirBnB account.
 * It tracks the text fields' values and updates the main window's top part according to the current state of the account (signed in or signed out)
 * It also manages all secondary window's meant for signing in or creating an AirBnB account
 * It also manages the different accounts created so far in the app
 * @author Jacqueline Ilie, Liam Clark Gutiérrez, Dexter Trower and Jad Sbaï
 * @version 29/03/2021
 */
public class MainController {

    @FXML
    private BorderPane mainPane;
    @FXML
    private Button leftButton;
    @FXML
    private Button rightButton;
    @FXML
    private Label currentPriceRangeLabel;

    private MapController mapController;

    @FXML
    private BorderPane accountBar;

    private Pane welcomeRoot;
    private ScrollPane mapRoot;
    //private Pane statisticsRoot;
    private AccountController accountController;



    public void initialize() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("welcome.fxml"));
        welcomeRoot = loader.load();
        mainPane.setCenter(welcomeRoot);
        WelcomeController welcomeController = loader.getController();
        welcomeController.initialize(leftButton, rightButton, currentPriceRangeLabel);

        loader = new FXMLLoader(getClass().getResource("Map.fxml"));
        mapRoot = loader.load();
        mapController = loader.getController();
        mapController.initialize(welcomeController);

        //loader = new FXMLLoader(getClass().getResource("statistics.fxml"));
        //statisticsRoot = loader.load();

        loader = new FXMLLoader(getClass().getResource("signed_out.fxml"));
        Pane signedOutBar = loader.load();
        accountController = loader.getController();

        accountController.initialize(this, accountController, signedOutBar, mapController);
        accountBar.setRight(accountController.getSignedOutBar());
    }

    @FXML
    private void leftButtonAction(ActionEvent e) {
        if(mainPane.getCenter() == welcomeRoot){
            mapController.setColor();
            mainPane.setCenter(mapRoot);
        }
        else if(mainPane.getCenter() == mapRoot){
            mainPane.setCenter(welcomeRoot);
        }
        //else{
          //  mainPane.setCenter(statisticsRoot);
        //}

    }


    @FXML
    private void rightButtonAction(ActionEvent e) {
        if(mainPane.getCenter() == welcomeRoot){
            mapController.setColor();
            mainPane.setCenter(mapRoot);
        }
        else if(mainPane.getCenter() == mapRoot){
            mainPane.setCenter(welcomeRoot);
        }
        //else{
         //   mainPane.setCenter(statisticsRoot);
        //}
    }

    public BorderPane getAccountBar()
    {
        return accountBar;
    }

    public AccountController getAccountController() {
        return accountController;
    }
}
