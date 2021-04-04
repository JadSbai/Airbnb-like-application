package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class PropertyPreviewController {

    AirbnbListing listing;

    @FXML
    private Label hostName;
    @FXML
    private Label price;
    @FXML
    private Label reviews;
    @FXML
    private Label minimumNights;

    private Account currentAccount;

    private PropertyViewController viewController;

    private Stage propertyStage;

    private boolean isPropertyWindowOpen;

    public void initialize(AirbnbListing listing, Account account){
        this.listing = listing;
        this.hostName.setText(listing.getHost_name());
        this.price.setText("£" + listing.getPrice() +" / night");
        this.reviews.setText("" + listing.getNumberOfReviews());
        this.minimumNights.setText("Min. nights: " + listing.getMinimumNights());
        this.currentAccount = account;
        isPropertyWindowOpen = false;
    }

    @FXML
    public void openProperty(MouseEvent e) throws IOException
    {
        if(!isPropertyWindowOpen) {

            FXMLLoader property = new FXMLLoader(getClass().getResource("AirbnbView.fxml"));
            propertyStage = property.load();


            propertyStage.setTitle(listing.getHostNameWithApostrophe() + " Airbnb");
            propertyStage.show();

            isPropertyWindowOpen = true;
            propertyStage.setOnCloseRequest(event -> {
                        isPropertyWindowOpen = false;
                        viewController.setFavouriteTextLabel("");
                    }
            );

            viewController = property.getController();
            viewController.initialize(listing, currentAccount);
        }
        else{
             propertyStage.close();
             propertyStage.show();
        }
    }

    public void reload(AirbnbListing listing, Account account) throws IOException {
        FXMLLoader property = new FXMLLoader(getClass().getResource("AirbnbView.fxml"));
        propertyStage = property.load();
        viewController = property.getController();
        currentAccount = account;
        viewController.reload(listing, account);
    }

    public Stage getPropertyStage() {
        return propertyStage;
    }


}
