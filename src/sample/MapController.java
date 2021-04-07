package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * This class is the controller for the map panel. The map panel shows each borough of london in the form of hexagons
 * that can be clicked to view the listings in that borough.
 * @author Jacqueline Ilie, Liam Clark Gutiérrez, Dexter Trower and Jad Sbaï
 * @version 07/04/2021
 */
public class MapController
{
    /**
     * Common instance of ControllerComponents held by all classes containing common elements like the account.
     */
    private final ControllerComponents controllerComponents;

    /**
     * Buttons for each of the boroughs
     */
    @FXML
    private Button ENFI, BARN, HRGY, WALT, HRRW, BREN, CAMD, ISLI, HACK, REDB, HAVE, HILL, EALI, KENS, WSTM, TOWH, NEWH, BARK, HOUN, HAMM, WAND, CITY, GWCH, BEXL, RICH, MERT, LAMB, STHW, LEWS, KING, SUTT, CROY, BROM;

    /**
     * List of hexagonal buttons
     */
    private ArrayList<Button> hexagons;

    /**
     * Instance of a property preview controller just opened
     */
    private ListingListController listOfProperties;
    /**
     * List of all the lists of previews open at any point
     */
    private ArrayList<ListingListController> listOfListingListControllers;

    /**
     * Instance of a list of previews stage
     */
    private Stage propertyListStage;
    /**
     * List of all the stages of lists of previews open at any point.
     */
    private ArrayList<Stage> listOfPropertyListStages;

    /**
     * Minimum and maximum price for the search as defined in the main controller
     */
    private int minPrice, maxPrice;

    /**
     * Controller for AccountDetailsController used to reload the panel if favourites or bookings change.
     */
    private AccountDetailsController accountDetailsController;

    /**
     * Contrustor for map contorller.
     * @param controllerComponents common instance of ControllerComponents held by all classes containing common elements like the account.
     */
    public MapController(ControllerComponents controllerComponents){
        this.controllerComponents = controllerComponents;
    }


    /**
     * Initializes map controller. Creates lists.
     */
    public void initialize()
    {
        hexagons = new ArrayList<>(Arrays.asList(ENFI, BARN, HRGY, WALT, HRRW, BREN, CAMD, ISLI, HACK, REDB, HAVE, HILL, EALI, KENS, WSTM, TOWH, NEWH, BARK, HOUN, HAMM, WAND, CITY, GWCH, BEXL, RICH, MERT, LAMB, STHW, LEWS, KING, SUTT, CROY, BROM));
        listOfPropertyListStages = new ArrayList<>();
        listOfListingListControllers = new ArrayList<>();
    }

    /**
     * Opens the list of previews of a borough when any borough is clicked.
     * @param event created when any hexagon borough button is clicked
     * @throws IOException {@link IOException} in some circumstance
     */
    @FXML
    public void boroughSearch(ActionEvent event) throws IOException
    {
        String boroughAbbreviation = ((Button) event.getSource()).getText();
        ArrayList<AirbnbListing> boroughListings = ControllerComponents.getDataLoader().loadFromBoroughAtPrice(boroughAbbreviation, minPrice, maxPrice);

        if(!listOfPropertyListStages.isEmpty()){


            ListingListController listingListController = new ListingListController(controllerComponents, accountDetailsController);
            FXMLLoader propertyList = new FXMLLoader(getClass().getResource("ListingList.fxml"));
            setPropertyListStage(propertyList, listingListController, boroughAbbreviation);

            if(!propertyListStage.isShowing()) {
                initializePropertyListStage(propertyList, boroughListings);
            }
            else{
                propertyListStage.close();
                propertyListStage.show();
            }
        }
        else{
            loadAndInitializePropertyListStage(boroughAbbreviation, boroughListings);
        }
    }

    /**
     * Loads and initializes the stage for the list of listings for a certain borough
     * @param boroughAbbreviation abbreviation of a borough
     * @param boroughListings list of listing in a borough
     * @throws IOException {@link IOException} in some circumstance
     */
    private void loadAndInitializePropertyListStage(String boroughAbbreviation, ArrayList<AirbnbListing> boroughListings) throws IOException
    {
        ListingListController listingListController = new ListingListController(controllerComponents, accountDetailsController);
        FXMLLoader propertyList = new FXMLLoader(getClass().getResource("ListingList.fxml"));
        setPropertyListStage(propertyList, listingListController, boroughAbbreviation);
        initializePropertyListStage(propertyList, boroughListings);
    }

    /**
     * Sets the stage for list of properties including the title to the full name
     * @param propertyList fxml loader
     * @param controller controller for the list of listing previews being set
     * @param boroughAbbreviation abbreviation of a borough
     * @throws IOException {@link IOException} in some circumstance
     */
    private void setPropertyListStage(FXMLLoader propertyList, ListingListController controller, String boroughAbbreviation) throws IOException
    {
        propertyList.setController(controller);
        propertyListStage = propertyList.load();
        propertyListStage.setTitle("AirBnB's in " + AirbnbListing.getFullBoroughName(boroughAbbreviation));
    }

    /**
     *  Initializes the list of listing previews for a certain borough
     * @param propertyList fxml loader
     * @param boroughListings list of listing in a borough
     * @throws IOException {@link IOException} in some circumstance
     */
    private void initializePropertyListStage(FXMLLoader propertyList, ArrayList<AirbnbListing> boroughListings ) throws IOException
    {
        listOfPropertyListStages.add(propertyListStage);
        listOfProperties = propertyList.getController();
        listOfListingListControllers.add(listOfProperties);
        listOfProperties.initialize(boroughListings);
        propertyListStage.show();
   }

    /**
     * Sets the color of the map. The transparency determines the number of listings.
     */
    public void setColor(){
        int maxSize = 0;
        for (Button borough: hexagons) {
            int numberOfPropertiesInBorough = ControllerComponents.getDataLoader().loadFromBoroughAtPrice(borough.getText(), minPrice, maxPrice).size();
            if(maxSize<numberOfPropertiesInBorough){
                maxSize = numberOfPropertiesInBorough;
            }
        }

        for (Button borough: hexagons) {
            int boroughSize = ControllerComponents.getDataLoader().loadFromBoroughAtPrice(borough.getText(), minPrice, maxPrice).size();
            String hexTransparency = Integer.toHexString((int) ((boroughSize*0.001/maxSize)*255000));
            if(hexTransparency.toCharArray().length==1){
                hexTransparency = "0" + hexTransparency;
            }
            String colour = "#FF5A60";
            borough.setStyle("-fx-background-color: " + colour + hexTransparency + ";");
        }
    }

    /**
     * Closes all stages of everything when circumstances change. (ex. change of price)
     */
    public void closeAllMapStages()
    {
        closeAllPropertyStages();
        closeAllPropertyListStages();
        clearAllTrackingLists();
    }

    /**
     * Closes all listing stages
     */
    private void closeAllPropertyStages()
    {
        for(ListingListController listingListController : listOfListingListControllers){
            if(listingListController != null){
                listingListController.closePropertyStages();
            }
        }
    }

    /**
     * Closes all stages of lists of previews
     */
    private void closeAllPropertyListStages()
    {
        Iterator<Stage> iterator = listOfPropertyListStages.iterator();
        Stage stage;
        while (iterator.hasNext()) {
            stage = iterator.next();
            stage.close();
        }
    }

    /**
     * Clears all the stages and controllers.
     */
    private void clearAllTrackingLists()
    {
        listOfPropertyListStages.clear();
        listOfListingListControllers.clear();
    }

    /**
     * Loads a new account or a change in the current account (reload method) with consequences in the view controller.
     * @throws IOException {@link IOException} in some circumstance
     */
    public void loadCurrentAccount() throws IOException {
        for(ListingListController listingListController : listOfListingListControllers){
            if(listingListController != null){
                listingListController.reload();
            }
        }
    }

    /**
     * Sets or resets the price range
     * @param minPrice minPrice
     * @param maxPrice maxPrice
     */
    public void setPriceRange(int minPrice, int maxPrice)
    {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    /**
     * Setting the accountDetailsController from the accountStageController when initialized
     * @param accountDetailsController  accountDetailsController
     */
    public void addAccountController(AccountDetailsController accountDetailsController){
        this.accountDetailsController = accountDetailsController;
    }
}
