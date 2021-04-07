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
    private ControllerComponents controllerComponents;

    /**
    * Button that creates a filechooser where the user can select
    * an image to set as their profile picture.
    */
    @FXML
    private Button chooseFileButton;

    /**
    * Label that gives the user feedback of any errors made when
    * trying to change their username.
    */
    @FXML
    private Label changeUsernameErrorLabel;

    /**
    * Textfields that display the current account's username and email, and
    * allows the user to change the username.
    */
    @FXML
    private TextField changeUsernameField, emailField;

    /**
     *  Label that gives the user feedback on saving their changes.
     */
    @FXML
    private Label saveFeedbackLabel;

    /**
     *  Pane where the user can change their current password.
     */
    private Pane changePasswordMenu;

    /**
     *  Images that will be loaded only if the user saves their changes via
     *  an ok button or a save button.
     */
    private Image bufferImage, bufferedBasicAvatar;

    /**
     * Pane where the user can view a grid of sample avatars and select
     * one to set as their profile picture.
     */
    private Pane chooseAvatarMenu;

    /**
     *  Preview circle that will display the avatar that the user
     *  will have as their profile picture when they save their changes.
     */
    @FXML
    private Circle changeAvatarCircle;

    /**
     *  The label that shows the path of the image the user has 
     *  currently selected from their files.
     */
    @FXML
    private Label imagePathLabel;

    /**
     *  Displays the user's current username.
     */
    private Label currentUsernameLabel;

    /**
     *  Circle that displays the user's current profile picture, or a 
     *  preview of the profile picture they select in the choose
     * avatar menu.
     */
    private Circle accountStageProfileCircle;

    /**
     *  Label that gives feedback to the user if they
     * change their password.
     */
    @FXML
    private Label passwordFeedbackLabel;

    /**
    * Stores the text that will be displayed if the user has not selected
    * an image from files to set as their profile picture. 
    */
    @FXML
    public static final String IMAGE_PATH_DEFAULT = "No file chosen";

    /**
     *  The pane that stores the user's account settings and options
     *  to change username, password and profile picture.
     */
    private Pane accountSettingsPanel;

    /**
     *  The borderpane that contains the sidebar (set to left) that stores
     *  a preview of the current account (the user's profile picture
     *  and username) and buttons to change the content of the center of the pane
     *  to the user's account settings or account details pane.
     */
    private BorderPane accountPanel;

    /**
     *  The controller that controls the components of the
     *  changePasswordMenu pane.
     */
    private ChangePasswordController changePasswordController;

    /**
     * @param controllerComponents
     * @param accountStage
     * @param accountSettingsPanel
     * @param accountStageProfileCircle
     * @param currentUsernameLabel
     * @throws IOException
     */
    public AccountSettingsController(ControllerComponents controllerComponents, Stage accountStage, Pane accountSettingsPanel, Circle accountStageProfileCircle, Label currentUsernameLabel) throws IOException {
        super(controllerComponents, accountStage);
        this.controllerComponents = controllerComponents;
        this.accountSettingsPanel = accountSettingsPanel;
        this.accountStageProfileCircle = accountStageProfileCircle;
        this.currentUsernameLabel = currentUsernameLabel;
    }


    /**
     * @throws IOException
     */
    public void initialize() throws IOException
    {
        ProfilePicturesGridController profilePicturesGridController = new ProfilePicturesGridController(controllerComponents, getAccountStage(), this);
        FXMLLoader pfpGridLoader = new FXMLLoader(getClass().getResource("ProfilePicturesGrid.fxml"));
        pfpGridLoader.setController(profilePicturesGridController);
        chooseAvatarMenu = pfpGridLoader.load();

        changePasswordController = new ChangePasswordController(controllerComponents, getAccountStage(), this);
        FXMLLoader changePasswordLoader = new FXMLLoader(getClass().getResource("ChangePassword.fxml"));
        changePasswordLoader.setController(changePasswordController);
        changePasswordMenu = changePasswordLoader.load();

        chooseFileButton.setOnAction(e-> {
            try {
                chooseFile(getAccountStage());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        AccountComponents.getInstance().getAccountCircles().add(changeAvatarCircle);

        Image cursor = new Image("/sample/crossout.png");
        emailField.setCursor(new ImageCursor(cursor, cursor.getWidth()/2, cursor.getHeight()/2));
    }

    /**
     * Opens a file chooser where the user can select an image to set as their
     * profile picture.
     * @param stage The stage where the file chooser should be attached to.
     * @throws IOException {@link IOException} in some circumstance
     */
    @FXML
    private void chooseFile(Stage stage) throws IOException {
        FileChooser filechooser = new FileChooser();
        File file = filechooser.showOpenDialog(stage);
        if (file != null)
        {
            openFile(file);
        }
    }

    /**
     * Opens a pane where the user can view a grid of sample avatrs to
     * select and set as their profile picture.
     */
    @FXML
    private void chooseBasicPfpAction()
    {
        accountPanel.setCenter(getChooseAvatarMenu());
        getAccountStage().sizeToScene();
    }

    /**
     * Opens a file and if it is of the expected file type, it sets it as the avatar circle.
     * Otherwise, it shows an error window.
     * @param file The file the user has opened from the file chooser.
     */
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

    /**
     * Displays an error window if the inputted file type is not of the expected type
     */
    private void showInvalidFileFormatError()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Image Load Error");
        alert.setHeaderText(null);
        alert.setContentText("The file was not recognized as an image file.");

        alert.showAndWait();
    }

    /**
     * Saves the settings made and updates any elements 
     * that have been changed.
     */
    @FXML
    private void saveSettings()
    {
        boolean changes = false;
        String newUsername = changeUsernameField.getText();

        if (checkChangeUsernameValidity(newUsername) && checkUsername(newUsername, changeUsernameErrorLabel)) {
            changeUsername(newUsername);
            changeUsernameField.setPromptText(newUsername);
            updateProfileLabels();
            changes = true;
        }
        if (bufferImage != null)
        {
            controllerComponents.getAccount().setProfilePicture(bufferImage);
            updateProfilePictures();
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

    /**
     * Changes the username to a new one
     * @param username
     */
    public void changeUsername(String username)
    {
        controllerComponents.getAccount().setUsername(username);
        currentUsernameLabel.setText(username);
    }

    /**
     * Checks whether the inputted username is valid.
     * @param newUsername The username the user has inputted.
     * @param label The error label where feedback of how the username is
     * not valid, is displayed.
     * @return true if the username is valid, false otherwise
     */
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

    /**
     * Check the changeUsernameField to see if the user has tried to change it.
     * If the field is equal empty or equal to current username, the user has not tried
     * to change their username.
     * @param newUsername
     * @return true if the user has entered a new username, false otherwise
     */
    private boolean checkChangeUsernameValidity(String newUsername)
    {
        return (!newUsername.equals("") && !newUsername.equals(controllerComponents.getAccount().getUsername()));
    }

    /**
     * The menu action for changing the password
     */
    @FXML
    private void changePasswordMenuAction()
    {
        changePasswordController.resetPasswordFields();
        accountPanel.setCenter(changePasswordMenu);
    }

    /**
     * Resets all elements relating to the user's account, so that they
     * are correctly set to the data for this user. i.e. All circles are
     * set to contain the user's profile picture, all labels are set to
     * the user's account username.
     *
     * Also resets all feedback fields to their default.
     */
    public void resetAccountSettings()
     {
         getSaveFeedbackLabel().setText("");
         getChangeUsernameErrorLabel().setText("");
         updateProfilePictures();
         updateProfileLabels();
         setBufferImage(null);
         getImagePathLabel().setText(IMAGE_PATH_DEFAULT);
         changePasswordController.resetPasswordFields();
         passwordFeedbackLabel.setText("");

         changeUsernameField.setText("");
         changeUsernameField.setPromptText(controllerComponents.getAccount().getUsername());
         emailField.setPromptText(controllerComponents.getAccount().getEmail());
         currentUsernameLabel.setText(controllerComponents.getAccount().getUsername());
     }

    /**
*     * @return the password feedback level
     */
    public Label getPasswordFeedbackLabel() {
        return passwordFeedbackLabel;
    }

    /**
     * Returns the avatar that the user has selected from the grid of sample
     * profile pictures.
     * @return the buffered basic avatar
     */
    public Image getBufferedBasicAvatar() {
        return bufferedBasicAvatar;
    }

    /**
     * @return the choose avatar menu
     */
    public Pane getChooseAvatarMenu() {
        return chooseAvatarMenu;
    }

    /**
     * @return the change avatar Circle
     */
    public Circle getChangeAvatarCircle() {
        return changeAvatarCircle;
    }

    /**
     * Sets the image that should be set as the user's profile picture when
     * the user saves their changes.
     * sets the current buffer image
     * @param bufferImage
     */
    public void setBufferImage(Image bufferImage) {
        this.bufferImage = bufferImage;
    }

    /**
     * Stores the image of the avatar that the user has selected from the
     * profile picture grid pane.
     *  Sets the current buffered basic avatar
     * @param bufferedBasicAvatar
     */
    public void setBufferedBasicAvatar(Image bufferedBasicAvatar) {
        this.bufferedBasicAvatar = bufferedBasicAvatar;
    }

    /**
     * @return the image path label
     */
    public Label getImagePathLabel() {
        return imagePathLabel;
    }

    /**
     * @return the change username error label
     */
    public Label getChangeUsernameErrorLabel() {
        return changeUsernameErrorLabel;
    }

    /**
     * @return the save feedback label
     */
    public Label getSaveFeedbackLabel() {
        return saveFeedbackLabel;
    }

    /**
     * @param accountPanel
     */
    public void setAccountPanel(BorderPane accountPanel) {
        this.accountPanel = accountPanel;
    }

    /**
     * @return the account panel
     */
    public BorderPane getAccountPanel() {
        return accountPanel;
    }

    /**
     * @return the account settings panel
     */
    public Pane getAccountSettingsPanel() {
        return accountSettingsPanel;
    }

    /**
     * Returns the circle that contains the user's
     * current profile picture.
     * @return the accountStageProfileCircle
     */
    public Circle getAccountStageProfileCircle()
    {
        return accountStageProfileCircle;
    }

    /**
     * Sets the pane that displays the user's account settings.
     * @param accountSettingsPanel
     */
    public void setAccountSettingsPanel(Pane accountSettingsPanel)
    {
        this.accountSettingsPanel = accountSettingsPanel;
    }
}
