package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class AccountSettingsController extends AccountController
{

    @FXML
    private Circle profileCircle;

    @FXML
    private Circle changeAvatarCircle;

    @FXML
    private Button chooseFileButton;

    @FXML
    private Label imagePathLabel;

    @FXML
    private Label changeUsernameErrorLabel;

    @FXML
    private TextField changeUsernameField;

    @FXML
    private TextField emailField;

    @FXML
    private Label saveFeedbackLabel;

    @FXML
    private Label passwordFeedbackLabel;

    @FXML
    private TilePane pfpGrid;

    @FXML
    private TextField currentPasswordField;

    @FXML
    private TextField newPasswordField;

    @FXML
    private TextField confirmPasswordField;

    @FXML
    private Label changePasswordErrorField;

    @FXML private Label currentUsernameLabel;

    @FXML
    public static final String IMAGE_PATH_DEFAULT = "No file chosen";

    private Image bufferImage;

    private Image bufferedBasicAvatar;

    private Pane chooseAvatarMenu;

    private Pane changePasswordMenu;


    public void initialize() throws IOException
    {


        FXMLLoader pfpGridLoader = new FXMLLoader(getClass().getResource("ProfilePicturesGrid.fxml"));
        pfpGridLoader.setController(this);
        chooseAvatarMenu = pfpGridLoader.load();

        FXMLLoader changePasswordLoader = new FXMLLoader(getClass().getResource("ChangePassword.fxml"));
        changePasswordLoader.setController(this);
        changePasswordMenu = changePasswordLoader.load();

        pfpGrid.setVgap(20);
        pfpGrid.setHgap(20);

        chooseFileButton.setOnAction(e-> {
            try {
                chooseFile(getAccountStage());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        Image cursor = new Image("/sample/crossout.png");
        emailField.setCursor(new ImageCursor(cursor, cursor.getWidth()/2, cursor.getHeight()/2));
        loadBasicAvatars();

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
            getCurrentAccount().setProfilePicture(bufferImage);
            setProfileCircles();
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


    private boolean checkChangeUsernameValidity(String newUsername)
    {
        return (!newUsername.equals("") && !newUsername.equals(getCurrentAccount().getUsername()));
    }

    @FXML
    private void chooseBasicPfpAction()
    {
        getAccountPanel().setCenter(chooseAvatarMenu);
        getAccountStage().sizeToScene();
    }

    @FXML
    private void exitAvatarMenu()
    {
        profileCircle.setFill(new ImagePattern(getCurrentAccount().getProfilePicture()));
        bufferedBasicAvatar = null;
        getAccountPanel().setCenter(getAccountSettings());
    }

    @FXML
    private void confirmAvatarAction()
    {
        bufferImage = bufferedBasicAvatar;
        changeAvatarCircle.setFill(new ImagePattern(bufferImage));
        imagePathLabel.setText(IMAGE_PATH_DEFAULT);

        exitAvatarMenu();
    }

    @FXML
    private void changePasswordMenuAction()
    {
        resetPasswordFields();
        getAccountPanel().setCenter(changePasswordMenu);
    }

    @FXML
    private void exitChangePasswordMenu()
    {
        getAccountPanel().setCenter(getAccountSettings());
    }

    private void resetPasswordFields()
    {
        currentPasswordField.setText("");
        newPasswordField.setText("");
        confirmPasswordField.setText("");
        changePasswordErrorField.setText("");
    }

    @FXML
    private void confirmNewPassword()
    {
        String currentPassword = currentPasswordField.getText();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (checkValidityOfCurrentPassword(currentPassword, newPassword) && checkPassword(newPassword, confirmPassword, changePasswordErrorField))
        {
            getCurrentAccount().setPassword(newPassword);
            passwordFeedbackLabel.setText("Password changed successfully");
            exitChangePasswordMenu();
        }
    }

    private boolean checkValidityOfCurrentPassword(String currentPassword, String newPassword)
    {
        boolean valid = false;
        String password = getCurrentAccount().getPassword();

        if (!currentPassword.equals(password))
        {
            changePasswordErrorField.setText("Current password is incorrect");
        } else if (newPassword.equals(password))
        {
            changePasswordErrorField.setText("New password is the same as current");
        } else
        {
            valid = true;
        }

        return valid;
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

    public void resetAccountSettings()
    {
        saveFeedbackLabel.setText("");
        changeUsernameField.setText("");
        changeUsernameErrorLabel.setText("");
        changeAvatarCircle.setFill(new ImagePattern(getCurrentAccount().getProfilePicture()));
        profileCircle.setFill(new ImagePattern(getCurrentAccount().getProfilePicture()));
        bufferImage = null;
        imagePathLabel.setText(IMAGE_PATH_DEFAULT);
    }

    private void setCircles()
    {
        profileCircle.setFill(new ImagePattern(getCurrentAccount().getProfilePicture()));
        changeAvatarCircle.setFill(new ImagePattern(getCurrentAccount().getProfilePicture()));
    }
}
