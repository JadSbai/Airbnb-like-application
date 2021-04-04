package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
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

    @FXML
    private ListView<BorderPane> listOfBookings;

    @FXML
    private ListView<Pane> listOfFavourites;

    @FXML private Label currentUsernameLabel;

    @FXML
    private Label emptyListLabel;

    @FXML
    private Label emptyListLabel2;

    @FXML
    public static final String IMAGE_PATH_DEFAULT = "No file chosen";

    private Image bufferImage;

    private Image bufferedBasicAvatar;

    private Stage stage;

    private BorderPane accountPanel;

    private Pane chooseAvatarMenu;

    private Pane changePasswordMenu;

    private AccountAccessController accountAccessController;


    public void initialize(AccountPanelController apc, AccountAccessController accountAccessController, BorderPane accountPanel) throws IOException {

        FXMLLoader accountSettingsLoader = new FXMLLoader(getClass().getResource("AccountSettings.fxml"));
        accountSettingsLoader.setController(apc);
        accountSettings = accountSettingsLoader.load();

        FXMLLoader pfpGridLoader = new FXMLLoader(getClass().getResource("pfpgrid.fxml"));
        pfpGridLoader.setController(apc);
        chooseAvatarMenu = pfpGridLoader.load();

        FXMLLoader changePasswordLoader = new FXMLLoader(getClass().getResource("changepassword.fxml"));
        changePasswordLoader.setController(apc);
        changePasswordMenu = changePasswordLoader.load();

        pfpGrid.setVgap(20);
        pfpGrid.setHgap(20);

        FXMLLoader accountDetailsLoader = new FXMLLoader(getClass().getResource("AccountDetails.fxml"));
        accountDetailsLoader.setController(apc);
        accountDetails = accountDetailsLoader.load();

        this.accountAccessController = accountAccessController;
        this.accountPanel = accountPanel;

        chooseFileButton.setOnAction(e-> {
            try {
                chooseFile(getStage());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        Image cursor = new Image("/sample/crossout.png");
        emailField.setCursor(new ImageCursor(cursor, cursor.getWidth()/2, cursor.getHeight()/2));

        loadBasicAvatars();
    }

    public void resetAccountSettings()
    {
        saveFeedbackLabel.setText("");
        changeUsernameField.setText("");
        changeUsernameErrorLabel.setText("");
        changeAvatarCircle.setFill(new ImagePattern(currentAccount.getProfilePicture()));
        profileCircle.setFill(new ImagePattern(currentAccount.getProfilePicture()));
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
        currentUsernameLabel.setText(currentAccount.getUsername());
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

        if (checkChangeUsernameValidity(newUsername) && accountAccessController.checkUsername(newUsername, changeUsernameErrorLabel)) {
            accountAccessController.changeUsername(newUsername);
            changeUsernameField.setPromptText(newUsername);
            currentUsernameLabel.setText(newUsername);
            changes = true;
        }
        if (bufferImage != null)
        {
            currentAccount.setProfilePicture(bufferImage);
            accountAccessController.setProfileCircles();
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
        stage.sizeToScene();
    }

    @FXML
    private void exitAvatarMenu()
    {
        profileCircle.setFill(new ImagePattern(currentAccount.getProfilePicture()));
        bufferedBasicAvatar = null;
        accountPanel.setCenter(accountSettings);
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
        accountPanel.setCenter(changePasswordMenu);
    }

    @FXML
    private void exitChangePasswordMenu()
    {
        accountPanel.setCenter(accountSettings);
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

        if (checkValidityOfCurrentPassword(currentPassword, newPassword) && accountAccessController.checkPassword(newPassword, confirmPassword, changePasswordErrorField))
        {
            currentAccount.setPassword(newPassword);
            passwordFeedbackLabel.setText("Password changed successfully");
            exitChangePasswordMenu();
        }
    }

    private boolean checkValidityOfCurrentPassword(String currentPassword, String newPassword)
    {
        boolean valid = false;
        String password = currentAccount.getPassword();

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

    public void loadFavourites() throws IOException
    {
        emptyListLabel.setText("");
        listOfFavourites.setItems(currentAccount.getListViewOfFavourites().getItems());
        if(listOfFavourites.getItems().isEmpty()){
            emptyListLabel.setText("You currently have no favourites. Click on the \"Save\" button to add a favourite.");
        }
    }

    public void loadBookings()
    {
        emptyListLabel2.setText("");
        listOfBookings.setItems(currentAccount.getListViewOfBookings().getItems());
        if(listOfBookings.getItems().isEmpty()){
            emptyListLabel2.setText("You currently have no bookings. Click on the \"Reserve\" button to add a booking.");
        }
    }

    @FXML
    private void accountSettingsAction()
    {
        BorderPane accountPanel = accountAccessController.getAccountPanel();
        accountPanel.setCenter(accountSettings);
        stage.sizeToScene();
    }

    @FXML
    private void accountDetailsAction() throws IOException {
        loadFavourites();
        loadBookings();
        stage.sizeToScene();
        BorderPane accountPanel = accountAccessController.getAccountPanel();
        accountPanel.setCenter(accountDetails);
    }

}
