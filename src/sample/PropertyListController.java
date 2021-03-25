package sample;

import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class PropertyListController {

    private AirbnbDataLoader loader;

    private HBox hbox;

    private ListView listView;

    private ArrayList<AirbnbListing> listings;


    public PropertyListController(String borough){
        listView = new ListView();
        listings = loader.loadFromBoruogh(borough);
        for(AirbnbListing listing: listings){

            listView.getItems().add("Item 1");
        }
        hbox = new HBox(listView);
    }
}
