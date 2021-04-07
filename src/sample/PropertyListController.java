package sample;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.*;

public class PropertyListController {

    private ControllerComponents controllerComponents;

    private ArrayList<PropertyPreviewController> listOfPropertyPreviewControllers;

    private static final String hostNameOrderString = "Name of host";
    private static final String priceLowestOrderString = "Lowest to highest price";
    private static final String priceHighestOrderString = "Highest to lowest price";
    private static final String reviewOrderString = "Number of reviews";

    @FXML
    private ListView<Pane> listView;
    @FXML
    private ChoiceBox<String> sortByChoiceBox;

    private List<EntryString> hostNameOrder;
    private List<EntryInteger> priceOrder;
    private List<EntryInteger> invertedPriceOrder;
    private List<EntryInteger> numberOfReviewsOrder;

    AccountDetailsController accountDetailsController;

    private ArrayList<AirbnbListing> boroughListings;

    public PropertyListController(ControllerComponents controllerComponents, AccountDetailsController accountDetailsController) {
        this.controllerComponents = controllerComponents;
        this.accountDetailsController = accountDetailsController;
    }
    
    public void initialize(ArrayList<AirbnbListing> boroughListings) throws IOException
    {
        this.boroughListings = boroughListings;
        listOfPropertyPreviewControllers = new ArrayList<>();
        createChoiceBoxOptions();
        createSortedLists();

        for (AirbnbListing listing : boroughListings) {
            FXMLLoader preview = new FXMLLoader(getClass().getResource("AirbnbPreview.fxml"));
            PropertyPreviewController propertyPreviewController = new PropertyPreviewController(controllerComponents, listing, accountDetailsController);
            preview.setController(propertyPreviewController);
            Pane propertyPreviewPane = preview.load();

            listOfPropertyPreviewControllers.add(propertyPreviewController);

            listView.getItems().add(propertyPreviewPane);
            addToSortedLists(listing, propertyPreviewPane);
        }

        sortLists();

        if (boroughListings.isEmpty()){ emptyListSettings(); }
    }

    public void reload() throws IOException {
        for(PropertyPreviewController propertyPreviewController: listOfPropertyPreviewControllers){
            if(propertyPreviewController != null){
                 propertyPreviewController.reload();
            }
        }
    }

    public void closePropertyStages()
    {
        for(PropertyPreviewController propertyPreviewController : listOfPropertyPreviewControllers){
            if(propertyPreviewController.getPropertyStage() != null && propertyPreviewController.getPropertyStage().isShowing()){
                propertyPreviewController.getPropertyStage().close();
            }
        }
    }

    public ArrayList<PropertyPreviewController> getListOfPropertyPreviewControllers() {
        return listOfPropertyPreviewControllers;
    }

    /**
     * To be able to sort the panes by the characteristics of the listing they represent, lists are created which are
     * made of entries of class EntryInteger or EntryString which can be ordered using their compareTo method like a
     * HashMap would (but a map doesn't store identical values and a priority queue doesn't have a key-value structure
     * in java)
     */
    private void createSortedLists() {
        hostNameOrder = new ArrayList<>();
        priceOrder = new ArrayList<>();
        numberOfReviewsOrder = new ArrayList<>();
    }

    /**
     * Once all the listings are added, this method sorts them in numerical or alphabetical order.
     * The compareTo method withing the entries is utilised to establish this order
     */
    private void sortLists() {
        Collections.sort(hostNameOrder);
        Collections.sort(priceOrder);
        Collections.sort(numberOfReviewsOrder);
        Collections.reverse(numberOfReviewsOrder);
        invertedPriceOrder = new ArrayList<>(priceOrder);
        Collections.reverse(invertedPriceOrder);
    }
    /**
     * Creates a pane with labels to display a message indicating that the list of properties is empty because of the
     * filters selected by the user. Disables the "sort by" choice box.
     */
    private void emptyListSettings() {
        Pane emptyListMessage = new VBox();
        Label noResults = new Label("No results :(");
        Label noResultsMessage = new Label("Try reducing your filters to get more results.");

        noResults.setStyle("-fx-font: bold 16pt \"Trebuchet MS\";");
        noResults.setPadding(new Insets(10, 0, 5, 10));

        noResultsMessage.setPadding(new Insets(0, 0, 10, 10));


        emptyListMessage.getChildren().addAll(noResults, noResultsMessage);
        listView.getItems().add(emptyListMessage);

        sortByChoiceBox.setDisable(true);
    }

    /**
     * Method to add a pane and it's relevant variable to each list. These lists will then be sorted according to their variable
     * @param listing for which one of its variables will be taken (price, host_name..)
     * @param propertyPane the visualisation of the listing that will be displayed in the order dictated by these lists
     */
    private void addToSortedLists(AirbnbListing listing, Pane propertyPane) {
        hostNameOrder.add(new EntryString(listing.getHost_name(), propertyPane));
        priceOrder.add(new EntryInteger(listing.getPrice(), propertyPane));
        numberOfReviewsOrder.add(new EntryInteger(listing.getNumberOfReviews(), propertyPane));
    }

    /**
     * Creates the options for the "sort by" choice box using static strings to improve maintainability.
     */
    private void createChoiceBoxOptions() {
        List<String> orderingOptions = Arrays.asList(hostNameOrderString, priceLowestOrderString, priceHighestOrderString, reviewOrderString);
        sortByChoiceBox.setItems(FXCollections.observableArrayList(orderingOptions));
    }

    /**
     * Method to determine the type of sort to establish when sorting is required according to the option in the
     * "sort by" choice box.
     * @param event created when any option of the choice box "sort by" is clicked
     */
    @FXML
    public void orderListings(ActionEvent event) {
        String selectedSort = sortByChoiceBox.getValue();

        switch (selectedSort) {
            case hostNameOrderString:
                sortListByString(hostNameOrder);
                break;
            case priceLowestOrderString:
                sortListByInteger(priceOrder);
                break;
            case priceHighestOrderString:
                sortListByInteger(invertedPriceOrder);
                break;
            case reviewOrderString:
                sortListByInteger(numberOfReviewsOrder);
                break;
        }
    }

    /**
     * Method to set the listView (list of panes) to the order established by the orderListings method
     * @param orderType list that carries the order of the panes, of type String.
     */
    private void sortListByString(List<EntryString> orderType) {
        int numberOfProperties = listView.getItems().size();
        listView.getItems().remove(0, numberOfProperties);
        int index = 0;
        for (EntryString entry : orderType) {
            listView.getItems().add(index, entry.getValue());
            index++;
        }
    }

    /**
     * Method to set the listView (list of panes) to the order established by the orderListings method
     * @param orderType list that carries the order of the panes, of type int.
     */
    private void sortListByInteger(List<EntryInteger> orderType) {
        int numberOfProperties = listView.getItems().size();
        listView.getItems().remove(0, numberOfProperties);
        int index = 0;
        for (EntryInteger entry : orderType) {
            listView.getItems().add(index, entry.getValue());
            index++;
        }
    }
}