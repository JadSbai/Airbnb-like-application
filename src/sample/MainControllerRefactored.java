package sample;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
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
public class MainControllerRefactored extends Controller
{

    @FXML
    private BorderPane mainPane;
    @FXML
    private Button leftButton;
    @FXML
    private Button rightButton;
    @FXML
    private Label currentPriceRangeLabel;
    @FXML
    private BorderPane accountBar;
    @FXML
    private ComboBox<Integer> minimumPrice, maximumPrice;
    @FXML
    private Button searchButton;

    private Pane mainRoot;

    private Pane welcomeRoot;
    private Pane mapRoot;
    private Pane statisticsRoot;

    private static final int MAX_VALUE = 500;
    private static final int MIN_VALUE = 0;

    private int minPrice;
    private int maxPrice;

    private boolean isSearched;
    private boolean isNewSearch;

    private ArrayList<Pane> listOfRoots;
    private int trackingIndex;
    private int mapRootIndex;

    private VBox dropDownRoot;

    private Pane signedOutBar;

    private MapControllerRefactored mapController;
    private StatisticsController statisticsController;

    public MainControllerRefactored(Account account) {
        super(account);
    }

    public void initialize() throws IOException
    {
        WelcomeControllerRefactored welcomeController = new WelcomeControllerRefactored(getAccount());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("WelcomeRefactored.fxml"));
        loader.setController(welcomeController);
        welcomeRoot = loader.load();

        welcomeController.setWelcomeRoot(welcomeRoot);

        mapController = new MapControllerRefactored(getAccount());
        loader = new FXMLLoader(getClass().getResource("Map.fxml"));
        loader.setController(mapController);
        mapRoot = loader.load();

        statisticsController = new StatisticsController(getAccount());
        loader = new FXMLLoader(getClass().getResource("Statistics.fxml"));
        loader.setController(statisticsController);
        statisticsRoot = loader.load();

        AccountStageController accountStageController = new AccountStageController(getAccount(), null, mapController);
        loader = new FXMLLoader(getClass().getResource("AccountStage.fxml"));
        loader.setController(accountStageController);
        Stage accountStage = loader.load();
        accountStageController.setAccountStage(accountStage);
        accountStageController.initializeControllers();

        DropDownMenuController dropDownMenuController = new DropDownMenuController(getAccount(), accountStage, accountStageController);
        loader = new FXMLLoader(getClass().getResource("AccountDropDownMenu.fxml"));
        loader.setController(dropDownMenuController);
        dropDownRoot = loader.load();

        StackPane.setMargin(dropDownRoot, new Insets(147,0,0,0));

        Label usernameLabel = dropDownMenuController.getAccountUsernameLabel();

        AccountAccessController accountAccessController = new AccountAccessController(getAccount(), accountStage);
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
        setCurrentAccount(null);

        rightButton.setDisable(true);
        leftButton.setDisable(true);
        listOfRoots = new ArrayList<>();

        isSearched = false;

        minimumPrice.setItems(FXCollections.observableArrayList(getPriceRange()));
        maximumPrice.setItems(FXCollections.observableArrayList(getPriceRange()));

        mainPane.setCenter(welcomeRoot);

        // This code of block has to be improved
        listOfRoots.add(0, welcomeRoot);
        listOfRoots.add(1, mapRoot);
        mapRootIndex = 1;
        listOfRoots.add(2, statisticsRoot);
        trackingIndex = 0;
    }

    public void setMainRoot(Pane root)
    {
        this.mainRoot = root;
        mainRoot.getChildren().add(dropDownRoot);
    }

    @FXML
    private void rightButtonAction(ActionEvent e)
    {
        trackingIndex = (trackingIndex + 1) % (listOfRoots.size());
        Pane nextPane = listOfRoots.get(trackingIndex);
        mainPane.setCenter(nextPane);

    }

    @FXML
    private void leftButtonAction()
    {
        trackingIndex = (trackingIndex - 1) % (listOfRoots.size());
        Pane previousPane = listOfRoots.get(trackingIndex);
        mainPane.setCenter(previousPane);
    }

    @FXML
    private void searchAction(ActionEvent e){

        boolean valid = (getIntFromBox(minimumPrice) && getIntFromBox(maximumPrice));
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

    private void jumpToNewMap()
    {
        mapController.closeAllMapStages();
        mapController.setColor();
        trackingIndex = mapRootIndex;
        mainPane.setCenter(mapRoot);
    }

    protected ArrayList<Integer> getPriceRange() {
        ArrayList<Integer> priceRange = new ArrayList<>();
        for (int i = MIN_VALUE; i <= MAX_VALUE; i = (int) (i + ((MAX_VALUE - MIN_VALUE)) * 0.1)) {
            priceRange.add(i);
        }
        return priceRange;
    }

    protected void invalidOptions(String error, String errorTitle)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(errorTitle);
        alert.setHeaderText(null);
        alert.setContentText(error);
        alert.showAndWait();
    }

    protected boolean getIntFromBox(ComboBox<Integer> box) {
        return (box.getValue() != null);
    }
    protected boolean isSearched() {
        return isSearched;
    }

    protected void setSearched(boolean searched) {
        isSearched = searched;
    }

    protected boolean isNewSearch() {
        return isNewSearch;
    }

    protected void setNewSearch(boolean newSearch) {
        isNewSearch = newSearch;
    }

    protected int getMinPrice() {
        return minPrice;
    }

    protected int getMaxPrice() {
        return maxPrice;
    }


    protected Pane getMainRoot() {
        return mainRoot;
    }

    protected Pane getWelcomeRoot() {
        return welcomeRoot;
    }

    protected Pane getMapRoot() {
        return mapRoot;
    }

    protected Pane getStatisticsRoot() {
        return statisticsRoot;
    }

    public BorderPane getAccountBar() {
        return accountBar;
    }

    public MapControllerRefactored getMapControllerRefactored() {
        return mapController;
    }
}
