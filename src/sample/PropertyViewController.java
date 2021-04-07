package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;

import static java.time.temporal.ChronoUnit.DAYS;

public class PropertyViewController extends ListingController {

    private final ControllerComponents controllerComponents;
    @FXML
    private Label nameAndHost, reviews, propertyType, priceAndNights, subtotal, serviceFeeValue, totalPriceLabel, availability, availabilityText;

    @FXML
    private ImageView availabilityIcon;

    @FXML
    private Button reserveButton;

    @FXML
    private CheckBox saveBox;

    @FXML
    private GridPane dateSelectPane;

    @FXML
    private DatePicker inDatePicker, outDatePicker;

    private LocalDate inDate, outDate;

    @FXML
    private Hyperlink locationLink;
    @FXML
    private Label favouriteTextLabel;
    
    private final AccountDetailsController accountDetailsController;

    public PropertyViewController(ControllerComponents controllerComponents, AirbnbListing listing, AccountDetailsController accountDetailsController)
    {
        super(listing);
        this.controllerComponents = controllerComponents;
        this.accountDetailsController = accountDetailsController;
    }

    public void initialize() throws IOException
    {
        favouriteTextLabel.setText("");

        setHeader();
        setAvailability();
        setReviews();

        if(controllerComponents.getAccount() == null){
            getListing().setFavourite(false);
            saveBox.setSelected(false);
        }
        else{
            initializeFavourites();
        }
        this.priceAndNights.setText("£" + getListing().getPrice() + " / night");
    }
    /**
     * Set the text for the header of the listing's view. Shows room type, neighbourhood, name (description) of the
     * property and host name.
     */
    private void setHeader() {
        this.propertyType.setText(getListing().getRoom_type() + " in " + getListing().getNeighbourhood());
        this.nameAndHost.setText(getListing().getName() + " - " + getListing().getHost_name());
    }
    /**
     * Set the number of reviews of the property. Shows "review" instead of "reviews" if only 1.
     */
    private void setReviews() {
        int numberOfReviews = getListing().getNumberOfReviews();

        if (numberOfReviews==1) {
            this.reviews.setText(numberOfReviews + " review");
        } else {
            this.reviews.setText(numberOfReviews + " reviews");
        }
    }

    /**
     * Sets the availability of the property including an icon. Disables the date selector pane if the property is
     * unavailable
     */
    private void setAvailability() {
        int daysAvailable = getListing().getAvailability365();

        if(daysAvailable==0){
            availability.setText("Unavailable");
            availabilityText.setText("This property is unavailable.");

            File file = new File("src/sample/Images/NotAvailable.png");
            Image image = new Image(file.toURI().toString());
            availabilityIcon.setImage(image);

            dateSelectPane.setDisable(true);
        } else if (daysAvailable<30) {
            availability.setText("Rare find");
            availabilityText.setText("This property is usually booked.");

            File file = new File("src/sample/Images/RareFind.png");
            Image image = new Image(file.toURI().toString());
            availabilityIcon.setImage(image);
        } else {
            availability.setText("Available");
            availabilityText.setText("This property is available.");

            File file = new File("src/sample/Images/Available.png");
            Image image = new Image(file.toURI().toString());
            availabilityIcon.setImage(image);
        }
    }

    private void initializeFavourites()
    {
        boolean isFavourite = false;
        ArrayList<AirbnbListing> listOfFavourites = controllerComponents.getAccount().getListOfFavouriteProperties();
        for(AirbnbListing property : listOfFavourites){
            if(getListing() == property){
                property.setFavourite(true);
                isFavourite = true;
                break;
            }
        }
        saveBox.setSelected(isFavourite);
    }

    @FXML
    public void reserveProperty() throws IOException
    {
        if(controllerComponents.getAccount() == null){
            warningAlert("If you want to book this property, you must be signed in. If you don't have an account, create one", "Not signed in");
        } else {
            addToBookings();
        }
    }

    /**
     * @param event created when link to open in google maps is clicked
     * @throws URISyntaxException {@link URISyntaxException;} in some circumstance
     * @throws IOException {@link IOException} in some circumstance
     */
    @FXML
    public void viewMap(ActionEvent event) throws URISyntaxException, IOException {
        double latitude = getListing().getLatitude();
        double longitude = getListing().getLongitude();

        URI uri = new URI("https://www.google.com/maps/place/" + latitude + "," + longitude);
        java.awt.Desktop.getDesktop().browse(uri);
    }

    /**
     * Method to take a check-in day for an airbnb reservation. Error messages will appear if the date is invalid.
     * @param event created when date is input into the date picker "check in"
     */
    @FXML
    public void setInDate(ActionEvent event){
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

    /**
     * Method to take a check-out day for an airbnb reservation. Error messages will appear if the date is invalid.
     * @param event created when date is input into the date picker "check out"
     */
    @FXML
    public void setOutDate(ActionEvent event){
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
    public void saveFavourites() throws IOException
    {
        if(controllerComponents.getAccount() == null){
            warningAlert("If you want to save this property into your favourites, you must you must be signed in. If you don't have an account, create one", "Not signed in");
            saveBox.setSelected(false);
        } else {
            getListing().setFavourite(!getListing().isFavourite());
            if (getListing().isFavourite()) {
                addToFavourites();
            }
            else {
                removeFromFavourites();
            }
        }
    }

    private void addToFavourites() throws IOException {
        controllerComponents.getAccount().addToFavouriteProperties(getListing(), accountDetailsController);
        setFavouriteTextLabel("This property has been added to your favourites");
        saveBox.setSelected(true);
        accountDetailsController.loadFavourites();
    }

    private void removeFromFavourites() throws IOException {
        controllerComponents.getAccount().removeFromFavourites(getListing());
        setFavouriteTextLabel("This property has been removed from your favourites");
        saveBox.setSelected(false);
        accountDetailsController.loadFavourites();
    }

    /**
     * Method called when a correct set of dates has been chosen. Sets price of labels considering the
     */
    private void calculatePrice(){
        int numberOfNights = (int) DAYS.between(inDate, outDate);
        int minNumberOfNights = getListing().getMinimumNights();

        if(numberOfNights < minNumberOfNights){
            invalidOptions("Minimum number of nights for this property is " + minNumberOfNights + " you selected " + numberOfNights, "Insufficient nights");
        } else {
            int price = getListing().getPrice();
            int subTotalPrice = price*numberOfNights;
            int serviceFee = (int) Math.round(subTotalPrice*0.2);
            int totalPrice = subTotalPrice+serviceFee;

            priceAndNights.setText("£" + price + " x " + numberOfNights + " nights");
            subtotal.setText("£" + subTotalPrice);
            serviceFeeValue.setText("£" + serviceFee);
            totalPriceLabel.setText("£" + totalPrice);

            reserveButton.setDisable(false);
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

    
    public void reload()
    {
        if(controllerComponents.getAccount() == null){
            getListing().setFavourite(false);
            saveBox.setSelected(false);
        }
        else{
            initializeFavourites();
        }
    }

    /**
     * Sets the a label that indicates that the property has been saved to favourites.
     * @param text New text to be set for the favourite label
     */
    public void setFavouriteTextLabel(String text) {
        favouriteTextLabel.setText(text);
    }

    /**
     * This method creates and displays an alert of type WARNING
     * @param warning The warning to be displayed
     * @param warningTitle The title of the warning
     */
    private void warningAlert(String warning, String warningTitle)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(warningTitle);
        alert.setHeaderText(null);
        alert.setContentText(warning);
        alert.showAndWait();

    }


    private void addToBookings() throws IOException
    {
        FXMLLoader bookingLoader = new FXMLLoader(getClass().getResource("BookingPane.fxml"));
        bookingLoader.setController(accountDetailsController);
        BorderPane booking = bookingLoader.load();
        controllerComponents.getAccount().addToBookings(getListing(), booking, accountDetailsController);
        accountDetailsController.loadBookings();

        FXMLLoader bookingDetailsLoader = new FXMLLoader(getClass().getResource("BookingDetailsWindow.fxml"));
        Stage bookingDetailsStage = bookingDetailsLoader.load();
        BookingDetailsController bookingDetailsController = bookingDetailsLoader.getController();
        bookingDetailsController.initialize(getListing(), inDate, outDate);
        controllerComponents.getAccount().addToBookingDetailsMap(getListing(), bookingDetailsStage);

        reserveButton.setDisable(true);
    }
}