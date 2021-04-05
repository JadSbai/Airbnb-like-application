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

public class BookingController
{

    @FXML
    private Label bookingDescription, subtotal, serviceFee, totalPrice, dateRange, roomType;

    @FXML
    private Scene bookingDetailsScene;

    @FXML
    private Stage bookingDetailsStage;

    private Button reserveButton;

    private Account currentAccount;

    private AirbnbListing listing;

    @FXML
    private ListView<BorderPane> listOfBookings;

    @FXML
    private Label emptyListLabel2;

    public void initialize(AirbnbListing listing, Button reserveButton, Account currentAccount, LocalDate inDate, LocalDate outDate) throws IOException {

        FXMLLoader bookingDetailsLoader = new FXMLLoader(getClass().getResource("BookingDetails.fxml"));
        bookingDetailsLoader.setController(this);
        bookingDetailsLoader.load();

        setLabels(listing, inDate, outDate);

        this.reserveButton = reserveButton;
        this.currentAccount = currentAccount;
        this.listing = listing;
    }

    private void setLabels(AirbnbListing listing, LocalDate inDate, LocalDate outDate) {
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

    public void loadBookings()
    {
        emptyListLabel2.setText("");
        listOfBookings.setItems(currentAccount.getListViewOfBookings().getItems());
        if(listOfBookings.getItems().isEmpty()){
            emptyListLabel2.setText("You currently have no bookings. Click on the \"Reserve\" button to add a booking.");
        }
    }
}
