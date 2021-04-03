package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class BookingController
{

    @FXML
    private Label totalPrice;
    @FXML
    private Label subTotalPrice;
    @FXML
    private Label serviceFee;
    @FXML
    private Label numberOfNights;
    @FXML
    private Label inDate, outDate;

    @FXML
    private Scene bookingDetailsScene;

    @FXML
    private Stage bookingDetailsStage;

    private Button reserveButton;

    private Account currentAccount;

    private AirbnbListing listing;

    public void initialize(AirbnbListing listing, Button reserveButton, Account currentAccount, int subTotalPrice, int serviceFee, int totalPrice, int numberOfNights, LocalDate inDate, LocalDate outDate) throws IOException {
        FXMLLoader bookingDetailsLoader = new FXMLLoader(getClass().getResource("BookingDetails.fxml"));
        bookingDetailsLoader.setController(this);
        bookingDetailsLoader.load();
        this.subTotalPrice.setText("Subtotal price: " + subTotalPrice);
        this.serviceFee.setText("Service fee: " + serviceFee);
        this.totalPrice.setText("Total price: " + totalPrice);
        this.numberOfNights.setText("Number of nights: " + numberOfNights);
        this.inDate.setText("Date of arrival: " + inDate);
        this.outDate.setText("Date of departure: " + outDate);
        this.reserveButton = reserveButton;
        this.currentAccount = currentAccount;
        this.listing = listing;
        bookingDetailsStage.setTitle("Booking details of " + listing.getHost_name() + "'s property");
    }

    @FXML
    private void viewDetailsAction()
    {
        if(bookingDetailsStage.isShowing()){
            bookingDetailsStage.close();
        }
        else{
            bookingDetailsStage.show();
        }
    }

    @FXML
    private void cancelBookingAction()
    {
        currentAccount.removeFromBookings(listing);
        reserveButton.setDisable(false);
    }
}
