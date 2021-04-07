package sample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**
 * This class manipulates all the personal data related to an AirBnB account: Credentials, favourites and bookings.
 *  * @author Jacqueline Ilie, Liam Clark Gutiérrez, Dexter Trower and Jad Sbaï
 *  * @version 08/04/2021
 */
public class Account
{
    /**
     * Common instance of ControllerComponents
     */
    private ControllerComponents controllerComponents;

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
     * The account's profile picture
     */
    private Image profilePicture;

    /**
     * The account's list of favourite properties
     */
    private HashMap<AirbnbListing, Pane> favouritePropertyToPropertyPreviewPaneMap;

    /**
     * The account's list of favourite properties
     */
    private ArrayList<AirbnbListing> listOfFavouriteProperties;

    /**
     * The list of favourite properties' preview panes
     */
    private ArrayList<Pane> listOfFavouritePanes;

    /**
     * The list of bookings' BorderPanes
     */
    private ArrayList<BorderPane> listOfBookingPanes;

    /**
     * The list of property preview controllers of the account's preview panes
     */
    private ArrayList<ListingPreviewController> listOfListingPreviewControllers;

    /**
     * HashMap that maps a preview pane to its preview controller
     */
    private HashMap<Pane, ListingPreviewController> paneToPropertyPreviewControllerMap;

    /**
     * HashMap that maps a booking (BorderPane) to the associated property
     */
    private HashMap<BorderPane, AirbnbListing> bookingToListingMap;

    /**
     * HashMap that maps a property to its booking details
     */
    private HashMap<AirbnbListing, Stage> listingToBookingDetailsMap;

    /**
     * The account's list of booking details stages
     */
    private ArrayList<Stage> listOfBookingDetailsStages;


    /**
     * The constructor initializes all the account fields.
     *
     * @param email    the account's email
     * @param username the account's username
     * @param password the account's password
     */
    public Account(String username, String email, String password, ControllerComponents controllerComponents)
    {
        this.email = email;
        this.username = username.trim();
        this.password = password;
        profilePicture = new Image("/sample/Images/nopfp.png");
        listOfFavouritePanes = new ArrayList<>();
        listOfBookingPanes = new ArrayList<>();
        favouritePropertyToPropertyPreviewPaneMap = new HashMap<>();
        listOfFavouriteProperties = new ArrayList<>();
        listOfListingPreviewControllers = new ArrayList<>();
        paneToPropertyPreviewControllerMap = new HashMap<>();
        bookingToListingMap = new HashMap<>();
        listingToBookingDetailsMap = new HashMap<>();
        this.controllerComponents = controllerComponents;
        listOfBookingDetailsStages = new ArrayList<>();
    }

    /**
     * This method adds the specified property to the account's favourites (load and add)
     * @param listing The property to be added
     */
    public void addToFavouriteProperties(AirbnbListing listing, AccountDetailsController accountDetailsController) throws IOException
    {
        // Load and initialize the preview pane specific to the property
        FXMLLoader preview = new FXMLLoader(getClass().getResource("ListingPreview.fxml"));
        ListingPreviewController listingPreviewController = new ListingPreviewController(controllerComponents, listing, accountDetailsController);
        preview.setController(listingPreviewController);
        Pane propertyPreviewPane = preview.load();

        // Update the appropriate fields
        listOfFavouriteProperties.add(listing);
        listOfListingPreviewControllers.add(listingPreviewController);
        paneToPropertyPreviewControllerMap.put(propertyPreviewPane, listingPreviewController);
        favouritePropertyToPropertyPreviewPaneMap.put(listing, propertyPreviewPane);
        listOfFavouritePanes.add(propertyPreviewPane);
    }

    /**
     * This method removes the specified property from the account's favourites (load and add)
     * @param listing The property to be added
     */
    public void removeFromFavourites(AirbnbListing listing)
    {
        Pane propertyPreviewPane = favouritePropertyToPropertyPreviewPaneMap.get(listing);
        listOfFavouritePanes.remove(propertyPreviewPane);
        favouritePropertyToPropertyPreviewPaneMap.remove(listing);
        removeFromListOfFavouriteProperties(listing);
        ListingPreviewController listingPreviewController = paneToPropertyPreviewControllerMap.get(propertyPreviewPane);
        removeFromListOfPropertyPreviewControllers(listingPreviewController);
        paneToPropertyPreviewControllerMap.remove(propertyPreviewPane);

    }

    /**
     * This method removes the specified property from the account's list of favourites
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
     * This method adds the specified listing to the account's bookings.
     * @param listing The listing to be added to the bookings
     * @param booking The borderPane created based on the property and that will be added to the list of the bookings
     * @param accountDetailsController The instance of the AccountDetailsController
     * @throws IOException When the fxml file is not loaded successfully
     */
    public void addToBookings(AirbnbListing listing, BorderPane booking, AccountDetailsController accountDetailsController) throws IOException
    {
        try{
            // Load and initialize the preview pane specific to the property
            FXMLLoader preview = new FXMLLoader(getClass().getResource("ListingPreview.fxml"));
            ListingPreviewController listingPreviewController = new ListingPreviewController(controllerComponents, listing, accountDetailsController);
            preview.setController(listingPreviewController);
            Pane propertyPreviewPane = preview.load();

            // Update appropriate fields
            bookingToListingMap.put(booking, listing);
            listOfListingPreviewControllers.add(listingPreviewController);
            booking.setCenter(propertyPreviewPane);
            paneToPropertyPreviewControllerMap.put(booking, listingPreviewController);
            listOfBookingPanes.add(booking);
        }
        catch (IOException preview) {
            // smthg stackTrace...
        }

    }

    /**
     * This method adds the specified (listing, bookingDetailsStage) entry into the listingToBookingDetailsMap.
     * @param listing The property
     * @param bookingDetailsStage The associated booking details stage (window)
     */
    public void addToBookingDetailsMap(AirbnbListing listing, Stage bookingDetailsStage){
        listingToBookingDetailsMap.put(listing, bookingDetailsStage);
    }
    
    /**
     * This method removes the specified booking from the account's bookings
     * @param booking The booking to be removed
     */
    public void removeFromBookings(BorderPane booking)
    {
        // Update all appropriate fields
        ListingPreviewController listingPreviewController = paneToPropertyPreviewControllerMap.get(booking);
        removeFromListOfPropertyPreviewControllers(listingPreviewController);
        paneToPropertyPreviewControllerMap.remove(booking);
        listOfBookingPanes.remove(booking);
        bookingToListingMap.remove(booking);
    }

    /**
     * This method removes the specified listingPreviewController from the listOfListingPreviewControllers
     * @param listingPreviewController the specified controller
     */
    public void removeFromListOfPropertyPreviewControllers(ListingPreviewController listingPreviewController)
    {
        Iterator<ListingPreviewController> iterator = listOfListingPreviewControllers.iterator();
        ListingPreviewController controller;
        while (iterator.hasNext()) {
            controller = iterator.next();
            if (listingPreviewController == controller) {
                iterator.remove();
                break;
            }
        }
    }
    

    /**
     * This method returns the account's current profile picture
     * @return The profile picture
     */
    public Image getProfilePicture() {
        return profilePicture;
    }
    
    /**
     * This method returns the account's listOfListingPreviewControllers
     * @return The list of preview controllers
     */
    public ArrayList<ListingPreviewController> getListOfPropertyPreviewControllers() {
        return listOfListingPreviewControllers;
    }

    /**
     * This method returns the email associated to the account
     * @return the account's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method returns the email associated to the account
     * @return the account's email
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method returns the email associated to the account
     * @return the account's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method returns the list of favourites properties associated to the account
     * @return the account's list of favourite properties
     */
    public ArrayList<AirbnbListing> getListOfFavouriteProperties() {
        return listOfFavouriteProperties;
    }

    /**
     * This method returns the list of preview panes associated to the favourite properties
     * @return the account's list of favourite panes
     */
    public ArrayList<Pane> getListOfFavourites() {
        return listOfFavouritePanes;
    }

    /**
     * This method returns the list of bookings (BorderPanes)
     * @return The list of booking panes
     */
    public ArrayList<BorderPane> getListOfBookingPanes() {
        return listOfBookingPanes;
    }

    /**
     * This method returns the HashMap mapping the bookings to their property
     * @return The BookingToListingMap
     */
    public HashMap<BorderPane, AirbnbListing> getBookingToListingMap() {
        return bookingToListingMap;
    }

    /**
     * This method returns the map mapping the properties to their booking details stage
     * @return The ListingToBookingDetailsMap
     */
    public HashMap<AirbnbListing, Stage> getListingToBookingDetailsMap() {
        return listingToBookingDetailsMap;
    }

    /**
     * This method returns the list of booking details windows
     * @return The ListOfBookingDetailsStages
     */
    public ArrayList<Stage> getListOfBookingDetailsStages() {
        return listOfBookingDetailsStages;
    }

    /**
     * This method returns whether the property specified is booked or not
     * @return True if the property is already booked, false otherwise
     */
    public boolean isAlreadyBooked(AirbnbListing listing)
    {
        return listingToBookingDetailsMap.get(listing) != null;
    }

    /**
     * This method sets the account's profile picture to the one specified
     *
     * @param pfp The new profile picture
     */
    public void setProfilePicture(Image pfp) {
        profilePicture = pfp;
    }

    /**
     * This method sets the username associated to the account
     * @param username the desired username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * This method sets the password associated to the account
     * @param password the desired password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}

