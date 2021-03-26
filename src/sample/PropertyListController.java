package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class PropertyListController {

    private AirbnbDataLoader loader;

    private HBox hbox;

    private ListView listView;

    private ArrayList<AirbnbListing> listings;


    public PropertyListController(String boroughAbbreviation) throws IOException {
        //listView = new ListView();
        loader = new AirbnbDataLoader();
        listings = loader.loadFromBoruogh(boroughAbbreviation);
        //for(AirbnbListing listing: listings){
        //    listView.getItems().add("Item 1");
        //}
        //hbox = new HBox(listView);
        FXMLLoader propertyList = new FXMLLoader(getClass().getResource("AirbnbViewer.fxml"));
        Stage stage = propertyList.load();
        stage.setTitle("AirBnB's in " + boroughAbbreviation);
        stage.show();
    }
}
