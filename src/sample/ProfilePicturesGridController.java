package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ProfilePicturesGridController extends AccountController
{
    /**
    * common instance of ControllerComponents held by all classes containing common elements like the account.
    */
    private ControllerComponents controllerComponents;

    /**
    * The controller which controls the components of the accountSettings pane.
    */
    private AccountSettingsController accountSettingsController;

    /**
    * The tilepane that contains the images of the sample avatars that
    * the user can set as their profile picture.
    */
    @FXML
    private TilePane pfpGrid;

    /**
     * @param controllerComponents common instance of ControllerComponents held by all classes containing common elements like the account.
     * @param accountStage The stage that contains the accountPanel where the accountSettings and accountDetails are displayed.
     * @param accountSettingsController The controller for the accountSettings panel.
     */
    public ProfilePicturesGridController(ControllerComponents controllerComponents, Stage accountStage, AccountSettingsController accountSettingsController) {
        super(controllerComponents, accountStage);
        this.controllerComponents = controllerComponents;
        this.accountSettingsController = accountSettingsController;
    }

    /**
     * Initializes the pfp grid and loads the basic avatars into the grid from
     * src/sample/pfp.
     * @throws IOException {@link IOException;} in some circumstance
     */
    public void initialize() throws IOException
    {
        pfpGrid.setVgap(20);
        pfpGrid.setHgap(20);

        loadBasicAvatars();
    }

    /**
     *  Sets the center of the accountPanel back to the accountSettings pane and
     *  resets some elemets where necessary.
     */
    @FXML
    private void exitAvatarMenu()
   {
        accountSettingsController.getAccountStageProfileCircle().setFill(new ImagePattern(controllerComponents.getAccount().getProfilePicture()));
        accountSettingsController.setBufferedBasicAvatar(null);
        getAccountPanel().setCenter(accountSettingsController.getAccountSettingsPanel());
        getAccountStage().sizeToScene();
    }

    /**
     *  Sets the bufferImage to the selected avatar so that it can be set to the
     *  user's profile picture if they save changes. Exits the avatar menu.
     */
    @FXML
    private void confirmAvatarAction()
    {
        Image bufferImage = accountSettingsController.getBufferedBasicAvatar();
        if (bufferImage != null)
        {
        accountSettingsController.setBufferImage(bufferImage);
        accountSettingsController.getChangeAvatarCircle().setFill(new ImagePattern(bufferImage));
        accountSettingsController.getImagePathLabel().setText(AccountSettingsController.IMAGE_PATH_DEFAULT);
        }
        exitAvatarMenu();
    }

    /**
     * Loads all the basic avatars into the tile pane from the
     * src/sample/pfp directory.
     */
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

    /**
     * Selects an avatar provided an image
     * @param image new avatar image
     */
    private void selectBasicAvatar(Image image)
    {
        accountSettingsController.setBufferedBasicAvatar(image);
        accountSettingsController.getAccountStageProfileCircle().setFill(new ImagePattern(accountSettingsController.getBufferedBasicAvatar()));
    }



}
