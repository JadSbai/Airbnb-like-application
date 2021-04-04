package sample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;


/**
 * This class manipulates all the personal data related to an AirBnB account.
 *  * @author Jacqueline Ilie, Liam Clark Gutiérrez, Dexter Trower and Jad Sbaï
 *  * @version 29/03/2021
 */
public class Account {

    /**
     * The account's email
     */
    private final String email;

    /**
     * The account's username
     */
    private String username;

    /**
     * The account's password
     */
    private String password;

    /**
     * The account's list of favourite properties
     */

    private HashMap<AirbnbListing, Pane> favouritePropertyToPropertyPreviewPaneMap;

    private ArrayList<AirbnbListing> listOfFavouriteProperties;

    private ListView<Pane> listViewOfFavourites;

    private HashMap<AirbnbListing, BorderPane> propertyToBookingMap;

    private ListView<BorderPane> listViewOfBookings;

    private ArrayList<AirbnbListing> listOfBookings;

    private ArrayList<PropertyPreviewController> listOfPropertyPreviewControllers;

    private HashMap<Pane, PropertyPreviewController> paneToPropertyPreviewControllerMap;

    /**
     * The account's profile picture
     */
    private Image profilePicture;


    /**
     * The constructor initializes all the account fields.
     *
     * @param email    the account's email
     * @param username the account's username
     * @param password the account's password
     */
    public Account(String username, String email, String password) {
        this.email = email;
        this.username = username.trim();
        this.password = password;
        profilePicture = new Image("/sample/nopfp.png");
        listViewOfFavourites = new ListView<>();
        listOfBookings = new ArrayList<>();
        listViewOfBookings = new ListView<>();
        favouritePropertyToPropertyPreviewPaneMap = new HashMap<>();
        listOfFavouriteProperties = new ArrayList<>();
        propertyToBookingMap = new HashMap<>();
        listOfPropertyPreviewControllers = new ArrayList<>();
        paneToPropertyPreviewControllerMap = new HashMap<>();

    }

    /**
     * This method returns the email associated to the account
     *
     * @return the account's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method returns the email associated to the account
     *
     * @return the account's email
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method returns the email associated to the account
     *
     * @return the account's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method sets the username associated to the account
     *
     * @param username the desired username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * This method sets the password associated to the account
     *
     * @param password the desired password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * This method returns the list of favourites properties associated to the account
     *
     * @return the account's list of favourite properties
     */
    public ArrayList<AirbnbListing> getListOfFavouriteProperties() {
        return listOfFavouriteProperties;
    }

    /**
     * This method removes the specified property from the account's list of favourites
     *
     * @param listing The property to be removed from the list
     */
    public void removeFromListOfFavouriteProperties(AirbnbListing listing) {
        // Use of an iterator object to avoid index errors in the list
        Iterator<AirbnbListing> iterator = listOfFavouriteProperties.iterator();
        AirbnbListing property;
        while (iterator.hasNext()) {
            property = iterator.next();
            if (property == listing) {
                iterator.remove();
                break;
            }
        }
    }


    /**
     * This method adds the specified property to the account's list of favourites
     *
     * @param listing The property to be added
     */
    public void addToFavouriteProperties(AirbnbListing listing) throws IOException {
        FXMLLoader preview  = new FXMLLoader(getClass().getResource("AirbnbPreview.fxml"));
        Pane propertyPreviewPane = preview.load();
        PropertyPreviewController propertyPreviewController = preview.getController();
        propertyPreviewController.initialize(listing);
        listOfPropertyPreviewControllers.add(propertyPreviewController);
        paneToPropertyPreviewControllerMap.put(propertyPreviewPane, propertyPreviewController);
        favouritePropertyToPropertyPreviewPaneMap.put(listing, propertyPreviewPane);
        listOfFavouriteProperties.add(listing);
        addToListViewOfFavourites(propertyPreviewPane);
    }

    /**
     * This method returns the account's current profile picture
     *
     * @return The profile picture
     */
    public Image getProfilePicture() {
        return profilePicture;
    }

    /**
     * This method sets the account's profile picture to the one specified
     *
     * @param pfp The new profile picture
     */
    public void setProfilePicture(Image pfp) {
        profilePicture = pfp;
    }

    private void addToListViewOfFavourites(Pane pane) {
        listViewOfFavourites.getItems().add(pane);
    }

    private void removeFromListViewOfFavourites(Pane pane) {
        listViewOfFavourites.getItems().remove(pane);
    }

    public void removeFromFavourites(AirbnbListing listing)
    {
        Pane propertyPreviewPane = favouritePropertyToPropertyPreviewPaneMap.get(listing);
        removeFromListViewOfFavourites(propertyPreviewPane);
        favouritePropertyToPropertyPreviewPaneMap.remove(listing);
        removeFromListOfFavouriteProperties(listing);
        PropertyPreviewController propertyPreviewController = paneToPropertyPreviewControllerMap.get(propertyPreviewPane);
        removeFromListOfPropertyPreviewControllers(propertyPreviewController);
        paneToPropertyPreviewControllerMap.remove(propertyPreviewPane);

    }


    public ListView<Pane> getListViewOfFavourites() {
        return listViewOfFavourites;
    }

    public ListView<BorderPane> getListViewOfBookings() {
        return listViewOfBookings;
    }

    public void setListViewOfBookings(ListView<BorderPane> listViewOfBookings) {
        this.listViewOfBookings = listViewOfBookings;
    }

    public ArrayList<AirbnbListing> getListOfBookings() {
        return listOfBookings;
    }

    public void addToBookings(AirbnbListing listing, BorderPane booking) throws IOException
    {
        FXMLLoader preview  = new FXMLLoader(getClass().getResource("AirbnbPreview.fxml"));
        Pane propertyPreviewPane = preview.load();
        PropertyPreviewController propertyPreviewController = preview.getController();
        listOfPropertyPreviewControllers.add(propertyPreviewController);
        propertyPreviewController.initialize(listing);
        booking.setCenter(propertyPreviewPane);
        paneToPropertyPreviewControllerMap.put(booking,propertyPreviewController);
        propertyToBookingMap.put(listing, booking);
        listOfBookings.add(listing);
        addToListViewOfBookings(booking);
    }

    private void addToListViewOfBookings(BorderPane pane) {
        listViewOfBookings.getItems().add(pane);
    }

    public void removeFromBookings(AirbnbListing listing)
    {
        BorderPane booking = propertyToBookingMap.get(listing);
        PropertyPreviewController propertyPreviewController = paneToPropertyPreviewControllerMap.get(booking);
        removeFromListOfPropertyPreviewControllers(propertyPreviewController);
        paneToPropertyPreviewControllerMap.remove(booking);
        removeFromListViewOfBookings(booking);
        propertyToBookingMap.remove(listing);
        removeFromListOfBookings(listing);
    }

    private void removeFromListViewOfBookings(BorderPane booking)
    {
        listViewOfBookings.getItems().remove(booking);
    }

    /**
     * This method removes the specified property from the account's list of favourites
     *
     * @param listing The property to be removed from the list
     */
    public void removeFromListOfBookings(AirbnbListing listing) {
        // Use of an iterator object to avoid index errors in the list
        Iterator<AirbnbListing> iterator = listOfBookings.iterator();
        AirbnbListing property;
        while (iterator.hasNext()) {
            property = iterator.next();
            if (property == listing) {
                iterator.remove();
                break;
            }
        }
    }

    public void removeFromListOfPropertyPreviewControllers(PropertyPreviewController propertyPreviewController)
    {
        Iterator<PropertyPreviewController> iterator = listOfPropertyPreviewControllers.iterator();
        PropertyPreviewController controller;
        while (iterator.hasNext()) {
            controller = iterator.next();
            if (propertyPreviewController == controller) {
                iterator.remove();
                break;
            }
        }
    }

    public ArrayList<PropertyPreviewController> getListOfPropertyPreviewControllers() {
        return listOfPropertyPreviewControllers;
    }
}

