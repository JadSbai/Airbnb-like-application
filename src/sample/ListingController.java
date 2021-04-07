package sample;

public class ListingController
{
    private AirbnbListing listing;

    public ListingController(AirbnbListing listing) {
        this.listing = listing;
    }

    protected AirbnbListing getListing() {
        return listing;
    }
}