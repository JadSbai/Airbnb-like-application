package sample;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is in charge of all the GUI's containers and controls related to the creation and usage of an AirBnB account.
 * It tracks the text fields' values and updates the main window's top part according to the current state of the account (signed in or signed out)
 * It also manages all secondary window's meant for signing in or creating an AirBnB account
 * It also manages the different accounts created so far in the app
 * @author Jacqueline Ilie, Liam Clark Gutiérrez, Dexter Trower and Jad Sbaï
 * @version 29/03/2021
 */
public class MainController
{
    private ControllerComponents  controllerComponents;
    
    /**
    * The main pane of the panel consists of a BorderPane
    */
    @FXML
    private BorderPane mainPane;
    
    /**
    * The left and right buttons, which switch between the current panel to the previous/next one
    */
    @FXML
    private Button leftButton, rightButton;
    
    /**
     * A label to display the current selected price range
     */
    @FXML
    private Label currentPriceRangeLabel;
    
    /**
     * The account bar consists of a BorderPane
     */
    @FXML
    private BorderPane accountBar;
    
    /**
     * The 2 ComboBoxes where the minimum and maximum price are selected
     */  
    @FXML
    private ComboBox<Integer> minimumPrice, maximumPrice;
    
    /**
     * The search button
     */
    @FXML
    private Button searchButton;

    /**
     * The welcome root pane
     */
    private Pane welcomeRoot;
    
    /**
     * The map root pane
     */
    private Pane mapRoot;
    
    /**
     * The statistics root pane
     */
    private Pane statisticsRoot;

    /**
    * The minimum and maximum prices that can be set in the price range, as shown in the
    * drop-down menu of the ComboBoxes
    */
    private static final int MIN_VALUE = 0, MAX_VALUE = 500;

    /**
    * The minimum and maximum prices selected in order to set the price range
    */
    private int minPrice, maxPrice;

    /**
    * Keeps track of whether the search button should be disabled or not
    */
    private boolean isSearched;

    /**
     * Keeps track of whether a new search has been made
     */
    private boolean isNewSearch;

    /**
     * A list of all the roots
     */
    private ArrayList<Pane> listOfRoots;

    /**
     *  The drop-down root
     */
    private VBox dropDownRoot;

    /**
     * The signed out bar pane
     */
    private Pane signedOutBar;

    /**
     * The indexes of each of the panels, in order
     */
    private static final int welcomeIndex = 0, mapIndex = 1, statisticsIndex = 2;

    /**
     * The index to keep track of the current panel
     */
    private int trackingIndex;

    /**
    * A MapController object
    */
    private MapController mapController;

    /**
    * A StatisticsController object
    */
    private StatisticsController statisticsController;

    /**
     * A method to initialize the way the GUI should first appear when the application is run
     * @param primaryStage
     * @throws IOException
     */
    public void initialize(Stage primaryStage) throws IOException
    {
        controllerComponents = new ControllerComponents(null);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("WelcomePanel.fxml"));
        welcomeRoot = loader.load();
        WelcomeController welcomeController = loader.getController();
        welcomeController.initialize(mainPane);

        mapController = new MapController(controllerComponents);
        loader = new FXMLLoader(getClass().getResource("MapPanel.fxml"));
        loader.setController(mapController);
        mapRoot = loader.load();
        BorderPane.setAlignment(mapRoot, Pos.CENTER);

        statisticsController = new StatisticsController(controllerComponents);
        loader = new FXMLLoader(getClass().getResource("StatisticsPanel.fxml"));
        loader.setController(statisticsController);
        statisticsRoot = loader.load();

        AccountStageController accountStageController = new AccountStageController(controllerComponents, null, mapController);
        loader = new FXMLLoader(getClass().getResource("AccountStage.fxml"));
        loader.setController(accountStageController);
        Stage accountStage = loader.load();
        accountStageController.setAccountStage(accountStage);
        accountStageController.initializeControllers();

        DropDownMenuController dropDownMenuController = new DropDownMenuController(controllerComponents, accountStage, accountStageController);
        loader = new FXMLLoader(getClass().getResource("AccountDropDownMenu.fxml"));
        loader.setController(dropDownMenuController);
        dropDownRoot = loader.load();

        StackPane.setMargin(dropDownRoot, new Insets(147,0,0,0));
        StackPane mainRoot = ((StackPane) primaryStage.getScene().getRoot());
        mainRoot.getChildren().add(dropDownRoot);

        Label usernameLabel = dropDownMenuController.getAccountUsernameLabel();

        AccountAccessController accountAccessController = new AccountAccessController(controllerComponents, accountStage);
        loader = new FXMLLoader(getClass().getResource("SignedOutBar.fxml"));
        loader.setController(accountAccessController);
        signedOutBar = loader.load();
        accountAccessController.setSignedOutBar(signedOutBar);

        FXMLLoader signedInLoader = new FXMLLoader(getClass().getResource("SignedInBar.fxml"));
        signedInLoader.setController(accountAccessController);
        accountAccessController.setSignedInBar(signedInLoader.load());

        FXMLLoader createAccountStage = new FXMLLoader(getClass().getResource("AccountAccessStage.fxml"));
        createAccountStage.setController(accountAccessController);
        accountAccessController.setAccountAccessStage(createAccountStage.load());

        FXMLLoader signInPanelLoader = new FXMLLoader(getClass().getResource("SignInPanel.fxml"));
        signInPanelLoader.setController(accountAccessController);
        accountAccessController.setSignInPanel(signInPanelLoader.load());

        FXMLLoader createAccountPanelLoader = new FXMLLoader(getClass().getResource("CreateAccountPanel.fxml"));
        createAccountPanelLoader.setController(accountAccessController);
        accountAccessController.setCreateAccountPanel(createAccountPanelLoader.load());

        accountAccessController.setDropDownPane(dropDownRoot);
        accountAccessController.setAccountBar(accountBar);
        accountAccessController.setUserNameLabel(usernameLabel);
        accountAccessController.setMapControllerRefactored(mapController);

        accountBar.setRight(signedOutBar);

        rightButton.setDisable(true);
        leftButton.setDisable(true);
        listOfRoots = new ArrayList<>();

        isSearched = false;

        minimumPrice.setItems(FXCollections.observableArrayList(getPriceRange()));
        maximumPrice.setItems(FXCollections.observableArrayList(getPriceRange()));

        mainPane.setCenter(welcomeRoot);


        listOfRoots = new CircularList<>(){};
        listOfRoots.add(welcomeIndex, welcomeRoot);
        listOfRoots.add(mapIndex, mapRoot);
        listOfRoots.add(statisticsIndex, statisticsRoot);
        trackingIndex = welcomeIndex;
    }

    /**
    * When the right button is pressed, the next panel is displayed.
    */
    @FXML
    private void rightButtonAction(ActionEvent e)
    {
        trackingIndex++;
        Pane nextPane = listOfRoots.get(trackingIndex);
        mainPane.setCenter(nextPane);
    }

    /**
    * When the left button is pressed, the previous panel is displayed.
    */
    @FXML
    private void leftButtonAction()
    {
        trackingIndex--;
        if(trackingIndex == -1){
            trackingIndex = listOfRoots.size() - 1;
        }
        Pane previousPane = listOfRoots.get(trackingIndex);
        mainPane.setCenter(previousPane);
    }

    /**
    * The search button is pressed, after a valid price range has been introduced.
    */
    @FXML
    private void searchAction(ActionEvent e){

        boolean valid = (isIntInBoxValid(minimumPrice) && isIntInBoxValid(maximumPrice));
        boolean isNewPrice = false;

        if (valid)
        {
            int temp1 = minPrice;
            int temp2 = maxPrice;
            minPrice = minimumPrice.getValue();
            maxPrice = maximumPrice.getValue();

            if(temp1 != minPrice || temp2 != maxPrice){
                isNewPrice = true;
            }
            else{
                invalidOptions("Please input an new price range before searching", "Same price range !");
            }
        }
        if (valid && maxPrice > minPrice && isNewPrice)
        {
            currentPriceRangeLabel.setText("Price range: " + minPrice + "-" + maxPrice);
            mapController.setPriceRange(minPrice, maxPrice);
            statisticsController.setPriceRange(minPrice, maxPrice);
            isNewSearch = true;
            if(!isSearched){
                rightButton.setDisable(false);
                leftButton.setDisable(false);
                mapController.setColor();
                rightButtonAction(e);
                setSearched(true);
            }
            else{
                jumpToNewMap();
            }

        }
        else if(valid && isNewPrice)
        {
            invalidOptions("Minimum price may not exceed or equal maximum price.", "Invalid price range!");
        }
        else if(!valid)
        {
            invalidOptions("Please input a price range first.", "Invalid price range!");
        }
    }

    /**
    * Checks whether a value has been selected for each ComboBox: a minimum or maximum price
    * from the drop-down menu
    * @return  true if a value has been selected, false otherwise
    */
    private boolean isIntInBoxValid(ComboBox<Integer> box) {
        return (box.getValue() != null);
    }

    /**
     * A method to show the map panel
     */
    private void jumpToNewMap()
    {
        mapController.closeAllMapStages();
        mapController.setColor();
        trackingIndex = mapIndex;
        mainPane.setCenter(mapRoot);
    }

    /**
     * This method computes the list of prices the user can select from when selecting the price range
     * @return the list of prices the user can select from when selecting the price range
     */
    private ArrayList<Integer> getPriceRange() {
        ArrayList<Integer> priceRange = new ArrayList<>();
        for (int i = MIN_VALUE; i <= MAX_VALUE; i = (int) (i + ((MAX_VALUE - MIN_VALUE)) * 0.1)) {
            priceRange.add(i);
        }
        return priceRange;
    }

    /**
     *  An error window is displayed
     * @param error
     * @param errorTitle
     */
    protected void invalidOptions(String error, String errorTitle)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(errorTitle);
        alert.setHeaderText(null);
        alert.setContentText(error);
        alert.showAndWait();
    }

    /**
     * Sets the status of the search button (if it should be disabled or not) * @param searched
     */
    private void setSearched(boolean searched) {
        isSearched = searched;
    }
}
