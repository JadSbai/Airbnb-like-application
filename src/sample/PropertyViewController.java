package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class PropertyViewController {

    private AirbnbListing listing;

    private boolean isAvailable;

    @FXML
    private Label nameAndHost;
    @FXML
    private Label reviews;
    @FXML
    private Label propertyType;

    @FXML
    private CheckBox saveBox;

    private boolean isFavourite;

    @FXML
    private Hyperlink locationLink;


    public void initialize(AirbnbListing listing){
        this.listing = listing;
        this.isAvailable = !(listing.getAvailability365()==0);

        this.nameAndHost.setText(listing.getName() + " - " + listing.getHost_name());
        this.propertyType.setText(listing.getRoom_type() + " in " + listing.getNeighbourhood());

        int numberOfReviews = listing.getNumberOfReviews();
        if (numberOfReviews==1) {
            this.reviews.setText(listing.getNumberOfReviews() + " review");
        } else {
            this.reviews.setText(listing.getNumberOfReviews() + " reviews");
        }

    }

    @FXML
    public void viewMap(ActionEvent e) throws URISyntaxException, IOException {
        double latitude = listing.getLatitude();
        double longitude = listing.getLongitude();

        URI uri = new URI("https://www.google.com/maps/place/" + latitude + "," + longitude);
        java.awt.Desktop.getDesktop().browse(uri);

    }

    @FXML
    public void saveFavourites(){
        isFavourite = !isFavourite;
    }

}
