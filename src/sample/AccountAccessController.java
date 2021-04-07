package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * This class is in charge of all the GUI's containers and controls related to the creation and usage of an AirBnB account.
 * It tracks the text fields' values and updates the main window's top part according to the current state of the account (signed in or signed out)
 * It also manages all secondary window's meant for signing in or creating an AirBnB account
 * It also manages the different accounts created so far in the app
 * @author Jacqueline Ilie, Liam Clark Gutiérrez, Dexter Trower and Jad Sbaï
 * @version 29/03/2021
 */
public class AccountAccessController extends AccountController
{
    private ControllerComponents controllerComponents;
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

    @FXML
    private Scene accountAccessScene;

    private Stage accountAccessStage;

    private Pane signedOutBar;

    private Pane signedInBar;

    private Pane signInPanel;

    private Pane createAccountPanel;

    private Pane dropDownPane;

    private BorderPane accountBar;

    private Label userNameLabel;

    private MapControllerRefactored mapControllerRefactored;

    @FXML
    private Circle profileCircle;

    public AccountAccessController(ControllerComponents controllerComponents, Stage accountStage)
    {
        super(controllerComponents, accountStage);
        this.controllerComponents = controllerComponents;
    }

    /**
     * The constructor initializes all the non-FXML fields, loads all the account related fxml files, sets their controller and displays the resulting stage.
     * @throws IOException if the designated files are not loaded successfully
     */

    @FXML
    public void initialize() throws IOException
    {

    }


    /**
     * This method opens a new window for the user to sign in into their account unless an "account window" is already open (either "sign in"  or "create account")
     * @param e The event (button click) that triggers the method call
     */
    @FXML
    private void signIn(ActionEvent e)
    {
        if(!accountAccessStage.isShowing()) {

            resetSignInPanelFields();
            accountAccessScene.setRoot(signInPanel);
            accountAccessStage.setTitle("Sign in");
        }
        else{
            // Throws an alert to signal that a window is already open
            warningAlert("An account window is already open", "window already open");
            accountAccessStage.close();
        }
        accountAccessStage.show();
    }

    /**
     * This method opens a new window for the user to create a new account unless an "account window" is already open (either "sign in"  or "create account")
     * @param e The event (button click) that triggers the method call
     */
    @FXML
    private void createAccount(ActionEvent e)
    {
        if(!accountAccessStage.isShowing()){

            resetCreateAccountFields();
            accountAccessScene.setRoot(createAccountPanel);
            accountAccessStage.setTitle("Create a new account");
        }
        else{
            warningAlert("An account window is already open", "window already open");
            accountAccessStage.close();
        }
        accountAccessStage.show();
    }

    /**
     * This method closes the current window and opens the create account window
     * @param e The event (button click) that triggers the method call
     */
    @FXML
    private void createAccountLink(ActionEvent e)
    {
        accountAccessStage.close();
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
            Account newAccount = new Account(username, email, password, controllerComponents);
            getListOfAccounts().add(newAccount);
            getAccountsMap().put(email, newAccount);

            controllerComponents.setCurrentAccount(newAccount);
            updateProfilePictures();
            changeUsername(username);

            accountBar.setRight(signedInBar);

            accountAccessStage.close();

            closeAllPropertyWindows();
            loadAccount();
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
            controllerComponents.setCurrentAccount(getAccount(email));

            accountBar.setRight(signedInBar);

            accountAccessStage.close();

            closeAllPropertyWindows();
            loadAccount();

        }
    }

    /**
     * This method saves all data and settings of the current account and signs it out
     * It then sets the top of the main frame to the "signed out" layout and makes the account's drop-down menu invisible (i.e., the subPane)
     * If a price range has been entered, then it loads the default map view GUI elements
     */
    @FXML
    protected void signOutAction() throws IOException {

        closeAllAccountWindows();

        controllerComponents.setCurrentAccount(null);
        userNameLabel.setText("");

        accountBar.setRight(signedOutBar);

        dropDownPane.setVisible(false);

        closeAllPropertyWindows();
        setDefaultSettingsAndData();

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
     * This method returns whether the fields of the "create account" window are valid or not
     * @param username The username entered
     * @param email The email entered
     * @param  password The password entered
     * @param confirmPassword the password for confirmation entered
     * @return true if the fields are valid and false otherwise
     */
    private boolean checkValidityOfCreateAccountFields(String username, String email, String password, String confirmPassword)
    {
        return (checkAccountUsername(username, emailCreateAccountErrorLabel) && checkEmail(email) && checkPassword(password, confirmPassword, passwordCreateAccountErrorLabel));
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
            if(getAccount(email) == null){
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
        for (Account account : getListOfAccounts()) {
            if (password.equals(account.getPassword())) {
                return true;
            }
        }
        passwordSignInErrorLabel.setText("This email is not associated to this password.");
        return false;
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
        for(Account account : getListOfAccounts()){
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
     * This method displays or hides the account's drop-down menu each time the profile circle is clicked (the one at the top right of the main frame)
     * @param e The mouse event that triggers the method call
     */
    @FXML
    private void profileClicked(MouseEvent e)
    {
        if (e.getButton() == MouseButton.PRIMARY) {
            dropDownPane.setVisible(!dropDownPane.isVisible());
        }
    }




    /**
     * This method returns whether the username entered in the "create account" window is valid or not, i.e., if it is not already taken by another account
     * If it is not valid, prints an error in the corresponding error label
     * @param username The username entered
     * @return true if the username entered isn't already taken by another account and false otherwise
     */
    public boolean checkAccountUsername(String username, Label label)
    {
        label.setText("");

        if(username.length() == 0){
            label.setText("Please choose a username ");
            return false;
        }
        for(Account account : getListOfAccounts()){
            if(username.equals(account.getUsername())){
                label.setText("This field is already taken by another account. Please choose another username");
                return false;
            }
        }
        return true;
    }

    /**
     * This method loads (or reloads) all GUI-related elements of the listing views that are proper to the account (state of the save button ...)
     */
    protected void loadAccount() throws IOException
    {
        mapControllerRefactored.loadCurrentAccount();
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
        controllerComponents.setCurrentAccount(null);
        loadAccount();
    }

    private void closeAllAccountWindows() {
        for (PropertyPreviewController propertyPreviewController : controllerComponents.getAccount().getListOfPropertyPreviewControllers()) {
            if (propertyPreviewController.getPropertyStage() != null && propertyPreviewController.getPropertyStage().isShowing()) {
                propertyPreviewController.getPropertyStage().close();
            }
        }
        getAccountStage().close();
    }



    /**
     * This method closes all windows opened when consulting AirBnB properties
     */
    protected void closeAllPropertyWindows()
    {
        mapControllerRefactored.closeAllMapStages();
    }

    /**
     * This method returns the account instance associated to the specified email address
     * @return The account associated to the given email address
     */
    private Account getAccount(String email)
    {
        return getAccountsMap().get(email);
    }

    public void setSignedInBar(Pane signedInBar) {
        this.signedInBar = signedInBar;
        AccountComponents.getInstance().getAccountCircles().add(profileCircle);
    }

    public void setSignInPanel(Pane signInPanel) {
        this.signInPanel = signInPanel;
    }

    public void setCreateAccountPanel(Pane createAccountPanel) {
        this.createAccountPanel = createAccountPanel;
    }

    public void setAccountAccessStage(Stage accountAccessStage) {
        this.accountAccessStage = accountAccessStage;
    }

    public void setSignedOutBar(Pane signedOutBar) {
        this.signedOutBar = signedOutBar;
    }

    public void setDropDownPane(Pane dropDownPane) {
        this.dropDownPane = dropDownPane;
    }

    public void setAccountBar(BorderPane accountBar) {
        this.accountBar = accountBar;
    }

    public void setUserNameLabel(Label userNameLabel) {
        this.userNameLabel = userNameLabel;
    }

    public void setMapControllerRefactored(MapControllerRefactored mapControllerRefactored) {
        this.mapControllerRefactored = mapControllerRefactored;
    }
}
