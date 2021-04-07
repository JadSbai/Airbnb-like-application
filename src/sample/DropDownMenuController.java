package sample;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;

public class DropDownMenuController extends AccountController
{
    /**
     * Common instance of ControllerComponents held by all classes containing common elements like the account.
     */
    private ControllerComponents controllerComponents;

    /**
    *   Label that displays the accounts's current username
    */
    @FXML
    private Label accountUsernameLabel;

    /**
     * Profile circle situated in the account's drop down menu containing the profile picture
     */
    @FXML
    private Circle profileCircle2;

    @FXML
    private Pane dropDownPane;

    /**
    * An AccountStageController object
    */
    private AccountStageController accountStageController;


    public DropDownMenuController(ControllerComponents controllerComponents, Stage accountStage, AccountStageController accountStageController) {
        super(controllerComponents, accountStage);
        this.controllerComponents = controllerComponents;
        this.accountStageController = accountStageController;
    }


    public void initialize() throws IOException
    {
        formatDropDownMenu();
        AccountComponents.getInstance().getAccountCircles().add(profileCircle2);
        AccountComponents.getInstance().getUsernameLabels().add(accountUsernameLabel);
    }

    @FXML
    public void openAccountSettings() throws IOException
    {
        accountStageController.goToAccountSettings();

        dropDownPane.setVisible(false);
        if(getAccountStage().isShowing()){
            getAccountStage().close();
        }
        getAccountStage().show();

    }

    /**
    *   Opens the accountStage and sets the accountDetails pane to the center of
    * the accountPane.
    * @throws IOException {@link IOException} in some circumstance
    */
    @FXML
    public void openAccountDetails() throws IOException
    {
        accountStageController.goToAccountDetails();
        dropDownPane.setVisible(false);
        if(getAccountStage().isShowing()){
            getAccountStage().close();
        }
        getAccountStage().show();
    }

    /**
     * This method sets a comfortable margin between the account's drop-down menu and the main frame border
     */
    public void formatDropDownMenu() {
        StackPane.setMargin(getDropDownPane(), new Insets(70,20,0,0));
    }

    /**
     * Label situated in the account's drop down menu (i.e., the subPane field) that displays the account's username
     * @return accountUsernameLabel
     */
    public Label getAccountUsernameLabel() {
        return accountUsernameLabel;
    }
    /**
     * Pane representing the account's drop down menu
     * @return dropDownPane
     */
    public Pane getDropDownPane() {
        return dropDownPane;
    }
}
