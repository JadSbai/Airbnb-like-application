package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class MapController {



    public void initialize(){

    }

    @FXML
    public void boroughSearch(ActionEvent event) throws IOException {
        System.out.println("It's working baby!");
        String boroughAbreviation = ((Button) event.getSource()).getText();
        PropertyListController listOfProperties = new PropertyListController(boroughAbreviation);
    }
}
