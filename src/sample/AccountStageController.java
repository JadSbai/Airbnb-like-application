package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class AccountStageController extends AccountController
{
    @FXML
    private BorderPane accountPanel;

    private Pane accountSettings;

    private Pane accountDetails;


    public AccountStageController(Account account, Stage accountStage)
    {
        super(account, accountStage);
    }

    public void initialize() throws IOException
    {
        AccountSettingsController accountSettingsController = new AccountSettingsController(getAccount(), getAccountStage());
        FXMLLoader accountSettingsLoader = new FXMLLoader(getClass().getResource("AccountSettings.fxml"));
        accountSettingsLoader.setController(accountSettingsController);
        accountSettings = accountSettingsLoader.load();

        AccountSettingsController accountDetailsController = new AccountSettingsController(getAccount(), getAccountStage());
        FXMLLoader accountDetailsLoader = new FXMLLoader(getClass().getResource("AccountDetails.fxml"));
        accountSettingsLoader.setController(accountDetailsController);
        accountDetails = accountDetailsLoader.load();

        accountSettingsController.setAccountPanel(accountPanel);
    }

    @FXML
    private void goToAccountSettings()
    {
        accountPanel.setCenter(accountSettings);
        getAccountStage().sizeToScene();
        getAccountStage().show();
    }

    @FXML
    private void goToAccountDetails() throws IOException {
        //loadFavourites();
        accountPanel.setCenter(accountDetails);
        getAccountStage().sizeToScene();
    }

    public BorderPane getAccountPanel() {
        return accountPanel;
    }

    public Pane getAccountSettings() {
        return accountSettings;
    }

    public Pane getAccountDetails() {
        return accountDetails;
    }
}
