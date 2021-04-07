package sample;

/**
 * Super class for ListingViewController and ListingPreviewController to get and set a common listing.
 * @author Jacqueline Ilie, Liam Clark Gutiérrez, Dexter Trower and Jad Sbaï
 * @version 07/04/2021
 */
public class ListingController
{
    /**
     * Listing common to the preview and the view
     */
    private AirbnbListing listing;

    /**
     * Sets the listing for this super class which will be previewed or viewed
     * @param listing listing
     */
    public ListingController(AirbnbListing listing) {
        this.listing = listing;
    }

    /**
     * Returns the listing held in this superclass for use in the preview or view
     * @return  listing
     */
    protected AirbnbListing getListing() {
        return listing;
    }
}