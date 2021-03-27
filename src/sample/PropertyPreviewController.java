package sample;

import javafx.fxml.FXML;

import javafx.scene.control.Label;

public class PropertyPreviewController {

    @FXML
    private Label hostName;

    @FXML
    private Label price;

    public void initialize(AirbnbListing airbnbListing){
        this.hostName.setText(airbnbListing.getHost_name());
        this.price.setText("£" + airbnbListing.getPrice() +" / night");
    }
}
