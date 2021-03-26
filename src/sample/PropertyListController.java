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

    public PropertyListController(){

    }

    public void initialize(String boroughAbbreviation) throws IOException {
        AirbnbDataLoader loader = new AirbnbDataLoader();
        listings = loader.loadFromBoruogh(boroughAbbreviation);

        int count = 0;


        for(AirbnbListing listing: listings){
            FXMLLoader preview = new FXMLLoader(getClass().getResource("AirbnbPreview.fxml"));
            Pane propertyPane = preview.load();
            listView.getItems().add(propertyPane);
            count++;
        }
        System.out.println(count);

    }



}
