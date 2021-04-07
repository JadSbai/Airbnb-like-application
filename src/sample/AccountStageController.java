package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;

public class AccountStageController extends AccountController
{
    /**
     * Common instance of ControllerComponents held by all classes containing common elements like the account.
     */
    private ControllerComponents controllerComponents;
    /**
     * Border pane that sets switchings one of its panes from settings to controller or viceversa.
     */
    @FXML
    private BorderPane accountPanel;
    /**
     * Pane for the account settings
     */
    private Pane accountSettingsPanel;
    /**
     * Pane for the account details
     */
    private Pane accountDetailsPanel;

    /**
     * Circle for the pfp
     */
    @FXML
    private Circle accountStageProfileCircle;
    /**
     * User name label
     */
    @FXML
    private Label currentUsernameLabel;
    /**
     * Instance of accountSettingsController
     */
    private AccountSettingsController accountSettingsController;
    /**
     * Instance of accountDetailsController
     */
    private AccountDetailsController accountDetailsController;
    /**
     * Instance of mapController
     */
    MapController mapController;

    /**
    *   @param controllerComponents common instance of ControllerComponents held by all classes containing common elements like the account.
    *   @param accountStage The stage where the account Panel is stored that contains
    */
    public AccountStageController(ControllerComponents controllerComponents, Stage accountStage, MapController mapController)
    {
        super(controllerComponents, accountStage);
        this.controllerComponents = controllerComponents;
        this.mapController = mapController;
    }

    /**
    *   Adds the username labels and account circles created by the FXML file
    *   to the singleton AccountComponents so they can be accessed and modified by
    *  any class.
    */
    public void initialize()
    {
        AccountComponents.getInstance().getAccountCircles().add(accountStageProfileCircle);
        AccountComponents.getInstance().getUsernameLabels().add(currentUsernameLabel);
    }

    /**
    *   Initializes child panes via FXML loaders and sets their controllers.
    */
    public void initializeControllers() throws IOException
    {
        accountSettingsController = new AccountSettingsController(controllerComponents, getAccountStage(), null, accountStageProfileCircle, currentUsernameLabel);
        FXMLLoader accountSettingsLoader = new FXMLLoader(getClass().getResource("AccountSettings.fxml"));
        accountSettingsLoader.setController(accountSettingsController);
        accountSettingsPanel = accountSettingsLoader.load();
        accountSettingsController.setAccountSettingsPanel(accountSettingsPanel);

        accountDetailsController = new AccountDetailsController(controllerComponents);
        FXMLLoader accountDetailsLoader = new FXMLLoader(getClass().getResource("AccountDetails.fxml"));
        accountDetailsLoader.setController(accountDetailsController);
        accountDetailsPanel = accountDetailsLoader.load();

        mapController.addAccountController(accountDetailsController);

        accountSettingsController.setAccountPanel(accountPanel);
    }

    /**
    *  Sets the accountSettings pane to the center of the accountStage and
    *  resets any of the elements of the accountSettings pane to match the
    *  data of the current account.
    */
    @FXML
    public void goToAccountSettings()
    {
        accountPanel.setCenter(accountSettingsPanel);
        accountSettingsController.resetAccountSettings();
        getAccountStage().sizeToScene();
        getAccountStage().show();
    }

    /**
    * Sets the accountDetails pane to the center of the accountStage and
    * reloads the favourites properties and bookings to match any changes
    * the user has made, or to match the correct details if the user has
    * changed account.
    */
    @FXML
    public void goToAccountDetails() throws IOException {
        accountPanel.setCenter(accountDetailsPanel);
        getAccountStage().sizeToScene();
        accountDetailsController.reloadFavouritesAndBookings();
    }

    /**
    * Closes all windows that have been opened from inside the accountStage.
    */
    @FXML
    protected void closeAllAccountWindows()
    {
        super.closeAllAccountWindows();
    }

    /**
    * Returns the pane where the account settings or account details
    * are stored in.
    * @return the accountPanel
    */
    public BorderPane getAccountPanel() {
        return accountPanel;
    }

    /**
    *   Returns the panel where the user can view and change their account
    *   settings.
    * @return the account settings panel.
    */
    public Pane getAccountSettingsPanel() {
        return accountSettingsPanel;
    }

    /**
    *   Returns the panel where the user can view their favourite properties
    *   and their bookings.
    *  @return the account details panel.
    */
    public Pane getAccountDetailsPanel() {
        return accountDetailsPanel;
    }

    /**
    *   Returns the controller that controls the elements of the
    *  account details pane.
    */
    public AccountDetailsController getAccountDetailsController(){
        return accountDetailsController;
    }

}
