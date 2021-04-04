package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class PropertyPreviewController extends ListingController {

    @FXML
    private Label hostName;
    @FXML
    private Label price;
    @FXML
    private Label reviews;
    @FXML
    private Label minimumNights;

    private PropertyViewController viewController;

    private Stage propertyStage;

    private boolean isPropertyWindowOpen;

    public void initialize(AirbnbListing listing){
        super.initialize(listing);

        this.hostName.setText(getListing().getHost_name());
        this.price.setText("£" + getListing().getPrice() +" / night");
        this.reviews.setText("" + getListing().getNumberOfReviews());
        this.minimumNights.setText("Min. nights: " + getListing().getMinimumNights());
        isPropertyWindowOpen = false;
    }

    @FXML
    public void openProperty(MouseEvent e) throws IOException
    {
        if(!isPropertyWindowOpen) {

            FXMLLoader property = new FXMLLoader(getClass().getResource("AirbnbView.fxml"));
            propertyStage = property.load();


            propertyStage.setTitle(getListing().getHostNameWithApostrophe() + " Airbnb");
            propertyStage.show();

            isPropertyWindowOpen = true;
            propertyStage.setOnCloseRequest(event -> {
                        isPropertyWindowOpen = false;
                        viewController.setFavouriteTextLabel("");
                    }
            );

            viewController = property.getController();
            viewController.initialize(getListing());
        }
        else{
             propertyStage.close();
             propertyStage.show();
        }
    }

    public void reload(AirbnbListing listing) throws IOException {
        FXMLLoader property = new FXMLLoader(getClass().getResource("AirbnbView.fxml"));
        propertyStage = property.load();
        viewController = property.getController();
        viewController.reload(listing);
    }

    public Stage getPropertyStage() {
        return propertyStage;
    }


}
