package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;


public class MapController {

    private WelcomeController welcomeController;
    private AirbnbDataLoader dataLoader;

    public void initialize(WelcomeController welcomeController){
        this.welcomeController = welcomeController;
        this.dataLoader = new AirbnbDataLoader();
    }

    @FXML
    public void boroughSearch(ActionEvent event) throws IOException {
        String boroughAbbreviation = ((Button) event.getSource()).getText();
        ArrayList<AirbnbListing> boroughListings = dataLoader.loadFromBorough(boroughAbbreviation);

        FXMLLoader propertyList = new FXMLLoader(getClass().getResource("AirbnbViewerList.fxml"));
        Stage stage = propertyList.load();
        stage.setTitle("AirBnB's in " + boroughAbbreviation);
        stage.show();

        PropertyListController listOfProperties = propertyList.getController();
        listOfProperties.initialize(boroughListings, welcomeController.getMinPrice(), welcomeController.getMaxPrice());
    }
}
