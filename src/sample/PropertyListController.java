package sample;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.*;

public class PropertyListController {

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

    private PropertyPreviewController propertyPreviewController;


    public void initialize(ArrayList<AirbnbListing> boroughListings, Account currentAccount) throws IOException {
        createChoiceBoxOptions();
        createSortedLists();

        long startTime = System.currentTimeMillis();

        for(AirbnbListing listing: boroughListings){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AirbnbPreview.fxml"));
            Pane propertyPane = loader.load();

            PropertyPreviewController propertyPreviewController = loader.getController();
            propertyPreviewController.initialize(listing, currentAccount);

            listView.getItems().add(propertyPane);

            addToSortedLists(listing, propertyPane);
        }

        long totalTime = System.currentTimeMillis() - startTime;
        System.out.println(totalTime);
        System.out.println(boroughListings.size());

        sortLists();
    }

    public void reload(ArrayList<AirbnbListing> boroughListings, Account currentAccount) throws IOException {
        for(AirbnbListing listing: boroughListings) {
            propertyPreviewController.reload(listing, currentAccount);
        }
    }

    private void createSortedLists() {
        hostNameOrder = new ArrayList<>();
        priceOrder = new ArrayList<>();
        numberOfReviewsOrder = new ArrayList<>();
    }

    private void sortLists() {
        Collections.sort(hostNameOrder);
        Collections.sort(priceOrder);
        Collections.sort(numberOfReviewsOrder);
        Collections.reverse(numberOfReviewsOrder);
        invertedPriceOrder = new ArrayList<>(priceOrder);
        Collections.reverse(invertedPriceOrder);
    }

    private void addToSortedLists(AirbnbListing listing, Pane propertyPane) {
        hostNameOrder.add(new EntryString(listing.getHost_name(), propertyPane));
        priceOrder.add(new EntryInteger(listing.getPrice(), propertyPane));
        numberOfReviewsOrder.add(new EntryInteger(listing.getNumberOfReviews(), propertyPane));
    }

    private void createChoiceBoxOptions() {
        List<String> orderingOptions = Arrays.asList(hostNameOrderString, priceLowestOrderString, priceHighestOrderString, reviewOrderString);
        sortByChoiceBox.setItems(FXCollections.observableArrayList(orderingOptions));
    }

    @FXML
    public void orderItems(ActionEvent e){
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
    private void sortListByString(List<EntryString> orderType){
        int numberOfProperties = listView.getItems().size();
        listView.getItems().remove(0, numberOfProperties);
        int index = 0;
        for (EntryString entry: orderType) {
            listView.getItems().add(index, entry.getValue());
            index++;
        }
    }
    private void sortListByInteger(List<EntryInteger> orderType){
        int numberOfProperties = listView.getItems().size();
        listView.getItems().remove(0, numberOfProperties);
        int index = 0;
        for (EntryInteger entry: orderType) {
            listView.getItems().add(index, entry.getValue());
            index++;
        }
    }



}
