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
    @FXML
    private BorderPane accountPanel;

    private Pane accountSettingsPanel;

    private Pane accountDetailsPanel;
    
    @FXML
    private Circle AccountStageProfileCircle;
    
    MapControllerRefactored mapController;


    public AccountStageController(Account account, Stage accountStage, MapControllerRefactored mapController)
    {
        super(account, accountStage);
        this.mapController = mapController;
    }

    public void initialize() throws IOException
    {
        AccountSettingsController accountSettingsController = new AccountSettingsController(getAccount(), getAccountStage(), accountSettingsPanel);
        FXMLLoader accountSettingsLoader = new FXMLLoader(getClass().getResource("AccountSettings.fxml"));
        accountSettingsLoader.setController(accountSettingsController);
        accountSettingsPanel = accountSettingsLoader.load();

        AccountDetailsController accountDetailsController = new AccountDetailsController(getAccount());
        FXMLLoader accountDetailsLoader = new FXMLLoader(getClass().getResource("AccountDetails.fxml"));
        accountSettingsLoader.setController(accountDetailsController);
        accountDetailsPanel = accountDetailsLoader.load();
        mapController.addAccountController(accountDetailsController);

        accountSettingsController.setAccountPanel(accountPanel);
    }

    @FXML
    private void goToAccountSettings()
    {
        accountPanel.setCenter(accountSettingsPanel);
        getAccountStage().sizeToScene();
        getAccountStage().show();
    }

    @FXML
    private void goToAccountDetails() throws IOException {
        //loadFavourites();
        accountPanel.setCenter(accountDetailsPanel);
        getAccountStage().sizeToScene();
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
}
