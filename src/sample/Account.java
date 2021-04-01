package sample;

import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.image.Image;




/**
 * This class manipulates all the personal data related to an AirBnB account.
 *  * @author Jacqueline Ilie, Liam Clark Gutiérrez, Dexter Trower and Jad Sbaï
 *  * @version 29/03/2021
 */
public class Account {

    // Account fields
    private final String email;
    private String username;
    private String password;
    private ArrayList<AirbnbListing> listOfFavouriteProperties;
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
        profilePicture = new Image("/sample/nopfp.png");

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

    public void removeFromListOfFavouriteProperties(AirbnbListing listing) {
        Iterator<AirbnbListing> iterator = listOfFavouriteProperties.iterator();
        AirbnbListing property = null;
        while (iterator.hasNext()) {
            property = iterator.next();
            if (property == listing) {
                iterator.remove();
                // Print "This property has been removed from your favourites"
                break;
            }
        }
    }

    public void addToListOfFavouriteProperties(AirbnbListing listing)
    {
        listOfFavouriteProperties.add(listing);
    }

    public Image getProfilePicture()
    {
        return profilePicture;
    }


    public void setProfilePicture(Image pfp)
    {
        profilePicture = pfp;
    }


}

