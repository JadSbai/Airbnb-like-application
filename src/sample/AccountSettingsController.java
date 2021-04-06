package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
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

public class AccountSettingsController extends AccountController
{

    @FXML
    private Button chooseFileButton;

    @FXML
    private Label changeUsernameErrorLabel;

    @FXML
    private TextField changeUsernameField;

    @FXML
    private TextField emailField;

    @FXML
    private Label saveFeedbackLabel;

    @FXML private Label currentUsernameLabel;

    private Pane changePasswordMenu;

    private Image bufferImage;

    private Image bufferedBasicAvatar;

    private Pane chooseAvatarMenu;

    @FXML
    private Circle changeAvatarCircle;

    @FXML
    private Label imagePathLabel;

    @FXML
    private TextField currentPasswordField;

    @FXML
    private TextField newPasswordField;

    @FXML
    private TextField confirmPasswordField;

    @FXML
    private Label changePasswordErrorField;

    @FXML
    private Label passwordFeedbackLabel;

    @FXML
    public static final String IMAGE_PATH_DEFAULT = "No file chosen";


    private Pane accountSettingsPanel;

    private BorderPane accountPanel;

    public AccountSettingsController(Account account, Stage accountStage, Pane accountSettingsPanel) throws IOException {
        super(account, accountStage);
        this.accountSettingsPanel = accountSettingsPanel;
    }


    public void initialize() throws IOException
    {
        ProfilePicturesGridController profilePicturesGridController = new ProfilePicturesGridController(getAccount(), getAccountStage(), this);
        FXMLLoader pfpGridLoader = new FXMLLoader(getClass().getResource("ProfilePicturesGrid.fxml"));
        pfpGridLoader.setController(profilePicturesGridController);
        chooseAvatarMenu = pfpGridLoader.load();

        //ChangePasswordController changePasswordController = new ChangePasswordController(getAccountStage());
        //FXMLLoader changePasswordLoader = new FXMLLoader(getClass().getResource("ChangePassword.fxml"));
        //changePasswordMenu = changePasswordLoader.load();

        chooseFileButton.setOnAction(e-> {
            try {
                chooseFile(getAccountStage());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        Image cursor = new Image("/sample/crossout.png");
        emailField.setCursor(new ImageCursor(cursor, cursor.getWidth()/2, cursor.getHeight()/2));
    }

    @FXML
    private void chooseFile(Stage stage) throws IOException {
        FileChooser filechooser = new FileChooser();
        File file = filechooser.showOpenDialog(stage);
        // Put a try catch statement to handle the exception...
        if (file != null)
        {
            openFile(file);
        }
    }

    @FXML
    private void chooseBasicPfpAction()
    {
        accountPanel.setCenter(getChooseAvatarMenu());
        getAccountStage().sizeToScene();
    }

    private void openFile(File file)
    {
        String imagePath = file.toURI().toString();
        Image image = new Image(imagePath);
        if ( image == null || image.isError())
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

        if (checkChangeUsernameValidity(newUsername) && checkUsername(newUsername, changeUsernameErrorLabel)) {
            changeUsername(newUsername);
            changeUsernameField.setPromptText(newUsername);
            currentUsernameLabel.setText(newUsername);
            changes = true;
        }
        if (bufferImage != null)
        {
            getAccount().setProfilePicture(bufferImage);
            //setProfileCircles();
            setCircles();
            changes = true;
        }
        bufferImage = null;
        if (changes)
        {
            saveFeedbackLabel.setText("Changes saved.");
        } else
        {
            saveFeedbackLabel.setText("You have not made any changes.");
        }
    }

    public void changeUsername(String username)
    {
        getAccount().setUsername(username);
        //setAccountUsername(username);
    }

    private boolean checkUsername(String newUsername, Label label)
    {
        label.setText("");

        if(newUsername.length() == 0){
            label.setText("Please choose a username ");
            return false;
        }
        for(Account account : getListOfAccounts()){
            if(newUsername.equals(account.getUsername())){
                label.setText("This field is already taken by another account. Please choose another username");
                return false;
            }
        }
        return true;
    }

    private boolean checkChangeUsernameValidity(String newUsername)
    {
        return (!newUsername.equals("") && !newUsername.equals(getAccount().getUsername()));
    }

    @FXML
    private void changePasswordMenuAction()
    {
        resetPasswordFields();
        accountPanel.setCenter(changePasswordMenu);
    }

    private void resetPasswordFields()
    {
        currentPasswordField.setText("");
        newPasswordField.setText("");
//         confirmPasswordField.setText("");
        changePasswordErrorField.setText("");
    }

    private void setCircles()
    {
        //profileCircle.setFill(new ImagePattern(getAccount().getProfilePicture()));
        changeAvatarCircle.setFill(new ImagePattern(getAccount().getProfilePicture()));
    }

    public void resetAccountSettings()
     {
         getSaveFeedbackLabel().setText("");
         getChangePasswordErrorField().setText("");
         getChangeUsernameErrorLabel().setText("");
         getChangeAvatarCircle().setFill(new ImagePattern(getAccount().getProfilePicture()));
         setBufferImage(null);
         getImagePathLabel().setText(IMAGE_PATH_DEFAULT);
     }

    /**
     * This method sets both the profile pictures to the one specified
     */
    public void setProfileCircles() {
        //profileCircle.setFill(new ImagePattern(getAccount().getProfilePicture()));
       //profileCircle2.setFill(new ImagePattern(getAccount().getProfilePicture()));
    }


    public TextField getCurrentPasswordField() {
        return currentPasswordField;
    }

    public TextField getNewPasswordField() {
        return newPasswordField;
    }

    public TextField getConfirmPasswordField() {
        return confirmPasswordField;
    }

    public Label getChangePasswordErrorField() {
        return changePasswordErrorField;
    }

    public Label getPasswordFeedbackLabel() {
        return passwordFeedbackLabel;
    }

    public Image getBufferImage() {
        return bufferImage;
    }

    public Image getBufferedBasicAvatar() {
        return bufferedBasicAvatar;
    }

    public Pane getChooseAvatarMenu() {
        return chooseAvatarMenu;
    }

    public Circle getChangeAvatarCircle() {
        return changeAvatarCircle;
    }

//     public Circle getProfileCircle() {
//         return profileCircle;
//     }

    public void setBufferImage(Image bufferImage) {
        this.bufferImage = bufferImage;
    }

    public void setBufferedBasicAvatar(Image bufferedBasicAvatar) {
        this.bufferedBasicAvatar = bufferedBasicAvatar;
    }

    public Label getImagePathLabel() {
        return imagePathLabel;
    }

    public Label getChangeUsernameErrorLabel() {
        return changeUsernameErrorLabel;
    }

    public TextField getChangeUsernameField() {
        return changeUsernameField;
    }

    public Label getSaveFeedbackLabel() {
        return saveFeedbackLabel;
    }

    public void setAccountPanel(BorderPane accountPanel) {
        this.accountPanel = accountPanel;
    }

    public BorderPane getAccountPanel() {
        return accountPanel;
    }

    public Pane getAccountSettingsPanel() {
        return accountSettingsPanel;
    }
}
