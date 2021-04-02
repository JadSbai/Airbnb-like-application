package sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javafx.scene.control.ListView;
import javafx.scene.image.Image;
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
    private ArrayList<AirbnbListing> listOfFavouriteProperties;

    private HashMap<AirbnbListing, Pane> listingToPaneMap;

    private ListView<Pane> listViewOfFavourites;

    /**
     * The account's profile picture
     */
    private Image profilePicture;


    /**
     * The constructor initializes all the account fields.
     * @param email the account's email
     * @param username the account's username
     * @param password the account's password
     */
    public Account(String username, String email, String password)
    {
        this.email = email;
        this.username = username;
        this.password = password;
        listOfFavouriteProperties = new ArrayList<>();
        listingToPaneMap = new HashMap<>();
        profilePicture = new Image("/sample/nopfp.png");
        listViewOfFavourites = new ListView<>();

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

    /**
     * This method returns the list of favourites properties associated to the account
     * @return the account's list of favourite properties
     */
    public ArrayList<AirbnbListing> getListOfFavouriteProperties()
    {
        return listOfFavouriteProperties;
    }

    /**
     * This method removes the specified property from the account's list of favourites
     * @param listing The property to be removed from the list
     */
    public void removeFromListOfFavouriteProperties(AirbnbListing listing)
    {
        // Use of an iterator object to avoid index errors in the list
        Iterator<AirbnbListing> iterator = listOfFavouriteProperties.iterator();
        AirbnbListing property = null;
        while (iterator.hasNext()) {
            property = iterator.next();
            if (property == listing) {
                iterator.remove();
                removeFromFavourites(listing);
                break;
            }
        }
    }


    /**
     * This method adds the specified property to the account's list of favourites
     * @param listing The property to be added
     */
    public void addToFavouriteProperties(AirbnbListing listing)
    {
        Pane propertyPreviewPane = listing.getPropertyPreviewPane();
        if(propertyPreviewPane == null){
            System.out.println("NULL");
        }
        listOfFavouriteProperties.add(listing);
        addToListViewOfFavourites(propertyPreviewPane);
    }
    /**
     * This method returns the account's current profile picture
     * @return The profile picture
     */
    public Image getProfilePicture()
    {
        return profilePicture;
    }

    /**
     * This method sets the account's profile picture to the one specified
     * @param pfp The new profile picture
     */
    public void setProfilePicture(Image pfp)
    {
        profilePicture = pfp;
    }

    private void addToListViewOfFavourites(Pane pane)
    {
        listViewOfFavourites.getItems().add(pane);
    }

    private void removeFromListViewOfFavourites(Pane pane)
    {
        listViewOfFavourites.getItems().remove(pane);
    }

    public void removeFromFavourites(AirbnbListing listing)
    {
        Pane propertyPreviewPane = listing.getPropertyPreviewPane();
        removeFromListOfFavouriteProperties(listing);
        removeFromListViewOfFavourites(propertyPreviewPane);
    }


    public ListView<Pane> getListViewOfFavourites() {
        return listViewOfFavourites;
    }
}

