package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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

    @FXML
    private BorderPane accountBar;

    private Pane welcomeRoot;
    private ScrollPane mapRoot;
    //private Pane statisticsRoot;

//TODO: looping arraylist with buttons, sign in text automatically selected so u cannot read textfield,


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

        loader = new FXMLLoader(getClass().getResource("signed_out.fxml"));
        Pane signedOutBar = loader.load();
        AccountController accountController = loader.getController();

        accountController.initialize(this, accountController, signedOutBar);

        accountBar.setRight(accountController.getSignedOutBar());


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





    public BorderPane getAccountBar() {
        return accountBar;
    }
}
