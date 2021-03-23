package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MapController {

    @FXML
    private Button HAMM;

    public MapController(){

    }

    public void boroughSearch(ActionEvent event){
        String boroughAbreviation = ((Button) event.getSource()).getText();
    }
}
