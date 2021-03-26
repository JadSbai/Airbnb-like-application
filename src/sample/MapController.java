package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MapController {


    public void initialize(){

    }

    @FXML
    public void boroughSearch(ActionEvent event) throws IOException {
        String boroughAbbreviation = ((Button) event.getSource()).getText();

        FXMLLoader propertyList = new FXMLLoader(getClass().getResource("AirbnbViewer.fxml"));
        Stage stage = propertyList.load();
        stage.setTitle("AirBnB's in " + boroughAbbreviation);
        stage.show();

        //PropertyListController listOfProperties = propertyList.getController();
        //listOfProperties.initialize(boroughAbbreviation);
    }
}
