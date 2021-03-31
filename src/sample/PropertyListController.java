package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;

public class PropertyListController {

    private PropertyPreviewController propertyPreviewController;

    @FXML
    private ListView<Pane> listView;


    public void initialize(ArrayList<AirbnbListing> boroughListings, Account currentAccount) throws IOException {

        for(AirbnbListing listing: boroughListings){
            FXMLLoader preview = new FXMLLoader(getClass().getResource("AirbnbPreview.fxml"));
            Pane propertyPane = preview.load();
            propertyPreviewController = preview.getController();
            propertyPreviewController.initialize(listing, currentAccount);
            listView.getItems().add(propertyPane);
        }

    }

    public void reload(ArrayList<AirbnbListing> boroughListings, Account currentAccount) throws IOException {
        for(AirbnbListing listing: boroughListings) {
            propertyPreviewController.reload(listing, currentAccount);
        }
    }



}
