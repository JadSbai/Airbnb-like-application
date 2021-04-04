package sample;

public abstract class ListingController extends Controller {

    private AirbnbListing listing;

    public void initialize(AirbnbListing listing){
        this.listing = listing;
    }

    protected AirbnbListing getListing() {
        return listing;
    }
}
