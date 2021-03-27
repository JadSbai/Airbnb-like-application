package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountController
{
    @FXML
    private TextField createAccountUsername;
    @FXML
    private TextField createAccountEmail;
    @FXML
    private PasswordField createAccountPassword;
    @FXML
    private PasswordField createAccountConfirmPassword;
    @FXML
    private TextField signInEmail;
    @FXML
    private PasswordField signInPassword;

    private MainController mainController;

    private BorderPane accountBar;

    private ArrayList<Account> listOfAccounts;

    private HashMap<String, Account> accountsMap;

    private Scene signInScene;

    private Scene createAccountScene;

    private Stage accountStage;

    private Pane signedInBar;

    private Pane signedOutBar;

    private boolean isAccountWindowOpen;

    @FXML
    public void initialize(MainController mainController, AccountController accountController, Pane signedOutBar) throws IOException {
        this.mainController = mainController;
        accountBar = mainController.getAccountBar();
        listOfAccounts = new ArrayList<>();
        accountsMap = new HashMap<>();
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
            warningAlert("An account window is already open", "window already open");

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
            warningAlert("An account window is already open", "window already open");

        }
    }

    @FXML
    private void createAccountLink(ActionEvent e)
    {
        ActionEvent event = new ActionEvent();
        accountStage.close();
        isAccountWindowOpen = false;
        createAccount(event);
    }

    private void warningAlert(String error, String errorTitle)
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
        String username = createAccountUsername.getText();
        String email = createAccountEmail.getText();
        String password = createAccountPassword.getText();
        String confirmPassword = createAccountConfirmPassword.getText();
        if(checkValidityOfCreateAccountFields(username, email, password, confirmPassword)) {
            Account newAccount = new Account(username, email, password);
            listOfAccounts.add(newAccount);
            accountsMap.put(email, newAccount);
            loadAccount(newAccount);
            accountStage.close();
        }

    }

    @FXML
    private void signInAction(ActionEvent e)
    {
        String email = signInEmail.getText();
        String password = signInPassword.getText();
        if(checkValidityOfSignInFields(email, password)){
            loadAccount(getAccount(email));
            accountStage.close();
        }


    }

    @FXML
    private void signOutAction()
    {
        saveAllSettingsAndData();
        accountBar.setRight(signedOutBar);
    }

    private void saveAllSettingsAndData()
    {
        System.out.println("All account settings and data usage successfully saved");
    }

    private boolean checkValidityOfCreateAccountFields(String username, String email, String password, String confirmPassword)
    {
        // Check validity of all the fields when creating an account
        // Throws specific alerts when fields are invalid
        return (checkUsername(username) && checkEmail(email) && checkPassword(password, confirmPassword));
    }


    private boolean checkValidityOfSignInFields(String email, String password)
    {
        // Check validity of all the fields when signing in.
        // Throws specific alerts when fields are invalid
        return (checkAccountEmail(email) && checkAccountPassword(password));
    }

    private boolean checkAccountEmail(String email)
    {
        if(isValidEmailPattern(email)) {
            for (Account account : listOfAccounts) {
                if (email.equals(account.getEmail())) {
                    return true;
                }
            }
            warningAlert("The email address you've entered is not associated to any account. Please try again or create an account", "Field not corresponding");
            return false;
        }
        return false;
    }

    private boolean checkAccountPassword(String password)
    {
        for (Account account : listOfAccounts) {
            if (password.equals(account.getPassword())) {
                return true;
            }
        }
        warningAlert("The password you've entered is not associated to this address email. Please try again or create an account", "Field not corresponding");
        return false;
    }



    private boolean checkUsername(String username)
    {
        if(username.length() == 0){
            warningAlert("Please choose a username ", "Empty field");
            return false;
        }
        for(Account account : listOfAccounts){
            if(username.equals(account.getUsername())){
                warningAlert("This field is already taken by another account. Please choose another username", "One of the fields is already taken");
                return false;
            }
        }
        return true;
    }

    private boolean checkEmail(String email) {
        // First check the pattern of the email, if it's valid then continue, else throw an alert and return false
        if(email.length() == 0 || !isValidEmailPattern(email)){
            warningAlert("Please enter a valid email address", "Empty field");
            return false;
        }
        for(Account account : listOfAccounts){
            if(email.equals(account.getEmail())){
                warningAlert("This email is already associated to an account. Please choose another email", "One of the fields is already taken");
                return false;
            }
        }
        return true;
    }

    private boolean isValidEmailPattern(String email) {
        // I didn't write the first line of code and thus do not claim ownership of this code (include the URL where it was found...). It allows to check that the email entered follows the international standard.
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }

    private boolean checkPassword(String password, String confirmPassword)
    {
        if(password.length() == 0){
            warningAlert("Please enter a password", "Empty field");
            return false;
        }
        else {
            return checkPasswordStrength(password) && checkPasswordEquality(password, confirmPassword);
        }

    }

    private boolean checkPasswordStrength(String password)
    {
        String regex = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        if(!matcher.matches()){
            warningAlert("The password you have entered is too weak: No whitespace is allowed and it must contain at least:\n" + "1 lower case letter\n" + "1 upper case letter\n" + "1 special character\n" + "8 characters", "Invalid password");
            return false;
        }
        return true;
    }

    private boolean checkPasswordEquality(String password, String confirmPassword)
    {
        if(!password.equals(confirmPassword)){
            warningAlert("The password you've entered is not the same in both the password fields. Please make sure you enter the same password", "Passwords not equal");
            return false;
        }
        return true;
    }




    private void loadAccount(Account account)
    {
        // load all info related to the specified account in the fields of the profile button.
        accountBar.setRight(signedInBar);

    }

    private Account getAccount(String email)
    {
        return accountsMap.get(email);
    }


    public Pane getSignedInBar() {
        return signedInBar;
    }

    public Pane getSignedOutBar() {
        return signedOutBar;
    }
}
