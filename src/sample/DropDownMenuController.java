package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;

public class DropDownMenuController extends AccountController
{

    @FXML
    private Label accountUsernameLabel;

    /**
     * Profile circle situated in the account's drop down menu containing the profile picture
     */
    @FXML
    private Circle profileCircle2;

    @FXML
    private Pane dropDownPane;

    private AccountStageController accountStageController;


    public DropDownMenuController(Account account, Stage accountStage, AccountStageController accountStageController) {
        super(account, accountStage);
        this.accountStageController = accountStageController;
    }


    public void initialize() throws IOException
    {
        formatDropDownMenu();
    }

    @FXML
    public void openAccountSettings() throws IOException
    {
        accountStageController.getAccountPanel().setCenter(accountStageController.getAccountSettings());
        dropDownPane.setVisible(false);
        if(getAccountStage().isShowing()){
            getAccountStage().close();
        }
        getAccountStage().show();

    }

    @FXML
    public void openAccountDetails() throws IOException {
        //loadFavourites();
        accountStageController.getAccountPanel().setCenter(accountStageController.getAccountDetails());
        dropDownPane.setVisible(false);
        if(getAccountStage().isShowing()){
            getAccountStage().close();
        }
        getAccountStage().show();
    }



    public void changeUsername(String username)
    {
        getAccount().setUsername(username);
        accountUsernameLabel.setText(username);
    }

    /**
     * This method sets both the profile pictures to the one specified
     */
   // public void setProfileCircles() {
    //    profileCircle.setFill(new ImagePattern(getAccount().getProfilePicture()));
    //    profileCircle2.setFill(new ImagePattern(getAccount().getProfilePicture()));
   // }

    /**
     * This method sets a comfortable margin between the account's drop-down menu and the main frame border
     */
    public void formatDropDownMenu() {
        StackPane.setMargin(getDropDownPane(), new Insets(70,20,0,0));
    }



    /**
     * Label situated in the account's drop down menu (i.e., the subPane field) that displays the account's username
     */
    public Label getAccountUsernameLabel() {
        return accountUsernameLabel;
    }

    public Pane getDropDownPane() {
        return dropDownPane;
    }
}
