package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AccountController
{
    @FXML
    private TextField username;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmPassword;

    private MainController mainController;

    private ArrayList<Account> listOfAccounts;

    private Scene signInScene;

    private Scene createAccountScene;

    private Stage accountStage;

    private Pane signedInBar;

    private Pane signedOutBar;

    private boolean isAccountWindowOpen;

    @FXML
    public void initialize(MainController mainController, AccountController accountController, Pane signedOutBar) throws IOException {
        this.mainController = mainController;
        listOfAccounts = new ArrayList<>();
        this.signedOutBar = signedOutBar;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("signed_in.fxml"));
        loader.setController(accountController);
        signedInBar = loader.load();

        FXMLLoader signInPanelLoader = new FXMLLoader(getClass().getResource("sign_in_panel.fxml"));
        signInPanelLoader.setController(accountController);
        Pane signInPanel = signInPanelLoader.load();
        signInScene = new Scene(signInPanel);

        FXMLLoader createAccountPanelLoader = new FXMLLoader(getClass().getResource("create_account_panel.fxml"));
        createAccountPanelLoader.setController(accountController);
        Pane createAccountPanel = createAccountPanelLoader.load();
        createAccountScene = new Scene(createAccountPanel);

        accountStage = new Stage();
    }

    @FXML
    private void signIn(ActionEvent e)
    {
        if(!isAccountWindowOpen) {
            accountStage.setScene(signInScene);
            accountStage.setTitle("Sign in");
            accountStage.show();
            isAccountWindowOpen = true;
            accountStage.setOnCloseRequest(event -> {
                        isAccountWindowOpen = false;
                    }
            );
        }
        else{
            windowAlreadyOpen("An account window is already open", "window already open");
        }

    }

    @FXML
    private void createAccount(ActionEvent e)
    {
        if(!isAccountWindowOpen){
            accountStage.setScene(createAccountScene);
            accountStage.setTitle("Create a new account");
            accountStage.show();
            isAccountWindowOpen = true;
            accountStage.setOnCloseRequest(event -> {
                        isAccountWindowOpen = false;
                    }
            );
        }
        else{
            windowAlreadyOpen("An account window is already open", "window already open");
        }

    }

    private void windowAlreadyOpen(String error, String errorTitle)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(errorTitle);
        alert.setHeaderText(null);
        alert.setContentText(error);

        alert.showAndWait();
    }

    @FXML
    private void createAccountAction(ActionEvent e)
    {
        checkValidityOfFields();
        Account newAccount = new Account(username.getText(), email.getText(), password.getText());
        listOfAccounts.add(newAccount);
        mainController.getAccountBar().setRight(signedInBar);
        accountStage.close();

    }

    @FXML
    private void signInAction(ActionEvent e)
    {

    }

    private boolean checkValidityOfFields()
    {
        return true;
    }


    public Pane getSignedInBar() {
        return signedInBar;
    }

    public Pane getSignedOutBar() {
        return signedOutBar;
    }
}
