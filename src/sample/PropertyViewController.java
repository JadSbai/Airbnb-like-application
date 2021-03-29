package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class PropertyViewController {

    private AirbnbListing listing;

    private boolean isAvailable;

    @FXML
    private Label nameAndHost, reviews, propertyType;

    @FXML
    private CheckBox saveBox;

    @FXML
    private DatePicker inDatePicker;
    @FXML
    private DatePicker outDatePicker;

    private LocalDate inDate, outDate;


    private boolean isFavourite;

    @FXML
    private Hyperlink locationLink;


    public void initialize(AirbnbListing listing){
        this.listing = listing;
        this.isAvailable = !(listing.getAvailability365()==0);
        this.isFavourite = false;

        this.nameAndHost.setText(listing.getName() + " - " + listing.getHost_name());
        this.propertyType.setText(listing.getRoom_type() + " in " + listing.getNeighbourhood());

        int numberOfReviews = listing.getNumberOfReviews();
        if (numberOfReviews==1) {
            this.reviews.setText(numberOfReviews + " review");
        } else {
            this.reviews.setText(numberOfReviews + " reviews");
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
    public void setInDate(ActionEvent e){
        LocalDate yesterday = LocalDate.now();
        yesterday = yesterday.minusDays(1);
        inDate = inDatePicker.getValue();
        if(yesterday.isBefore(inDate) && outDate!=null && outDate.isAfter(inDate)) {
            calculatePrice();
        } else if (outDate!=null && outDate.isBefore(inDate)) {
            invalidOptions("Check-in date must be before check-out date", "Invalid date");
        } else if (yesterday.isAfter(inDate)){
            invalidOptions("Check-in date must be later than yesterday", "Invalid date");
        } else if (outDate!= null && outDate.equals(inDate)) {
            invalidOptions("Check-in date must be different to check-out date", "Invalid date");
        }
    }

    @FXML
    public void setOutDate(ActionEvent e){
        LocalDate today = LocalDate.now();
        outDate = outDatePicker.getValue();
        if(today.isBefore(outDate) && inDate!=null && inDate.isBefore(outDate)) {
            calculatePrice();
        } else if (today.isAfter(outDate)) {
            invalidOptions("Check-out date must be later than today", "Invalid date");
        } else if (inDate!=null && inDate.isAfter(outDate)){
            invalidOptions("Check-out date must be after check-in date", "Invalid date");
        } else if (inDate!= null && inDate.equals(outDate)) {
            invalidOptions("Check-out date must be different to check-in date", "Invalid date");
        }
    }

    @FXML
    public void saveFavourites(){
        isFavourite = !isFavourite;
    }

    private void calculatePrice(){
        int numberOfNights = (int) DAYS.between(inDate, outDate);
        if(numberOfNights < listing.getMinimumNights()){
            invalidOptions("Minimum number of nights for this property is " + listing.getMinimumNights() + " you selected " + numberOfNights, "Insufficient nights");
        }
    }

    private void invalidOptions(String error, String errorTitle)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(errorTitle);
        alert.setHeaderText(null);
        alert.setContentText(error);

        alert.showAndWait();
    }

}
