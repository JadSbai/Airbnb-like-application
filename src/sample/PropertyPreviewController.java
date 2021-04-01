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

    private Account account;

    private PropertyViewController viewController;

    public void initialize(AirbnbListing listing, Account account){
        this.listing = listing;
        this.hostName.setText(listing.getHost_name());
        this.price.setText("£" + listing.getPrice() +" / night");
        this.reviews.setText("" + listing.getNumberOfReviews());
        this.minimumNights.setText("Min. nights: " + listing.getMinimumNights());
        this.account = account;
    }

    /**
     * @param event created when clicking anywhere on the pane. Opens the listing previewed.
     * @throws IOException {@link java.io.IOException} in some circumstance
     */
    @FXML
    public void openProperty(MouseEvent event) throws IOException {
        FXMLLoader property = new FXMLLoader(getClass().getResource("AirbnbView.fxml"));
        Stage stage = property.load();

        String hostName = listing.getHost_name();
        if(hostName.endsWith("s")){
            stage.setTitle(hostName + "' Airbnb");
        }else{
            stage.setTitle(hostName + "'s Airbnb");
        }

        stage.show();

        viewController = property.getController();
        viewController.initialize(listing, account);
    }

    public void reload(AirbnbListing listing, Account account) throws IOException {
        FXMLLoader property = new FXMLLoader(getClass().getResource("AirbnbView.fxml"));
        Stage stage = property.load();
        viewController = property.getController();
        viewController.reload(listing, account);
    }
}
