package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountController extends Controller
{
    @FXML
    private BorderPane accountPanel;

    private Stage accountStage;

    private MainControllerRefactored mainControllerRefactored;

    private MapControllerRefactored mapControllerRefactored;

    private Pane accountSettings;

    private Pane accountDetails;

    private Stage accountAccessStage;

    private ArrayList<Account> listOfAccounts;

    private HashMap<String, Account> accountsMap;

    @FXML
    private Label accountUsernameLabel;

    @FXML
    private Circle profileCircle;

    /**
     * Profile circle situated in the account's drop down menu containing the profile picture
     */
    @FXML
    private Circle profileCircle2;

    @FXML
    private Pane dropDownPane;

    private Pane signedOutBar;

    private Pane signedInBar;

    private Pane signInPanel;

    private Pane createAccountPanel;


    public void initialize() throws IOException
    {
        listOfAccounts = new ArrayList<>();
        accountsMap = new HashMap<>();



        FXMLLoader loader = new FXMLLoader(getClass().getResource("SignedOutBar.fxml"));
        signedOutBar = loader.load();
        AccountAccessController accountAccessController = loader.getController();

        FXMLLoader signedInLoader = new FXMLLoader(getClass().getResource("SignedInBar.fxml"));
        signedInLoader.setController(accountAccessController);
        signedInBar = signedInLoader.load();

        FXMLLoader createAccountStage = new FXMLLoader(getClass().getResource("AccountAccessStage.fxml"));
        createAccountStage.setController(accountAccessController);
        accountAccessStage = createAccountStage.load();

        FXMLLoader signInPanelLoader = new FXMLLoader(getClass().getResource("SignInPanel.fxml"));
        signInPanelLoader.setController(accountAccessController);
        signInPanel = signInPanelLoader.load();

        FXMLLoader createAccountPanelLoader = new FXMLLoader(getClass().getResource("CreateAccountPanel.fxml"));
        createAccountPanelLoader.setController(accountAccessController);
        createAccountPanel = createAccountPanelLoader.load();

        FXMLLoader accountSettingsLoader = new FXMLLoader(getClass().getResource("AccountSettings.fxml"));
        setAccountSettings(accountSettingsLoader.load());
        AccountSettingsController accountSettingsController = accountSettingsLoader.getController();
        accountSettingsController.initialize();

        FXMLLoader accountDetailsLoader = new FXMLLoader(getClass().getResource("AccountDetails.fxml"));
        accountDetails = accountDetailsLoader.load();


        formatDropDownMenu();
    }

    public void setAccountStage(Stage accountStage)
    {
        this.accountStage = accountStage;
    }


    /**
     * This method saves all data and settings of the current account and signs it out
     * It then sets the top of the main frame to the "signed out" layout and makes the account's drop-down menu invisible (i.e., the subPane)
     * If a price range has been entered, then it loads the default map view GUI elements
     */
    @FXML
    protected void signOutAction() throws IOException {

        saveAllSettingsAndData();
        closeAllAccountWindows();

        setCurrentAccount(null);
        setAccountUsernameLabel("");

        setAccountBar(getSignedOutBar());

        getDropDownPane().setVisible(false);

        if(isSearched()){
            closeAllPropertyWindows();
            setDefaultSettingsAndData();
        }
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
        loadAccount(null);
    }

    /**
     * This method loads all GUI-related elements that are proper to the account (state of the save button ...)
     * @param account The account to be loaded
     */
    protected void loadAccount(Account account) throws IOException
    {
        mapControllerRefactored.loadCurrentAccount(getAccount());
    }

    /**
     * This method loads all the account's data and settings in the corresponding fields and displays the "signed out" Pane at the top of the main frame
     * @param account The account to be loaded
     */
    protected void loadDataAndSettings(Account account)
    {
        setAccountUsernameLabel(account.getUsername());
    }

    private void closeAllAccountWindows() {
        for (PropertyPreviewController propertyPreviewController : getAccount().getListOfPropertyPreviewControllers()) {
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


    @FXML
    private void goToAccountSettings()
    {
        accountPanel.setCenter(accountSettings);
        accountStage.sizeToScene();
    }

    @FXML
    private void goToAccountDetails() throws IOException {
        //loadFavourites();
        //loadBookings();
        accountPanel.setCenter(accountDetails);
        accountStage.sizeToScene();
    }

    @FXML
    public void openAccountSettings() throws IOException
    {
        accountPanel.setCenter(getAccountSettings());
        dropDownPane.setVisible(false);
        if(accountStage.isShowing()){
            accountStage.close();
        }
        accountStage.show();

    }

    @FXML
    public void openAccountDetails() throws IOException {
        //loadFavourites();
        //loadBookings();
        accountPanel.setCenter(getAccountDetails());
        dropDownPane.setVisible(false);
        if(accountStage.isShowing()){
            accountStage.close();
        }
        accountStage.show();
    }


    public void changeUsername(String username)
    {
        getAccount().setUsername(username);
        setAccountUsernameLabel(username);
    }

    /**
     * This method sets both the profile pictures to the one specified
     */
    public void setProfileCircles() {
        profileCircle.setFill(new ImagePattern(getAccount().getProfilePicture()));
        profileCircle2.setFill(new ImagePattern(getAccount().getProfilePicture()));
    }

    /**
     * This method sets the account's username to the one specified
     * @param newUsername The new username
     */
    public void setAccountUsernameLabel(String newUsername) {
        accountUsernameLabel.setText(newUsername);
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
     * This method sets a comfortable margin between the account's drop-down menu and the main frame border
     */
    public void formatDropDownMenu() {
        StackPane.setMargin(getDropDownPane(), new Insets(70,20,0,0));
    }




    protected void setAccountBar(Pane pane)
    {
        mainControllerRefactored.getAccountBar().setRight(pane);
    }

    protected boolean isSearched()
    {
        return mainControllerRefactored.isSearched();
    }

    public Pane getAccountDetails() {
        return accountDetails;
    }

    public void setAccountDetails(Pane accountDetails) {
        this.accountDetails = accountDetails;
    }

    public Pane getAccountSettings() {
        return accountSettings;
    }

    public void setAccountSettings(Pane accountSettings) {
        this.accountSettings = accountSettings;
    }

    public BorderPane getAccountPanel() {
        return accountPanel;
    }

    public Stage getAccountStage() {
        return accountStage;
    }

    /**
     * List of current accounts created in the app
     */
    public ArrayList<Account> getListOfAccounts() {
        return listOfAccounts;
    }

    /**
     * Maps the email of each account to the corresponding account object
     */
    public HashMap<String, Account> getAccountsMap() {
        return accountsMap;
    }

    /**
     * Label situated in the account's drop down menu (i.e., the subPane field) that displays the account's username
     */
    public Label getAccountUsernameLabel() {
        return accountUsernameLabel;
    }

    public Pane getDropDownPane() {
        return dropDownPane;
    }

    public Pane getSignedOutBar() {
        return signedOutBar;
    }

    public Pane getSignedInBar() {
        return signedInBar;
    }

    public void setMapControllerRefactored(MainControllerRefactored mainControllerRefactored) {
        this.mainControllerRefactored = mainControllerRefactored;
        mapControllerRefactored = this.mainControllerRefactored.getMapControllerRefactored();
        setAccountBar(signedOutBar);
    }

    /**
     * Scene loaded when a user wants to sign in their account
     */
    public Pane getSignInPanel() {
        return signInPanel;
    }

    /**
     * Scene loaded when a user wants to create a new account
     *
     */
    public Pane getCreateAccountPanel() {
        return createAccountPanel;
    }

    public Stage getAccountAccessStage() {
        return accountAccessStage;
    }
}
