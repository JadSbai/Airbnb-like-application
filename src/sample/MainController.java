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

    private MapController mapController;

    private Pane welcomeRoot;
    private ScrollPane mapRoot;
    //private Pane statisticsRoot;



    public void initialize() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("welcome.fxml"));
        welcomeRoot = loader.load();
        mainPane.setCenter(welcomeRoot);
        WelcomeController welcomeController = loader.getController();
        loader = new FXMLLoader(getClass().getResource("map.fxml"));
        mapRoot = loader.load();
        this.mapController = loader.getController();
        mapController.initialize(welcomeController);
        //loader = new FXMLLoader(getClass().getResource("statistics.fxml"));
        //statisticsRoot = loader.load();
        welcomeController.initialize(leftButton, rightButton, currentPriceRangeLabel);

        loader = new FXMLLoader(getClass().getResource("signed_out.fxml"));
        Pane signedOutBar = loader.load();
        AccountController accountController = loader.getController();

        accountController.initialize(this, accountController, signedOutBar);

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
    private void rightButtonAction(ActionEvent e) throws IOException {
        if(mainPane.getCenter() == welcomeRoot){
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


}
