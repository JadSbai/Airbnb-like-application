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
    private ControllerComponents controllerComponents;
    
    private AccountSettingsController accountSettingsController;

    @FXML
    private TilePane pfpGrid;

    public ProfilePicturesGridController(ControllerComponents controllerComponents, Stage accountStage, AccountSettingsController accountSettingsController) {
        super(controllerComponents, accountStage);
        this.controllerComponents = controllerComponents;
        this.accountSettingsController = accountSettingsController;
    }

    public void initialize() throws IOException
    {
        pfpGrid.setVgap(20);
        pfpGrid.setHgap(20);

        loadBasicAvatars();
    }

    @FXML
    private void exitAvatarMenu()
   {
        accountSettingsController.getAccountStageProfileCircle().setFill(new ImagePattern(controllerComponents.getAccount().getProfilePicture()));
        accountSettingsController.setBufferedBasicAvatar(null);
        getAccountPanel().setCenter(accountSettingsController.getAccountSettingsPanel());
    }

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
        accountSettingsController.setBufferedBasicAvatar(image);
        accountSettingsController.getAccountStageProfileCircle().setFill(new ImagePattern(accountSettingsController.getBufferedBasicAvatar()));
    }



}