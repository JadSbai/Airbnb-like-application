package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;

public class PropertyListController {

    @FXML
    private ListView<Pane> listView;


    public void initialize(ArrayList<AirbnbListing> boroughListings) throws IOException {

        for(AirbnbListing listing: boroughListings){
            FXMLLoader preview = new FXMLLoader(getClass().getResource("AirbnbPreview.fxml"));
            Pane propertyPane = preview.load();
            PropertyPreviewController propertyPreviewController = preview.getController();
            propertyPreviewController.initialize(listing);
            listView.getItems().add(propertyPane);
        }

    }



}
