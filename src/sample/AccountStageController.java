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
    private ControllerComponents controllerComponents;
    @FXML
    private BorderPane accountPanel;

    private Pane accountSettingsPanel;

    private Pane accountDetailsPanel;

    @FXML
    private Circle accountStageProfileCircle;
    @FXML
    private Label currentUsernameLabel;

    private AccountSettingsController accountSettingsController;

    private AccountDetailsController accountDetailsController;

    MapController mapController;


    public AccountStageController(ControllerComponents controllerComponents, Stage accountStage, MapController mapController)
    {
        super(controllerComponents, accountStage);
        this.controllerComponents = controllerComponents;
        this.mapController = mapController;
    }

    public void initialize()
    {
        AccountComponents.getInstance().getAccountCircles().add(accountStageProfileCircle);
        AccountComponents.getInstance().getUsernameLabels().add(currentUsernameLabel);
    }

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

    // test comment
    @FXML
    public void goToAccountSettings()
    {
        accountPanel.setCenter(accountSettingsPanel);
        accountSettingsController.resetAccountSettings();
        getAccountStage().sizeToScene();
        getAccountStage().show();
    }

    @FXML
    public void goToAccountDetails() throws IOException {
        accountPanel.setCenter(accountDetailsPanel);
        getAccountStage().sizeToScene();
        accountDetailsController.reloadFavouritesAndBookings();
    }

    @FXML
    protected void closeAllAccountWindows()
    {
        super.closeAllAccountWindows();
    }

    public BorderPane getAccountPanel() {
        return accountPanel;
    }

    public Pane getAccountSettingsPanel() {
        return accountSettingsPanel;
    }

    public Pane getAccountDetailsPanel() {
        return accountDetailsPanel;
    }

    public AccountDetailsController getAccountDetailsController(){
        return accountDetailsController;
    }

}
