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


public class AccountDetailsController
{

    private ControllerComponents controllerComponents;
    
    @FXML
    private ListView<Pane> listOfFavourites;
    @FXML
    private Label emptyListLabel;
    @FXML
    private ListView<BorderPane> listOfBookings;

    @FXML
    private Label emptyListLabel2;

    private HashMap<BorderPane, Stage> bookingToBookingDetailsMap;

    public AccountDetailsController(ControllerComponents controllerComponents) {
        this.controllerComponents = controllerComponents;
        bookingToBookingDetailsMap = new HashMap<>();
    }

    public void initialize() throws IOException
    {
        
    }

    public void reloadFavouritesAndBookings() throws IOException {
        loadFavourites();
        loadBookings();
    }

    public void loadFavourites() throws IOException
    {
        listOfFavourites.getItems().clear();
        listOfFavourites.setItems(FXCollections.observableArrayList(controllerComponents.getAccount().getListOfFavourites()));
        if(listOfFavourites.getItems().isEmpty()){
            emptyListLabel.setText("You currently have no favourites. Click on the \"Save\" button to add a favourite.");
        } else {
            emptyListLabel.setText("");
        }
    }



    @FXML
    private void viewDetailsAction(ActionEvent e) throws IOException
    {
        BorderPane bookingPane = getBookingFromButton(e);
        AirbnbListing listing = controllerComponents.getAccount().getBookingToListingMap().get(bookingPane);
        Stage bookingDetailsStage = controllerComponents.getAccount().getListingToBookingDetailsMap().get(listing);

        bookingToBookingDetailsMap.put(bookingPane, bookingDetailsStage);

        if(bookingDetailsStage.isShowing()){
            bookingDetailsStage.close();
        }
        else{
            bookingDetailsStage.show();
        }
    }

    @FXML
    private void cancelBookingAction(ActionEvent e)
    {
        BorderPane booking = getBookingFromButton(e);
        controllerComponents.getAccount().removeFromBookings(booking);
        Stage bookingDetailsStage = bookingToBookingDetailsMap.get(booking);
        if(bookingDetailsStage.isShowing()){
            bookingDetailsStage.close();
        }
        loadBookings();
    }

    private BorderPane getBookingFromButton(ActionEvent e)
    {
        return (BorderPane) ((Button) e.getSource()).getParent().getParent();
    }

    public void loadBookings()
    {
        listOfBookings.getItems().clear();
        listOfBookings.setItems(FXCollections.observableArrayList(controllerComponents.getAccount().getListOfBookingPanes()));
        if(listOfBookings.getItems().isEmpty()){
            emptyListLabel2.setText("You currently have no bookings. Click on the \"Reserve\" button to add a booking.");
        } else {
            emptyListLabel2.setText("");
        }
    }

}
