package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AccountPanelController{

    private Account currentAccount;

    @FXML
    private Circle profileCircle;

    @FXML
    private Circle changeAvatarCircle;

    @FXML
    private Pane accountSettings;

    @FXML
    private SplitPane accountDetails;

    @FXML
    private Button chooseFileButton;

    @FXML
    private Label changeUsernameErrorLabel;

    @FXML
    private Label imagePathLabel;

    @FXML
    private TextField changeUsernameField;

    @FXML
    private ListView<BorderPane> listOfBookings;

    @FXML
    private ListView<Pane> listOfFavourites;

    private Image bufferImage;

    private Stage stage;

    private AccountController accountController;


    public void initialize(AccountPanelController apc, AccountController accountController) throws IOException
    {
        FXMLLoader accountSettingsLoader = new FXMLLoader(getClass().getResource("AccountSettings.fxml"));
        accountSettingsLoader.setController(apc);
        accountSettings = accountSettingsLoader.load();

        FXMLLoader accountDetailsLoader = new FXMLLoader(getClass().getResource("AccountDetails.fxml"));
        accountDetailsLoader.setController(apc);
        accountDetails = accountDetailsLoader.load();

        this.accountController = accountController;

        chooseFileButton.setOnAction(e-> chooseFile(getStage()));

    }

    private Stage getStage() {
        return stage;
    }

    protected void setCurrentAccount(Account account)
    {
        currentAccount = account;
        setCircles();
    }

    protected Account getCurrentAccount()
    {
        return currentAccount;
    }

    private void setCircles()
    {
        profileCircle.setFill(new ImagePattern(currentAccount.getProfilePicture()));
        changeAvatarCircle.setFill(new ImagePattern(currentAccount.getProfilePicture()));
    }

    public SplitPane getAccountDetailsPane() {
        return accountDetails;
    }

    public Pane getAccountSettingsPane() {
        return accountSettings;
    }

    @FXML
    private void chooseFile(Stage stage)
    {
        FileChooser filechooser = new FileChooser();
        File file = filechooser.showOpenDialog(stage);
        if (file != null)
        {
            openFile(file);
        }
    }

    private void openFile(File file)
    {
        String imagePath = file.toURI().toString();
        Image image = new Image(imagePath);
        if (image.isError() || image == null)
        {
            showInvalidFileFormatError();
        } else
        {
            bufferImage = image;
            changeAvatarCircle.setFill(new ImagePattern(image));
            imagePathLabel.setText(imagePath);
        }
    }

    private void showInvalidFileFormatError()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Image Load Error");
        alert.setHeaderText(null);
        alert.setContentText("The file was not recognized as an image file.");

        alert.showAndWait();
    }

    @FXML
    private void saveSettings()
    {
        String newUsername = changeUsernameField.getText();
        if (accountController.checkUsername(newUsername, changeUsernameErrorLabel))accountController.changeUsername(newUsername);
        if (bufferImage != null)
        {
            currentAccount.setProfilePicture(bufferImage);
            accountController.setProfileCircles();
            setCircles();
        }
        bufferImage = null;
    }

    public void loadFavourites()
    {
        listOfFavourites.setItems(currentAccount.getListViewOfFavourites().getItems());
    }

    @FXML
    private void accountSettingsAction()
    {
        BorderPane accountPanel = accountController.getAccountPanel();
        accountPanel.setCenter(accountSettings);
    }

    @FXML
    private void accountDetailsAction()
    {
        loadFavourites();
        BorderPane accountPanel = accountController.getAccountPanel();
        accountPanel.setCenter(accountDetails);
    }



    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

}
