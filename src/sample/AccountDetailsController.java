package sample;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;


/**
 * This class holds all fields and methods necessary for handling the favourites and bookings.
 * @author Jacqueline Ilie, Liam Clark Gutiérrez, Dexter Trower and Jad Sbaï
 * @version 08/04/2021
 */
public class AccountDetailsController
{

    /**
    * A ControllerComponents object
    */
    private ControllerComponents controllerComponents;

    /**
     * The ListView of propertyPreview panes of the favourite properties
     */
    @FXML
    private ListView<Pane> listOfFavourites;

    /**
     * The label displayed when the list of favourites is empty
     */
    @FXML
    private Label emptyListOfFavouritesLabel;

    /**
     * The ListView of borderPanes of the booked properties
     */
    @FXML
    private ListView<BorderPane> listOfBookings;

    /**
     * The label displayed when the list of bookings is empty
     */
    @FXML
    private Label emptyListLabel2;

    /**
     * HashMap that maps the bookings to their booking details window
     */
    private HashMap<BorderPane, Stage> bookingToBookingDetailsMap;
    

    /**
     * The constructor initializes the non-FXML fields
     * @param controllerComponents
     */
    public AccountDetailsController(ControllerComponents controllerComponents)
    {
        this.controllerComponents = controllerComponents;
        bookingToBookingDetailsMap = new HashMap<>();
    }

    /**
     * This method is called automatically when loading the file which has this class specified as controller.
     * In this case, nothing needs to be initialized (already done in the constructor)
     * @throws IOException when the fxml file calling this method is not loaded successfully
     */
    public void initialize() throws IOException
    {
    }


    /**
     * This method reloads the properties displayed as favourites based on the current account's list of favourites
     * @throws IOException when the file for loading the preview panes is not loaded successfully
     */
    public void loadFavourites()
    {
        listOfFavourites.getItems().clear();
        listOfFavourites.setItems(FXCollections.observableArrayList(controllerComponents.getAccount().getListOfFavourites()));
        if(listOfFavourites.getItems().isEmpty()){
            emptyListOfFavouritesLabel.setText("You currently have no favourites. Click on the \"Save\" button to add a favourite.");
        } else {
            emptyListOfFavouritesLabel.setText("");
        }
    }

    /**
     * This method reloads the properties displayed as bookings based on the current account's list of bookings
     */
    public void loadBookings()
    {
        listOfBookings.getItems().clear();
        // Updates the list of bookings to the list of bookings from the current account
        listOfBookings.setItems(FXCollections.observableArrayList(controllerComponents.getAccount().getListOfBookingPanes()));

        if(listOfBookings.getItems().isEmpty()){
            emptyListLabel2.setText("You currently have no bookings. Click on the \"Reserve\" button to add a booking.");
        } else {
            emptyListLabel2.setText("");
        }
    }

    /**
     * This method reloads the properties displayed as favourites and bookings based on the current account's list of favourites and bookings
     * @throws IOException when the file for loading the preview panes is not loaded successfully
     */
    public void reloadFavouritesAndBookings()
    {
        loadFavourites();
        loadBookings();
    }

    /**
     * This method reloads the properties displayed as favourites based on the current account's list of favourites
     * @param e
     * @throws IOException when the file for loading the preview panes is not loaded successfully
     */
    @FXML
    private void viewDetailsAction(ActionEvent e)
    {
        BorderPane bookingPane = getBookingFromButton(e);
        AirbnbListing listing = controllerComponents.getAccount().getBookingToListingMap().get(bookingPane);
        Stage bookingDetailsStage = controllerComponents.getAccount().getListingToBookingDetailsMap().get(listing);
        controllerComponents.getAccount().getListOfBookingDetailsStages().add(bookingDetailsStage);

        bookingToBookingDetailsMap.put(bookingPane, bookingDetailsStage);

        if(bookingDetailsStage.isShowing()){
            bookingDetailsStage.close();
        }
        else{
            bookingDetailsStage.show();
        }
    }

    /**
     * This method cancels a booking.
     * @param e the click event
     */
    @FXML
    private void cancelBookingAction(ActionEvent e)
    {
        // Get the booking from the button clicked
        BorderPane booking = getBookingFromButton(e);
        // remove the booking
        controllerComponents.getAccount().removeFromBookings(booking);
        Stage bookingDetailsStage = bookingToBookingDetailsMap.get(booking);
        // Close the booking details stage associated to the booking if it's open
        if(bookingDetailsStage != null && bookingDetailsStage.isShowing()){
            bookingDetailsStage.close();
        }
        // reload the bookings
        loadBookings();
    }

    /**
     * this method returns the booking associated with the "view details" button clicked
     * @param e the click event
     * @return The booking (BorderPane) to which the button belongs to
     */
    private BorderPane getBookingFromButton(ActionEvent e)
    {
        return (BorderPane) ((Button) e.getSource()).getParent().getParent();
    }
}
