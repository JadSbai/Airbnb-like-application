package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
    // FXML fields:
    // FXML fields are tracking the different FXML controls or containers used for manipulating or displaying account data. Their IDs and the methods they call on action are set in the fxml files:

    /**
     * Text field for the "create account" username
     */
    @FXML
    private TextField createAccountUsername;

    /**
     * Text field for the "create account" email
     */
    @FXML
    private TextField createAccountEmail;

    /**
     * Text field for the "create account" password
     */
    @FXML
    private PasswordField createAccountPassword;

    /**
     * Text field for the "create account" confirmation password
     */
    @FXML
    private PasswordField createAccountConfirmPassword;

    /**
     * Text field for the "sign in" email
     */
    @FXML
    private TextField signInEmail;

    /**
     * Text field for the "sign in" password
     */
    @FXML
    private PasswordField signInPassword;

    /**
     * Error label for the "create account" username
     */
    @FXML
    private Label usernameCreateAccountErrorLabel;

    /**
     * Error label for the "create account" email
     */
    @FXML
    private Label emailCreateAccountErrorLabel;

    /**
     * Error label for the "create account" password
     */
    @FXML
    private Label passwordCreateAccountErrorLabel;

    /**
     * Error label for the "create account" confirmation password
     */
    @FXML
    private Label confirmPasswordCreateAccountErrorLabel;

    /**
     * Hyperlink for info on password restrictions
     */
    @FXML
    private Hyperlink whatIsAStrongPasswordLink;

    /**
     * Error label for the "sign in" email
     */
    @FXML
    private Label emailSignInErrorLabel;

    /**
     * Error label for the "sign in" password
     */
    @FXML
    private Label passwordSignInErrorLabel;

    /**
     * VBox acting as a drop down menu displaying account options
     */
    @FXML
    private VBox subPane;

    /**
     * Profile circle situated at the top of right of the main pane containing the profile picture
     */
    @FXML
    private Circle profileCircle;

    /**
     * Profile circle situated in the account's drop down menu (i.e., the subPane field) containing the profile picture
     */
    @FXML
    private Circle profileCircle2;

    /**
     * Label situated in the account's drop down menu (i.e., the subPane field) that displays the account's username
     */
    @FXML
    private Label accountUsername;


    // Other fields (non-FXML fields):

    /**
     * BorderPane containing either the "signed in" bar or "signed out" bar depending on if an account is logged in or not
     */
    private BorderPane accountBar;

    /**
     * List of current accounts created in the app
     */
    private ArrayList<Account> listOfAccounts;

    /**
     * Maps the email of each account to the corresponding account object
     */
    private HashMap<String, Account> accountsMap;

    /**
     * Scene loaded when a user wants to sign in their account
     */
    private Pane signInPanel;

    /**
     * Scene loaded when a user wants to create a new account
     */
    private Pane createAccountPanel;

    @FXML
    private Scene accountScene;

    /**
     * Stage containing either one of the two account scenes ("sign in" ore "create account")
     */
    private Stage accountStage;

    /**
     * Pane loaded at the top of the main frame when a user is signed in to their account
     */
    private Pane signedInBar;

    /**
     * Pane loaded at the top of the main frame when a user is not signed in to any account
     */
    private Pane signedOutBar;

    /**
     * Tracks whether an "account window" is open or not
     */
    private boolean isAccountWindowOpen;

    /**
     * Tracks the current account being manipulated by the application
     */
    private Account currentAccount;

    /**
     * MapController object of the application
     */
    private MapController mapController;

    /**
     * WelcomeController object of the application
     */
    private WelcomeController welcomeController;

    private AccountPanelController accountPanelController;
    private Stage accountPanelStage;
    private BorderPane accountPanel;
    private boolean isSettingsShowed;

    /**
     * The constructor initializes all the non-FXML fields, loads all the account related fxml files, sets their controller and displays the resulting stage.
     * @param mainController The main controller object passed from the Main controller class
     * @param signedOutBar The signed Out Pane that was set in the MainController class
     * @throws IOException if the designated files are not loaded successfully
     */
    public void initialize(MainController mainController, Pane signedOutBar, MapController mapController) throws IOException
    {
        accountBar = mainController.getAccountBar();
        listOfAccounts = new ArrayList<>();
        accountsMap = new HashMap<>();
        this.signedOutBar = signedOutBar;
        currentAccount = null;
        isAccountWindowOpen = false;
        this.mapController = mapController;
        welcomeController = mapController.getWelcomeController();
        isSettingsShowed = false;


        FXMLLoader signedInLoader = new FXMLLoader(getClass().getResource("signed_in.fxml"));
        signedInLoader.setController(this);
        signedInBar = signedInLoader.load();

        FXMLLoader createAccountStage = new FXMLLoader(getClass().getResource("AccountStage.fxml"));
        createAccountStage.setController(this);
        accountStage = createAccountStage.load();

        FXMLLoader signInPanelLoader = new FXMLLoader(getClass().getResource("sign_in_panel.fxml"));
        signInPanelLoader.setController(this);
        signInPanel = signInPanelLoader.load();

        FXMLLoader createAccountPanelLoader = new FXMLLoader(getClass().getResource("create_account_panel.fxml"));
        createAccountPanelLoader.setController(this);
        createAccountPanel = createAccountPanelLoader.load();

        FXMLLoader accountPanelLoader = new FXMLLoader(getClass().getResource("AccountPanel.fxml"));
        accountPanel = accountPanelLoader.load();
        accountPanelController = accountPanelLoader.getController();
        accountPanelController.initialize(accountPanelController, this, accountPanel);
        accountPanelStage = new Stage();
        accountPanelStage.setScene(new Scene(accountPanel));

        formatPopUpMenu();
        setAccountUsername("");
    }


    /**
     * This method opens a new window for the user to sign in into their account unless an "account window" is already open (either "sign in"  or "create account")
     * @param e The event (button click) that triggers the method call
     */
    @FXML
    private void signIn(ActionEvent e)
    {
        if(!accountStage.isShowing()) {

            resetSignInPanelFields();
            accountScene.setRoot(signInPanel);
            accountStage.setTitle("Sign in");
        }
        else{
            // Throws an alert to signal that a window is already open
            warningAlert("An account window is already open", "window already open");
            accountStage.close();
        }
        accountStage.show();
    }

    /**
     * This method opens a new window for the user to create a new account unless an "account window" is already open (either "sign in"  or "create account")
     * @param e The event (button click) that triggers the method call
     */
    @FXML
    private void createAccount(ActionEvent e)
    {
        if(!accountStage.isShowing()){

            resetCreateAccountFields();
            accountScene.setRoot(createAccountPanel);
            accountStage.setTitle("Create a new account");
        }
        else{
            warningAlert("An account window is already open", "window already open");
            accountStage.close();
        }
        accountStage.show();
    }

    /**
     * This method closes the current window and opens the create account window
     * @param e The event (button click) that triggers the method call
     */
    @FXML
    private void createAccountLink(ActionEvent e)
    {
        accountStage.close();
        createAccount(e);
    }

    /**
     * This method retrieves the contents of the "create account" window's text fields.
     * It then checks whether they are valid according to certain restrictions
     * If the fields are valid, it creates a new account with the given credentials and updates the appropriate fields.
     * The account stage is closed
     * If a price range has been entered, then it closes all previously opened windows and loads the account's specific map view GUI elements.
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
            accountPanelController.setCurrentAccount(currentAccount);
            setProfileCircles();
            setAccountUsername(username);

            mapController.setCurrentAccount(currentAccount);

            accountBar.setRight(signedInBar);

            accountStage.close();

            if(welcomeController.isSearched()){
                closeAllPropertyWindows();
                loadAccount(currentAccount);
            }

        }

    }

    /**
     * This method retrieves the contents of the "sign in" window's text fields.
     * It then checks whether they are valid according to certain restrictions
     * If the fields are valid, it loads the account's data and settings and sets its profile picture and username
     * The stage is then closed
     * If a price range has been entered, then it closes all previously opened windows and loads the account's specific map view GUI elements.
     * @param e The event (button click) that triggers the method call
     */
    @FXML
    private void signInAction(ActionEvent e) throws IOException {
        String email = signInEmail.getText();
        String password = signInPassword.getText();

        if(checkValidityOfSignInFields(email, password)){
            currentAccount = getAccount(email);

            accountPanelController.setCurrentAccount(currentAccount);
            setProfileCircles();
            setAccountUsername(currentAccount.getUsername());

            mapController.setCurrentAccount(currentAccount);
            loadDataAndSettings(currentAccount);

            accountBar.setRight(signedInBar);

            accountStage.close();

            if(welcomeController.isSearched()){
                closeAllPropertyWindows();
                loadAccount(currentAccount);
            }
        }
    }

    /**
     * This method saves all data and settings of the current account and signs it out
     * It then sets the top of the main frame to the "signed out" layout and makes the account's drop-down menu invisible (i.e., the subPane)
     * If a price range has been entered, then it loads the default map view GUI elements
     * @param e The event (button click) that triggers the method call
     */
    @FXML
    private void signOutAction(ActionEvent e) throws IOException
    {
        saveAllSettingsAndData();
        closeAllAccountWindows();

        currentAccount = null;
        setAccountUsername("");

        mapController.setCurrentAccount(null);

        accountBar.setRight(signedOutBar);

        subPane.setVisible(false);

        if(welcomeController.isSearched()){
            closeAllPropertyWindows();
            setDefaultSettingsAndData();
        }

    }




    /**
     * This method throws an info alert via a method call to indicate to the user the password restrictions in place
     * @param e The event (button click) that triggers the method call
     */
    @FXML
    private void whatIsAStrongPasswordAction(ActionEvent e)
    {
        infoAlert(" Passwords must contain a lowercase letter, capital letter, number, symbol and must be at least 8 characters. It cannot contain any spaces.", "What is a strong password?");
    }

    /**
     * This method displays or hides the account's drop-down menu each time the profile circle is clicked (the one at the top right of the main frame)
     * @param e The mouse event that triggers the method call
     */
    @FXML
    private void profileClicked(MouseEvent e)
    {
        if (e.getButton() == MouseButton.PRIMARY) {
            subPane.setVisible(!subPane.isVisible());
        }
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
     * This method saves all data and settings of the current account by updating the appropriate fields in the account panel
     */
    private void saveAllSettingsAndData()
    {
        System.out.println("All account settings and data successfully saved");
    }

    /**
     * This method sets all the settings and data of the account to default values
     * It also loads the default map view GUI elements
     */
    private void setDefaultSettingsAndData() throws IOException
    {
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
        return (checkUsername(username, emailCreateAccountErrorLabel) && checkEmail(email) && checkPassword(password, confirmPassword, passwordCreateAccountErrorLabel));
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
    public boolean checkUsername(String username, Label label)
    {
        label.setText("");

        if(username.length() == 0){
            label.setText("Please choose a username ");
            return false;
        }
        for(Account account : listOfAccounts){
            if(username.equals(account.getUsername())){
                label.setText("This field is already taken by another account. Please choose another username");
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
    private boolean isValidEmailPattern(String email)
    {
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
    public boolean checkPassword(String password, String confirmPassword, Label label) {
        label.setText("");
        if (password.length() == 0) {
            label.setText("Please enter a password");
            return false;
        } else {
            return checkPasswordStrength(password, label) && checkPasswordEquality(password, confirmPassword, label);
        }
    }
    /**
     * This method returns whether the password entered in the "create account" window is valid or not, i.e., if it follows the right pattern to be strong enough
     * If it is not valid, it throws an alert and prints an error (in the corresponding error label)
     * @param password The password entered
     * @return true if the password entered is deemed strong and false otherwise
     */
    private boolean checkPasswordStrength(String password, Label label) {
        label.setText("");
        String regex = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!?.><:;])(?=\\S+$).{8,}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            label.setText("This password is too weak.");
            return false;
        } else {
            return true;
        }
    }

    /**
     * This method returns whether the password and confirmation password are equal or not. Prints an error (in the corresponding error label) if not.
     * If it is not valid, prints an error in the corresponding error label
     * @param password The password entered
     * @param confirmPassword The confirmation password entered
     * @return true if the password and confirmation password match, false otherwise
     */
    private boolean checkPasswordEquality(String password, String confirmPassword, Label label) {
        label.setText("");
        if (!password.equals(confirmPassword)) {
            label.setText("Passwords do not match");
            return false;
        } else {
            return true;
        }
    }


    /**
     * This method loads all GUI-related elements that are proper to the account (state of the save button ...)
     * @param account The account to be loaded
     */
    private void loadAccount(Account account) throws IOException
    {
        mapController.loadCurrentAccount(account);
    }

    /**
     * This method loads all the account's data and settings in the corresponding fields and displays the "signed out" Pane at the top of the main frame
     * @param account The account to be loaded
     */
    private void loadDataAndSettings(Account account)
    {
        accountUsername.setText(account.getUsername());
    }

    /**
     * This method resets all the text fields of the "sign in" window
     */
    private void resetSignInPanelFields()
    {
        signInEmail.setText("");
        signInPassword.setText("");
        emailSignInErrorLabel.setText("");
        passwordSignInErrorLabel.setText("");
    }

    /**
     * This method resets all the text fields of the "create account" window
     */
    private void resetCreateAccountFields()
    {
        createAccountUsername.setText("");
        createAccountEmail.setText("");
        createAccountPassword.setText("");
        createAccountConfirmPassword.setText("");
        usernameCreateAccountErrorLabel.setText("");
        emailCreateAccountErrorLabel.setText("");
        passwordCreateAccountErrorLabel.setText("");
        confirmPasswordCreateAccountErrorLabel.setText("");
    }

    /**
     * This method closes all windows opened when consulting AirBnB properties
     */
    private void closeAllPropertyWindows()
    {
        mapController.closeAllMapStages();
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

    /**
     * This method returns the account currently loaded. If none is loaded, it returns null
     * @return The current account object loaded
     */
    public Account getCurrentAccount() {
        return currentAccount;
    }

    /**
     * This method returns the MapController object currently used
     * @return The MapController instance used
     */
    public MapController getMapController() {
        return mapController;
    }

    /**
     * This method returns the WelcomeController object currently used
     * @return The WelcomeController instance used
     */
    public WelcomeController getWelcomeController() {
        return welcomeController;
    }

    /**
     * This method sets a comfortable margin between the account's drop-down menu and the main frame border
     */
    public void formatPopUpMenu() {
        StackPane.setMargin(subPane, new Insets(70,20,0,0));
    }

    /**
     * This method sets both the profile pictures to the one specified
     */
    public void setProfileCircles() {
        profileCircle.setFill(new ImagePattern(currentAccount.getProfilePicture()));
        profileCircle2.setFill(new ImagePattern(currentAccount.getProfilePicture()));
    }

    /**
     * This method sets the account's username to the one specified
     * @param newUsername The new username
     */
    public void setAccountUsername(String newUsername) {
        accountUsername.setText(newUsername);
    }

    @FXML
    public void accountSettingsAction() throws IOException
    {
        if(accountPanelStage.isShowing()){
            if(!isSettingsShowed){
                accountPanel.setCenter(accountPanelController.getAccountSettingsPane());
                subPane.setVisible(false);
                isSettingsShowed = true;
            }
            else{
                accountPanelStage.close();
            }
            accountPanelStage.show();
        }
        else{
            accountPanel.setCenter(accountPanelController.getAccountSettingsPane());
            accountPanelController.setStage(accountPanelStage);
            subPane.setVisible(false);
            accountPanelStage.show();
            isSettingsShowed = true;
        }

    }

    @FXML
    public void accountDetailsAction() throws IOException {
        accountPanelController.loadFavourites();
        accountPanelController.loadBookings();
        if(accountPanelStage.isShowing()){
            if(isSettingsShowed){
                accountPanel.setCenter(accountPanelController.getAccountDetailsPane());
                subPane.setVisible(false);
                accountPanelController.setStage(accountPanelStage);
                accountPanelController.resetAccountSettings();
                accountPanelStage.show();
                isSettingsShowed = false;
            }
            else{
                accountPanelStage.close();
            }
            accountPanelStage.show();
        }
        else{
            Pane accountDetails = accountPanelController.getAccountDetailsPane();
            accountPanel.setCenter(accountDetails);

            accountPanelController.setStage(accountPanelStage);
            subPane.setVisible(false);
            accountPanelStage.show();
            isSettingsShowed = false;
        }
    }

    private void closeAllAccountWindows() {

        for (PropertyPreviewController propertyPreviewController : this.currentAccount.getListOfPropertyPreviewControllers()) {
            if (propertyPreviewController.getPropertyStage() != null && propertyPreviewController.getPropertyStage().isShowing()) {
                propertyPreviewController.getPropertyStage().close();
            }
        }

        accountPanelStage.close();
    }



    public void changeUsername(String username)
    {
        currentAccount.setUsername(username);
        setAccountUsername(username);
    }

    public BorderPane getAccountPanel() {
        return accountPanel;
    }
}
