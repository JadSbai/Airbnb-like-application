package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

    private AccountSettingsController accountSettingsController;

    private AccountDetailsController accountDetailsController;

    MapControllerRefactored mapController;


    public AccountStageController(ControllerComponents controllerComponents, Stage accountStage, MapControllerRefactored mapController)
    {
        super(controllerComponents, accountStage);
        this.controllerComponents = controllerComponents;
        this.mapController = mapController;
    }

    public void initialize()
    {
        AccountCircles.getInstance().getAccountCircles().add(accountStageProfileCircle);
    }

    public void initializeControllers() throws IOException
    {
        accountSettingsController = new AccountSettingsController(controllerComponents, getAccountStage(), null, accountStageProfileCircle);
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
