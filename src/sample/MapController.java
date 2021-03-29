package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class MapController {

    private WelcomeController welcomeController;
    private AirbnbDataLoader dataLoader;

    @FXML
    private Button ENFI, BARN, HRGY, WALT, HRRW, BREN, CAMD, ISLI, HACK, REDB, HAVE, HILL, EALI, KENS, WSTM, TOWH, NEWH, BARK, HOUN, HAMM, WAND, CITY, GWCH, BEXL, RICH, MERT, LAMB, STHW, LEWS, KING, SUTT, CROY, BROM;

    private ArrayList<Button> hexagons;

    public void initialize(WelcomeController welcomeController){
        this.welcomeController = welcomeController;
        this.dataLoader = new AirbnbDataLoader();
        this.hexagons = new ArrayList<>(Arrays.asList(ENFI, BARN, HRGY, WALT, HRRW, BREN, CAMD, ISLI, HACK, REDB, HAVE, HILL, EALI, KENS, WSTM, TOWH, NEWH, BARK, HOUN, HAMM, WAND, CITY, GWCH, BEXL, RICH, MERT, LAMB, STHW, LEWS, KING, SUTT, CROY, BROM));

    }

    @FXML
    public void boroughSearch(ActionEvent event) throws IOException {
        String boroughAbbreviation = ((Button) event.getSource()).getText();
        ArrayList<AirbnbListing> boroughListings = dataLoader.loadFromBoroughAtPrice(boroughAbbreviation, welcomeController.getMinPrice(), welcomeController.getMaxPrice());

        FXMLLoader propertyList = new FXMLLoader(getClass().getResource("AirbnbViewerList.fxml"));
        Stage stage = propertyList.load();
        stage.setTitle("AirBnB's in " + boroughAbbreviation);
        stage.show();

        PropertyListController listOfProperties = propertyList.getController();
        listOfProperties.initialize(boroughListings);
    }

    public void setColor(){
        int maxSize = 0;
        for (Button borough: hexagons) {
            int numberOfPropertiesInBorough = dataLoader.loadFromBoroughAtPrice(borough.getText(), welcomeController.getMinPrice(), welcomeController.getMaxPrice()).size();
            if(maxSize<numberOfPropertiesInBorough){
                maxSize = numberOfPropertiesInBorough;
            }
        }

        for (Button borough: hexagons) {
            int boroughSize = dataLoader.loadFromBoroughAtPrice(borough.getText(), welcomeController.getMinPrice(), welcomeController.getMaxPrice()).size();
            String hexTransparency = Integer.toHexString((int) ((boroughSize*0.001/maxSize)*255000));
            if(hexTransparency.toCharArray().length==1){
                hexTransparency = "0" + hexTransparency;
            }
            String colour = "#FF5A60";
            borough.setStyle("-fx-background-color: " + colour + hexTransparency + ";");
        }
    }
}
