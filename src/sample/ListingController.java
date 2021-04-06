package sample;

import java.io.IOException;

public class ListingController extends Controller
{
    private AirbnbListing listing;

    public ListingController(Account account, AirbnbListing listing) {
        super(account);
        this.listing = listing;
    }

    protected AirbnbListing getListing() {
        return listing;
    }
}
