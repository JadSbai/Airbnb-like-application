package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

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
    private Circle profileCircle;

    private Account account;

    @FXML
    private BorderPane accountBar;


    private Pane signedOutBar;
    private Pane signedInBar;
    private Pane signInWindow;

    private MapController mapController;

    private Pane welcomeRoot;
    private Pane mapRoot;
    //private Pane statisticsRoot;

    private AccountController accountController;

//TODO; looping arraylist with buttons

    public void initialize() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("welcome.fxml"));
        welcomeRoot = loader.load();
        mainPane.setCenter(welcomeRoot);
        WelcomeController welcomeController = loader.getController();
        loader = new FXMLLoader(getClass().getResource("map.fxml"));
        mapRoot = loader.load();
        //loader = new FXMLLoader(getClass().getResource("statistics.fxml"));
        //statisticsRoot = loader.load();
        welcomeController.initialize(leftButton, rightButton, currentPriceRangeLabel);

        signedInBar = FXMLLoader.load(getClass().getResource("signed_in.fxml"));
        signedOutBar = FXMLLoader.load(getClass().getResource("signed_out.fxml"));
        accountBar.setRight(signedOutBar);

        

    }

    @FXML
    private void leftButtonAction(ActionEvent e){
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
    private void rightButtonAction(ActionEvent e){
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

    private void setAccountBar()
    {
        if (account != null)
        {
            accountBar.setRight(signedInBar);
        } else
        {
            accountBar.setRight(signedOutBar);
        }
    }



}
