package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class AccountController
{

    private Pane signInWindow;
    private Scene signInScene;

    private boolean isSignInWindowOpen;

    @FXML
    public void initialize() throws IOException {
        FXMLLoader signInWindowLoader = new FXMLLoader(getClass().getResource("sign_in_window.fxml"));
        signInWindow = signInWindowLoader.load();

        signInScene = new Scene(signInWindow);

    }

    @FXML
    private void signInAction(ActionEvent e)
    {

        if(!isSignInWindowOpen){
            Stage signInStage = new Stage();
            signInStage.setScene(signInScene);
            signInStage.setTitle("Sign in");
            signInStage.show();
            isSignInWindowOpen = true;
            signInStage.setOnCloseRequest(event -> {
                isSignInWindowOpen = false;
            }
            );
        }

    }

    @FXML
    private void createAccount(ActionEvent e)
    {

    }
}
