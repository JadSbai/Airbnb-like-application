package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
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
    private Button leftButton, rightButton;
    @FXML
    private Label currentPriceRangeLabel;
    @FXML
    private BorderPane accountBar;

    private Pane welcomeRoot, mapRoot, statisticsRoot;

    private AccountController accountController;
    private MapController mapController;



    public void initialize(Pane mainRoot) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("welcome.fxml"));
        welcomeRoot = loader.load();
        WelcomeController welcomeController = loader.getController();
        welcomeController.initialize(leftButton, rightButton, currentPriceRangeLabel, this);

        AirbnbDataLoader dataLoader = new AirbnbDataLoader();

        loader = new FXMLLoader(getClass().getResource("Map.fxml"));
        mapRoot = loader.load();
        mapController = loader.getController();
        mapController.initialize(welcomeController, dataLoader);

        loader = new FXMLLoader(getClass().getResource("statistics.fxml"));
        statisticsRoot = loader.load();
        StatisticsController statisticsController = loader.getController();
        statisticsController.initialize(dataLoader);

        loader = new FXMLLoader(getClass().getResource("accountPopUpMenu.fxml"));
        VBox popUpRoot = loader.load();
        accountController = loader.getController();
        mainRoot.getChildren().add(popUpRoot);

        loader = new FXMLLoader(getClass().getResource("signed_out.fxml"));
        loader.setController(accountController);
        Pane signedOutBar = loader.load();
        accountController.initialize(this, signedOutBar, mapController);
        accountBar.setRight(accountController.getSignedOutBar());

        mainPane.setCenter(welcomeRoot);
    }

    @FXML
    private void leftButtonAction(ActionEvent e) {
        if(mainPane.getCenter() == welcomeRoot){
            mainPane.setCenter(statisticsRoot);
        }
        else if(mainPane.getCenter() == mapRoot){
            mainPane.setCenter(welcomeRoot);
        }
        else{
        mapController.setColor();
        mainPane.setCenter(mapRoot);
        if(accountController.getWelcomeController().isNewSearch()){
            mapController.setColor();
            accountController.getMapController().closeAllMapStages();
            }
        }
    }

    @FXML
    public void rightButtonAction(ActionEvent e) {
        if(mainPane.getCenter() == welcomeRoot){
            mapController.setColor();
            mainPane.setCenter(mapRoot);
            if(accountController.getWelcomeController().isNewSearch()){
                mapController.setColor();
                accountController.getMapController().closeAllMapStages();
            }
        }
        else if(mainPane.getCenter() == mapRoot){
            mainPane.setCenter(statisticsRoot);
        }
        else{
            mainPane.setCenter(welcomeRoot);
        }
    }

    public BorderPane getAccountBar()
    {
        return accountBar;
    }

    public AccountController getAccountController() {
        return accountController;
    }
}
