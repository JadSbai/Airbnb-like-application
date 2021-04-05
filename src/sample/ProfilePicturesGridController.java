package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.File;
import java.io.IOException;

public class ProfilePicturesGridController extends AccountSettingsController
{
    @FXML
    private TilePane pfpGrid;

    @FXML
    public static final String IMAGE_PATH_DEFAULT = "No file chosen";

    public void initialize() throws IOException
    {
        pfpGrid.setVgap(20);
        pfpGrid.setHgap(20);

        loadBasicAvatars();
    }



    @FXML
    private void exitAvatarMenu()
    {
        getProfileCircle().setFill(new ImagePattern(getAccount().getProfilePicture()));
         setBufferedBasicAvatar(null);
        getAccountPanel().setCenter(getAccountSettings());
    }

    @FXML
    private void confirmAvatarAction()
    {
        setBufferImage(getBufferedBasicAvatar());
        getChangeAvatarCircle().setFill(new ImagePattern(getBufferImage()));
        getImagePathLabel().setText(IMAGE_PATH_DEFAULT);

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
        setBufferedBasicAvatar(image);
        getProfileCircle().setFill(new ImagePattern(getBufferedBasicAvatar()));
    }

    public void resetAccountSettings()
    {
        getSaveFeedbackLabel().setText("");
        getChangePasswordErrorField().setText("");
        getChangeUsernameErrorLabel().setText("");
        getChangeAvatarCircle().setFill(new ImagePattern(getAccount().getProfilePicture()));
        getProfileCircle().setFill(new ImagePattern(getAccount().getProfilePicture()));
        setBufferImage(null);
        getImagePathLabel().setText(IMAGE_PATH_DEFAULT);
    }

}
