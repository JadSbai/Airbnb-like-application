package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class BookingDetailsController
{

    /**
    * The labels for the booking description, subtotal, serviceFee, totalPrice, dateRange, roomType
    */
    @FXML
    private Label bookingDescription, subtotal, serviceFee, totalPrice, dateRange, roomType;

    /**
    * The booking details scene
    */
    @FXML
    private Scene bookingDetailsScene;

    /**
    * The booking details stage
    */
    @FXML
    private Stage bookingDetailsStage;

    /**
    * An AirbnbListing object
    */
    private AirbnbListing listing;

    /**
    * The dates of the check-in and check-out of the booking
    */
    private LocalDate inDate, outDate;


    public void initialize(AirbnbListing listing, LocalDate inDate, LocalDate outDate) throws IOException {
        
        this.listing = listing;
        this.inDate = inDate;
        this.outDate = outDate;

        setLabels();
    }

    /**
    * A method to initialize the labels
    */
    private void setLabels() {
        int numberOfNights = (int) DAYS.between(inDate, outDate);
        int price = listing.getPrice()*numberOfNights;
        int otherFees = (int) Math.round(price*0.2);
        int totalPrice = price+otherFees;
        
        this.bookingDescription.setText("You have a reservation for " + numberOfNights +" nights at " + listing.getHostNameWithApostrophe());
        this.dateRange.setText(inDate + " - " + outDate);
        this.roomType.setText("Room type: " + listing.getRoom_type());

        this.subtotal.setText("£" + price);
        this.serviceFee.setText("£" + otherFees);
        this.totalPrice.setText("£" + totalPrice);

    }
}
