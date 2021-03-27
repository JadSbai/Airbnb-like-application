package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class PropertyListController {

    private ArrayList<AirbnbListing> listings;

    @FXML
    private ListView listView;


    public void initialize(ArrayList<AirbnbListing> boroughListings, int minPrice, int maxPrice) throws IOException {

        System.out.println("" + minPrice + maxPrice);

        for(AirbnbListing listing: boroughListings){
            int listingPrice = listing.getPrice();
            if(listingPrice>minPrice && listingPrice<maxPrice) {
                FXMLLoader preview = new FXMLLoader(getClass().getResource("AirbnbPreview.fxml"));
                Pane propertyPane = preview.load();
                PropertyPreviewController propertyPreviewController = preview.getController();
                propertyPreviewController.initialize(listing);
                listView.getItems().add(propertyPane);
            }
        }

    }



}
