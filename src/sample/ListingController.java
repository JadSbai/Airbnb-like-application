package sample;

import java.io.IOException;

public abstract class ListingController extends Controller
{
    private AirbnbListing listing;

    public ListingController(AirbnbListing listing) {
        this.listing = listing;
    }

    protected AirbnbListing getListing() {
        return listing;
    }
}
