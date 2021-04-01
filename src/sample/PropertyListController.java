package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class PropertyListController {

    private PropertyPreviewController propertyPreviewController;

    private ArrayList<PropertyPreviewController> listOfPropertyPreviewControllers;

    @FXML
    private ListView<Pane> listView;


    public void initialize(ArrayList<AirbnbListing> boroughListings, Account currentAccount) throws IOException {
        listOfPropertyPreviewControllers = new ArrayList<>();

        for (AirbnbListing listing : boroughListings) {
            FXMLLoader preview = new FXMLLoader(getClass().getResource("AirbnbPreview.fxml"));
            Pane propertyPane = preview.load();
            propertyPreviewController = preview.getController();
            listOfPropertyPreviewControllers.add(propertyPreviewController);
            propertyPreviewController.initialize(listing, currentAccount);
            listView.getItems().add(propertyPane);
        }

    }

    public void reload(ArrayList<AirbnbListing> boroughListings, Account currentAccount) throws IOException {
        for (AirbnbListing listing : boroughListings) {
            propertyPreviewController.reload(listing, currentAccount);
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
}
