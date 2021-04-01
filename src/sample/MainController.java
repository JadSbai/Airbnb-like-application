package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

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
    @FXML
    private BorderPane accountBar;

    private Pane welcomeRoot;
    private ScrollPane mapRoot;
    //private Pane statisticsRoot;
    private AccountController accountController;



    public void initialize(Pane mainRoot) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("welcome.fxml"));

        welcomeRoot = loader.load();
        WelcomeController welcomeController = loader.getController();
        mainPane.setCenter(welcomeRoot);
        loader = new FXMLLoader(getClass().getResource("map.fxml"));

        mapRoot = loader.load();
        MapController mapController = loader.getController();
        mapController.initialize(welcomeController);
        //loader = new FXMLLoader(getClass().getResource("statistics.fxml"));
        //statisticsRoot = loader.load();
        welcomeController.initialize(leftButton, rightButton, currentPriceRangeLabel, this);

        FXMLLoader popUpLoader = new FXMLLoader(getClass().getResource("accountPopUpMenu.fxml"));
        VBox popUpRoot = popUpLoader.load();
        accountController = popUpLoader.getController();
        mainRoot.getChildren().add(popUpRoot);

        loader = new FXMLLoader(getClass().getResource("signed_out.fxml"));
        loader.setController(accountController);
        Pane signedOutBar = loader.load();





        accountController.initialize(this, signedOutBar, mapController);
        accountBar.setRight(accountController.getSignedOutBar());
    }

    @FXML
    private void leftButtonAction(ActionEvent e) throws IOException {
        if(mainPane.getCenter() == welcomeRoot){
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
    public void rightButtonAction(ActionEvent e) throws IOException {
        if(mainPane.getCenter() == welcomeRoot){
            mainPane.setCenter(mapRoot);
            if(accountController.getWelcomeController().isNewSearch()){
                accountController.getMapController().closeAllPropertyListStages();
            }
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
