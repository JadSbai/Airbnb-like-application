package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.ImageCursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class AccountPanelController{

    private Account currentAccount;

    @FXML
    private Circle profileCircle;

    @FXML
    private Circle changeAvatarCircle;

    @FXML
    private Pane accountSettings;

    @FXML
    private Pane accountDetails;

    @FXML
    private Button chooseFileButton;

    @FXML
    private Label changeUsernameErrorLabel;

    @FXML
    private Label imagePathLabel;

    @FXML
    private TextField changeUsernameField;

    @FXML
    private TextField emailField;

    @FXML
    private Label saveResponseLabel;

    @FXML
    private TilePane pfpGrid;


    private Image bufferImage;

    private Image bufferedBasicAvatar;

    private Stage stage;

    private BorderPane accountPanel;

    private Pane chooseAvatarMenu;

    private AccountController accountController;

    @FXML
    public static final String IMAGE_PATH_DEFAULT = "No file chosen";

    public void initialize(AccountPanelController apc, AccountController accountController, BorderPane accountPanel) throws IOException {

        FXMLLoader accountSettingsLoader = new FXMLLoader(getClass().getResource("AccountSettings.fxml"));
        accountSettingsLoader.setController(apc);
        accountSettings = accountSettingsLoader.load();

        FXMLLoader pfpGridLoader = new FXMLLoader(getClass().getResource("pfpgrid.fxml"));
        pfpGridLoader.setController(apc);
        chooseAvatarMenu = pfpGridLoader.load();

        pfpGrid.setVgap(20);
        pfpGrid.setHgap(20);

//        FXMLLoader accountDetailsLoader = new FXMLLoader(getClass().getResource("AccountDetails.fxml"));
//        accountDetailsLoader.setController(this);
        this.accountController = accountController;
        this.accountPanel = accountPanel;

        chooseFileButton.setOnAction(e-> chooseFile(getStage()));

        Image cursor = new Image("/sample/crossout.png");
        emailField.setCursor(new ImageCursor(cursor, cursor.getWidth()/2, cursor.getHeight()/2));

        loadBasicAvatars();
    }

    public void reset()
    {
        saveResponseLabel.setText("");
        changeUsernameField.setText("");
        changeUsernameErrorLabel.setText("");
        changeAvatarCircle.setFill(new ImagePattern(currentAccount.getProfilePicture()));
        bufferImage = null;
        imagePathLabel.setText(IMAGE_PATH_DEFAULT);
    }

    private Stage getStage() {
        return stage;
    }

    protected void setCurrentAccount(Account account)
    {
        currentAccount = account;
        setCircles();
        changeUsernameField.setPromptText(currentAccount.getUsername());
        emailField.setPromptText(currentAccount.getEmail());
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

    public Pane getAccountDetailsPane() {
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
        boolean changes = false;
        String newUsername = changeUsernameField.getText();

        if (checkChangeUsernameValidity(newUsername) && accountController.checkUsername(newUsername, changeUsernameErrorLabel)) {
            accountController.changeUsername(newUsername);
            changeUsernameField.setPromptText(newUsername);
            changes = true;
        }
        if (bufferImage != null)
        {
            currentAccount.setProfilePicture(bufferImage);
            accountController.setProfileCircles();
            setCircles();
            changes = true;
        }
        bufferImage = null;
        if (changes)
        {
            saveResponseLabel.setText("Changes saved.");
        } else
        {
            saveResponseLabel.setText("You have not made any changes.");
        }
    }

    private boolean checkChangeUsernameValidity(String newUsername)
    {
        return (!newUsername.equals("") && !newUsername.equals(currentAccount.getUsername()));
    }

    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    @FXML
    private void chooseBasicPfpAction()
    {
        accountPanel.setCenter(chooseAvatarMenu);
    }

    @FXML
    private void backButtonAction()
    {
        backToAccountSettings();
    }

    @FXML
    private void okButtonAction()
    {
        bufferImage = bufferedBasicAvatar;
        changeAvatarCircle.setFill(new ImagePattern(bufferImage));
        imagePathLabel.setText(IMAGE_PATH_DEFAULT);

        backToAccountSettings();

    }

    private void backToAccountSettings()
    {
        profileCircle.setFill(new ImagePattern(currentAccount.getProfilePicture()));
        bufferedBasicAvatar = null;
        accountPanel.setCenter(accountSettings);
    }


    @FXML
    private void loadBasicAvatars() {

        File dir = new File("src/sample/pfp/");
        File[] directoryList = dir.listFiles();

        if (directoryList != null) {
            for (File file : directoryList) {
                String imagePath = file.toURI().toString();
                Image im = new Image(imagePath);
               if (im != null && !im.isError())
                {
                    Circle avatarPreview = new Circle(50);
                    avatarPreview.setFill(new ImagePattern(im));
                    avatarPreview.setOnMouseClicked(e-> {
                        if (e.getButton() == MouseButton.PRIMARY)
                        {
                            selectBasicAvatar(im);
                        }
                    });
                    pfpGrid.getChildren().add(avatarPreview);
                }
            }
        }
    }

    private void selectBasicAvatar(Image image)
    {
        bufferedBasicAvatar = image;
        profileCircle.setFill(new ImagePattern(bufferedBasicAvatar));
    }

}
