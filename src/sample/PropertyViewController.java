package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.File;

import static java.time.temporal.ChronoUnit.DAYS;

public class PropertyViewController {

    private AirbnbListing listing;

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


    private boolean isReserved;

    @FXML
    private Hyperlink locationLink;
    @FXML
    private Label favouriteTextLabel;


    private Account currentAccount;


    public void initialize(AirbnbListing listing, Account account){
        favouriteTextLabel.setText("");

        this.listing = listing;
        this.currentAccount = account;

        setHeader();
        setAvailability();
        setReviews();

        if(currentAccount == null){
            listing.setFavourite(false);
            setSaveBox(false);
        }
        else{
            initializeFavourites();
        }
        this.priceAndNights.setText("£" + listing.getPrice() + " / night");
    }

    @FXML
    public void reserveProperty(){
        isReserved = !isReserved;
    }

    /**
     * @param event created when link to open in google maps is clicked
     * @throws URISyntaxException {@link URISyntaxException;} in some circumstance
     * @throws IOException {@link IOException} in some circumstance
     */
    @FXML
    public void viewMap(ActionEvent event) throws URISyntaxException, IOException {
        double latitude = listing.getLatitude();
        double longitude = listing.getLongitude();

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
    public void saveFavourites()
    {
        if(currentAccount == null){
            setSaveBox(false);
            warningAlert("If you want to save this property into your favourites, you must first sign in to your account. If you don't have an account, create one", "Not signed in");
        }
        else {
            listing.setFavourite(!listing.isFavourite());

            if (listing.isFavourite()) {
                addToFavourites(listing);
            }
            else {
                removeFromFavourites(listing);
            }
        }
    }

    private void initializeFavourites()
    {
        boolean isFavourite = false;
        ArrayList<AirbnbListing> listOfFavourites = currentAccount.getListOfFavouriteProperties();
        for(AirbnbListing property : listOfFavourites){
            if(listing == property){
                property.setFavourite(true);
                isFavourite = true;
                break;
            }
        }
        setSaveBox(isFavourite);
    }


    private void setSaveBox(boolean isFavourite)
    {
        saveBox.setSelected(isFavourite);
    }

    private void addToFavourites(AirbnbListing listing)
    {
        currentAccount.addToListOfFavouriteProperties(listing);
        favouriteTextLabel.setText("This property has been added to your favourites");
        setSaveBox(true);
    }

    private void removeFromFavourites(AirbnbListing listing)
    {
        currentAccount.removeFromListOfFavouriteProperties(listing);
        favouriteTextLabel.setText("This property has been removed from your favourites");
        setSaveBox(false);
    }

    /**
     * Method called when a correct set of dates has been chosen. Sets price of labels considering the
     */
    private void calculatePrice(){
        int numberOfNights = (int) DAYS.between(inDate, outDate);
        int minNumberOfNights = listing.getMinimumNights();

        if(numberOfNights < minNumberOfNights){
            invalidOptions("Minimum number of nights for this property is " + minNumberOfNights + " you selected " + numberOfNights, "Insufficient nights");
        } else {
            int price = listing.getPrice();
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

    public CheckBox getSaveBox() {
        return saveBox;
    }

    /**
     * Set the text for the header of the listing's view. Shows room type, neighbourhood, name (description) of the
     * property and host name.
     */
    private void setHeader() {
        this.propertyType.setText(listing.getRoom_type() + " in " + listing.getNeighbourhood());
        this.nameAndHost.setText(listing.getName() + " - " + listing.getHost_name());
    }

    /**
     * Set the number of reviews of the property. Shows "review" instead of "reviews" if only 1.
     */
    private void setReviews() {
        int numberOfReviews = listing.getNumberOfReviews();

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
        int daysAvailable = listing.getAvailability365();

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

    public void reload(AirbnbListing listing, Account account)
    {
        currentAccount = account;
        if(currentAccount == null){
            listing.setFavourite(false);
            setSaveBox(false);
        }
        else{
            initializeFavourites();
        }
    }
}




