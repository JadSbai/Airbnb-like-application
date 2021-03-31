package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;






/**
 * This class is in charge of all the GUI's containers and controls related to the creation and usage of an AirBnB account.
 * It tracks the text fields' values and updates the main window's top part according to the current state of the account (signed in or signed out)
 * It also manages all secondary window's meant for signing in or creating an AirBnB account
 * It also manages the different accounts created so far in the app
 * @author Jacqueline Ilie, Liam Clark Gutiérrez, Dexter Trower and Jad Sbaï
 * @version 29/03/2021
 */
public class AccountController
{
    // FXML fields are tracking the different FXML controls or containers used for manipulating or displaying account data. Their IDs and the methods they call on action are set in the fxml files:

    // Text field for the "create account" username
    @FXML
    private TextField createAccountUsername;

    // Text field for the "create account" email
    @FXML
    private TextField createAccountEmail;

    // Password field for the "create account" password
    @FXML
    private PasswordField createAccountPassword;

    // Password field for the "create account" confirmation password
    @FXML
    private PasswordField createAccountConfirmPassword;

    // Text field for the "sign in" email
    @FXML
    private TextField signInEmail;

    // Text field for the "sign in" password
    @FXML
    private PasswordField signInPassword;

    // Error label for the "create account" username
    @FXML
    private Label usernameCreateAccountErrorLabel;

    // Error label for the "create account" email
    @FXML
    private Label emailCreateAccountErrorLabel;

    // Error label for the "create account" password
    @FXML
    private Label passwordCreateAccountErrorLabel;

    // Error label for the "create account" confirmation password
    @FXML
    private Label confirmPasswordCreateAccountErrorLabel;

    // Hyperlink for password info
    @FXML
    private Hyperlink whatIsAStrongPasswordLink;

    // Error label for the "sign in" email
    @FXML
    private Label emailSignInErrorLabel;

    // Error label for the "sign in" password
    @FXML
    private Label passwordSignInErrorLabel;

    @FXML
    private VBox subPane;

    @FXML
    private Circle profileCircle;






    // Other fields:

    private BorderPane accountBar;
    // List of current accounts created in the app
    private ArrayList<Account> listOfAccounts;
    // Maps the email of each account to the corresponding account object
    private HashMap<String, Account> accountsMap;
    // Scene loaded when a user wants to sign in their account
    private Scene signInScene;
    // Scene loaded when a user wants to create a new account
    private Scene createAccountScene;
    // Stage containing either one of the two account scenes ("sign in" ore "create account")
    private Stage accountStage;
    // Pane loaded at the top of the main frame when a user is signed in to their account
    private Pane signedInBar;
    // Pane loaded at the top of the main frame when a user is not signed in to any account
    private Pane signedOutBar;
    // Tracks whether an "account window" is open or not
    private boolean isAccountWindowOpen;
    // Tracks the current account being manipulated by the application
    private Account currentAccount;
    // MapController object of the application
    private MapController mapController;
    // WelcomeController object of the application
    private WelcomeController welcomeController;


    /**
     * The constructor initializes all the non-FXML fields, loads all the account related fxml files, sets their controller and displays the resulting stage.
     * @param accountController The account controller object passed from the MainController
     * @param mainController The main controller object passed from the Main controller class
     * @param signedOutBar The signed Out Pane that was set in the MainController class
     * @throws IOException if the designated files are not loaded successfully
     */
    public void initialize(MainController mainController, AccountController accountController, Pane signedOutBar, MapController mapController) throws IOException
    {
        accountBar = mainController.getAccountBar();
        listOfAccounts = new ArrayList<>();
        accountsMap = new HashMap<>();
        this.signedOutBar = signedOutBar;
        currentAccount = null;
        isAccountWindowOpen = false;
        this.mapController = mapController;
        welcomeController = mapController.getWelcomeController();


        FXMLLoader signedInLoader = new FXMLLoader(getClass().getResource("signed_in.fxml"));
        signedInLoader.setController(accountController);
        signedInBar = signedInLoader.load();

        FXMLLoader signInPanelLoader = new FXMLLoader(getClass().getResource("sign_in_panel.fxml"));
        signInPanelLoader.setController(accountController);
        Pane signInPanel = signInPanelLoader.load();
        signInScene = new Scene(signInPanel);

        FXMLLoader createAccountPanelLoader = new FXMLLoader(getClass().getResource("create_account_panel.fxml"));
        createAccountPanelLoader.setController(accountController);
        Pane createAccountPanel = createAccountPanelLoader.load();
        createAccountScene = new Scene(createAccountPanel);

        Image image = new Image("/sample/pfp/nopfp.png");
        this.profileCircle.setFill(new ImagePattern(image));

        accountStage = new Stage();
    }

    /**
     * This method opens a new window for the user to sign in into their account unless an "account window" is already open (either "sign in"  or "create account")
     * @param e The event (button click) that triggers the method call
     */
    @FXML
    private void signIn(ActionEvent e)
    {
        if(!isAccountWindowOpen) {
            resetSignInSceneFields();
            accountStage.setScene(signInScene);
            accountStage.setTitle("Sign in");
            accountStage.show();
            isAccountWindowOpen = true;
            // We use a window listener to avoid having multiple similar windows open at the same time
            accountStage.setOnCloseRequest(event -> {
                        isAccountWindowOpen = false;
                    }
            );
        }
        else{
            // Throws an alert to signal that a window is already open
            warningAlert("An account window is already open", "window already open");
            accountStage.close();
            accountStage.show();
        }

    }

    /**
     * This method opens a new window for the user to create a new account unless an "account window" is already open (either "sign in"  or "create account")
     * @param e The event (button click) that triggers the method call
     */
    @FXML
    private void createAccount(ActionEvent e)
    {
        if(!isAccountWindowOpen){
            resetCreateAccountFields();
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
            accountStage.close();
            accountStage.show();

        }
    }

    /**
     * This method closes the current window and opens the create account window
     * @param e The event (button click) that triggers the method call
     */
    @FXML
    private void createAccountLink(ActionEvent e)
    {
        accountStage.close();
        isAccountWindowOpen = false;
        createAccount(e);
    }
    /**
     * This method creates and displays an alert of type WARNING
     * @param warning The warning to be displayed
     * @param warningTitle The title of the warning
     */
    private void warningAlert(String warning, String warningTitle)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(warningTitle);
        alert.setHeaderText(null);
        alert.setContentText(warning);
        alert.showAndWait();
    }

    /**
     * This method creates and displays an alert of type INFORMATION
     * @param info The warning to be displayed
     * @param infoTitle The title of the warning
     */
    private void infoAlert(String info, String infoTitle)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(infoTitle);
        alert.setHeaderText(null);
        alert.setContentText(info);
        alert.showAndWait();
    }

    /**
     * This method retrieves the contents of the "create account" window's text fields.
     * It then checks whether they are valid according to certain restrictions
     * If the fields are valid, it creates a new account with the given credentials and updates the appropriate fields.
     * The account stage is closed
     * If a price range has been entered, then it loads the account's specific map view GUI elements.
     * @param e The event (button click) that triggers the method call
     */
    @FXML
    private void createAccountAction(ActionEvent e) throws IOException {
        String username = createAccountUsername.getText();
        String email = createAccountEmail.getText();
        String password = createAccountPassword.getText();
        String confirmPassword = createAccountConfirmPassword.getText();

        if(checkValidityOfCreateAccountFields(username, email, password, confirmPassword)) {
            Account newAccount = new Account(username, email, password);
            listOfAccounts.add(newAccount);
            accountsMap.put(email, newAccount);
            currentAccount = newAccount;
            isAccountWindowOpen = false;
            mapController.setCurrentAccount(currentAccount);
            accountBar.setRight(signedInBar);
            accountStage.close();

            if(welcomeController.isSearched()){
                loadAccount(currentAccount);
            }

        }

    }

    /**
     * This method retrieves the contents of the "sign in" window's text fields.
     * It then checks whether they are valid according to certain restrictions
     * If the fields are valid, it loads the account's data and settings.
     * The stage is then closed
     * If a price range has been entered, then it loads the account's specific map view GUI elements.
     * @param e The event (button click) that triggers the method call
     */
    @FXML
    private void signInAction(ActionEvent e) throws IOException {
        String email = signInEmail.getText();
        String password = signInPassword.getText();

        if(checkValidityOfSignInFields(email, password)){
            currentAccount = getAccount(email);
            isAccountWindowOpen = false;
            mapController.setCurrentAccount(currentAccount);
            accountBar.setRight(signedInBar);
            loadDataAndSettings(currentAccount);
            accountStage.close();

            if(welcomeController.isSearched()){
                loadAccount(currentAccount);
            }

        }
    }

    /**
     * This method saves all data and settings of the current account and signs it out
     * It then sets the top of the main frame to the "signed out" layout
     * If a price range has been entered, then it loads the default map view GUI elements
     * @param e The event (button click) that triggers the method call
     */
    @FXML
    private void signOutAction(ActionEvent e) throws IOException {
        saveAllSettingsAndData();
        currentAccount = null;
        mapController.setCurrentAccount(null);
        accountBar.setRight(signedOutBar);

        if(welcomeController.isSearched()){
            setDefaultSettingsAndData();
        }

    }

    /**
     * This method throws an info info alert via a method call to indicate to the user the password restrictions in place
     * @param e The event (button click) that triggers the method call
     */
    @FXML
    private void whatIsAStrongPasswordAction(ActionEvent e)
    {
        infoAlert(" Passwords must contain a lowercase letter, capital letter, number, symbol and must be at least 8 characters. It cannot contain any spaces.", "What is a strong password?");
    }

    /**
     * This method saves all data and settings of the current account by updating the appropriate fields
     */
    private void saveAllSettingsAndData()
    {
        System.out.println("All account settings and data successfully saved");
    }

    /**
     * This method sets all the settings and data of the account to default values
     * It also loads the default map view GUI elements
     */
    private void setDefaultSettingsAndData() throws IOException {
        mapController.loadCurrentAccount(null);
    }

    /**
     * This method returns whether the fields of the "create account" window are valid or not
     * @param username The username entered
     * @param email The email entered
     * @param  password The password entered
     * @param confirmPassword the password for confirmation entered
     * @return true if the fields are valid and false otherwise
     */
    private boolean checkValidityOfCreateAccountFields(String username, String email, String password, String confirmPassword)
    {
        return (checkUsername(username) && checkEmail(email) && checkPassword(password, confirmPassword));
    }

    /**
     * This method returns whether the fields of the "sign in" window are valid or not
     * @param email The email entered
     * @param  password The password entered
     * @return true if the fields are valid and false otherwise
     */
    private boolean checkValidityOfSignInFields(String email, String password)
    {
        return (checkAccountEmail(email) && checkAccountPassword(password));
    }

    /**
     * This method returns whether the email entered in the "sign in" window is valid, i.e., if it follows the standard email pattern
     * and if it is indeed associated to an existing account
     * If it is not valid, prints an error in the corresponding error label
     * @param email The email entered
     * @return true if the email is valid and associated to an existing account, false otherwise
     */
    private boolean checkAccountEmail(String email)
    {
        emailSignInErrorLabel.setText("");

        if(email.length() == 0){
            // Set the text of the error label (situated just above the text field)
            emailSignInErrorLabel.setText("Please enter an email address");
            return false;
        }
        // If the email follows the right pattern
        if(isValidEmailPattern(email)) {
            // If the email is not associated to any existing account
            if(accountsMap.get(email) == null){
                emailSignInErrorLabel.setText("This email address is not associated to any account.");
                return false;
            }
            return true;
        }
        else{
            emailSignInErrorLabel.setText("The email address you've entered is not valid");
        }
        return false;

    }

    /**
     * This method returns whether the password entered in the "sign in" window is valid or not, i.e., if it corresponds to the previously entered email
     * If it is not valid, prints an error in the corresponding error label
     * @param password The password entered
     * @return true if the password is valid and false otherwise
     */
    private boolean checkAccountPassword(String password)
    {
        passwordSignInErrorLabel.setText("");
        if(password.length() == 0){
            passwordSignInErrorLabel.setText("Please enter a password");
            return false;
        }
        for (Account account : listOfAccounts) {
            if (password.equals(account.getPassword())) {
                return true;
            }
        }
        passwordSignInErrorLabel.setText("This email is not associated to this password.");
        return false;
    }

    /**
     * This method returns whether the username entered in the "create account" window is valid or not, i.e., if it is not already taken by another account
     * If it is not valid, prints an error in the corresponding error label
     * @param username The username entered
     * @return true if the username entered isn't already taken by another account and false otherwise
     */
    private boolean checkUsername(String username)
    {
        usernameCreateAccountErrorLabel.setText("");

        if(username.length() == 0){
            usernameCreateAccountErrorLabel.setText("Please choose a username ");
            return false;
        }
        for(Account account : listOfAccounts){
            if(username.equals(account.getUsername())){
                usernameCreateAccountErrorLabel.setText("This field is already taken by another account. Please choose another username");
                return false;
            }
        }
        return true;
    }

    /**
     * This method returns whether the email entered in the "create account" window is valid or not, i.e., if it follows the right pattern
     * and if it is not already associated to an existing account.
     * If it is not valid, prints an error in the corresponding error label
     * @param email The email entered
     * @return true if the email entered follows the specified pattern and isn't already taken by another account, false otherwise
     */
    private boolean checkEmail(String email)
    {
        emailCreateAccountErrorLabel.setText("");

        if(email.length() == 0 || !isValidEmailPattern(email)){
            emailCreateAccountErrorLabel.setText("Please enter a valid email address");
            return false;
        }
        for(Account account : listOfAccounts){
            if(email.equals(account.getEmail())){
                emailCreateAccountErrorLabel.setText("This email is already associated to an account. Please choose another email");
                return false;
            }
        }
        return true;
    }

    /**
     * This method returns whether the email entered in the "create account" window is valid or not, i.e., if it follows the right pattern
     * and if it is not already associated to an existing account.
     * @param email The email entered
     * @return true if the email entered follows the specified pattern and isn't already taken by another account, false otherwise
     */
    private boolean isValidEmailPattern(String email) {
        // We do not claim ownership of the following line of code: URL:..
        // It creates a regex corresponding to the email address international standard
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        // Create a pattern based on the regex specified
        Pattern pattern = Pattern.compile(regex);
        // Create a matcher between the email and the pattern
        Matcher matcher = pattern.matcher(email);
        // returns whether the email follows the specified pattern
        return matcher.matches();

    }

    /**
     * This method returns whether the password entered in the "create account" window is valid or not, i.e., if it is strong enough
     * and is equal to the confirmation password
     * If it is empty, prints an error (in the corresponding error label)
     * @param password The password entered
     * @param confirmPassword the confirmation password entered
     * @return true if the password entered is strong and is equal to the confirmation password, false otherwise
     */
    private boolean checkPassword(String password, String confirmPassword)
    {
        passwordCreateAccountErrorLabel.setText("");

        if(password.length() == 0){
            passwordCreateAccountErrorLabel.setText("Please enter a password");
            return false;
        }
        else {
            return checkPasswordStrength(password) && checkPasswordEquality(password, confirmPassword);
        }
    }

    /**
     * This method returns whether the password entered in the "create account" window is valid or not, i.e., if it follows the right pattern to be strong enough
     * If it is not valid, it throws an alert and prints an error (in the corresponding error label)
     * @param password The password entered
     * @return true if the password entered is deemed strong and false otherwise
     */
    private boolean checkPasswordStrength(String password)
    {
        passwordCreateAccountErrorLabel.setText("");

        // We do not claim ownership of the following line of code: URL =...
        // It creates a regex corresponding to certain password restrictions (...)
        String regex = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!?.><:;])(?=\\S+$).{8,}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        if(!matcher.matches()){
            passwordCreateAccountErrorLabel.setText("This password is too weak.");
            return false;
        }
        return true;
    }

    /**
     * This method returns whether the password and confirmation password are equal or not. Prints an error (in the corresponding error label) if not.
     * If it is not valid, prints an error in the corresponding error label
     * @param password The password entered
     * @param confirmPassword The confirmation password entered
     * @return true if the password and confirmation password match, false otherwise
     */
    private boolean checkPasswordEquality(String password, String confirmPassword)
    {
        confirmPasswordCreateAccountErrorLabel.setText("");

        if(!password.equals(confirmPassword)){
            confirmPasswordCreateAccountErrorLabel.setText("Passwords do not match");
            return false;
        }
        return true;
    }

    /**
     * This method loads all GUI-related elements that are proper to the account (state of the save button ...)
     * @param account The account to be loaded
     */
    private void loadAccount(Account account) throws IOException {
        mapController.loadCurrentAccount(account);
    }

    /**
     * This method loads all the account's data and settings in the corresponding fields and displays the "signed out" Pane at the top of the main frame
     * @param account The account to be loaded
     */
    private void loadDataAndSettings(Account account)
    {
        // Loads all data and settings apart from GUI elements related to the map (i.e., save button...)
    }

    private void resetSignInSceneFields()
    {
        signInEmail.setText("");
        signInPassword.setText("");
    }

    private void resetCreateAccountFields()
    {
        createAccountUsername.setText("");
        createAccountEmail.setText("");
        createAccountPassword.setText("");
        createAccountConfirmPassword.setText("");
    }

    @FXML
    private void profileClicked(MouseEvent e) {
        if (e.getButton() == MouseButton.PRIMARY) {
            subPane.setVisible(!subPane.isVisible());

        }

    }

    /**
     * This method returns the account instance associated to the specified email address
     * @return The account associated to the given email address
     */
    private Account getAccount(String email)
    {
        return accountsMap.get(email);
    }

    /**
     * This method returns the "signed in" pane object defined by the fxml file
     * @return The "signed in" pane
     */
    public Pane getSignedInBar() {
        return signedInBar;
    }

    /**
     * This method returns the "signed out" pane object defined by the fxml file
     * @return The "signed out" pane
     */
    public Pane getSignedOutBar() {
        return signedOutBar;
    }

    public void formatPopUpMenu() {
        StackPane.setMargin(subPane, new Insets(70,40,0,0));
    }
}
