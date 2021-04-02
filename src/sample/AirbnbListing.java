package sample;


import javafx.scene.layout.Pane;

import java.util.HashMap;

/**
 * Represents one listing of a property for rental on Airbnb.
 * This is essentially one row in the data table. Each column
 * has a corresponding field.
 */

public class AirbnbListing {
    /**
     * The id and name of the individual property
     */
    private final String id;
    private final String name;
    /**
     * The id and name of the host for this listing.
     * Each listing has only one host, but one host may
     * list many properties.
     */
    private final String host_id;
    private final String host_name;

    /**
     * The grouped location to where the listed property is situated.
     * For this data set, it is a london borough.
     */
    private final String neighbourhood;
    private final String abbreviatedNeighbourhood;

    /**
     * The location on a map where the property is situated.
     */
    private final double latitude;
    private final double longitude;

    /**
     * The type of property, either "Private room" or "Entire Home/apt".
     */
    private final String room_type;

    /**
     * The price per night's stay
     */
    private final int price;

    /**
     * The minimum number of nights the listed property must be booked for.
     */
    private final int minimumNights;
    private final int numberOfReviews;

    /**
     * The date of the last review, but as a String
     */
    private final String lastReview;
    private final double reviewsPerMonth;

    /**
     * The total number of listings the host holds across AirBnB
     */
    private final int calculatedHostListingsCount;
    /**
     * The total number of days in the year that the property is available for
     */
    private final int availability365;

    private boolean isFavourite;

    private Pane propertyPreviewPane;

    /**
     * Static HashMap used to establish the abbreviation of the borough used for the map.
     */
    private static final HashMap<String, String> boroughAbbreviation;
    static {
        boroughAbbreviation = new HashMap<>();
        boroughAbbreviation.put("Enfield", "ENFI");
        boroughAbbreviation.put("Barnet", "BARN");
        boroughAbbreviation.put("Haringey", "HRGY");
        boroughAbbreviation.put("Waltham Forest", "WALT");
        boroughAbbreviation.put("Harrow", "HRRW");
        boroughAbbreviation.put("Brent", "BREN");
        boroughAbbreviation.put("Camden", "CAMD");
        boroughAbbreviation.put("Islington", "ISLI");
        boroughAbbreviation.put("Hackney", "HACK");
        boroughAbbreviation.put("Redbridge", "REDB");
        boroughAbbreviation.put("Havering", "HAVE");
        boroughAbbreviation.put("Hillingdon", "HILL");
        boroughAbbreviation.put("Ealing", "EALI");
        boroughAbbreviation.put("Kensington and Chelsea", "KENS");
        boroughAbbreviation.put("Westminster", "WSTM");
        boroughAbbreviation.put("Tower Hamlets", "TOWH");
        boroughAbbreviation.put("Newham", "NEWH");
        boroughAbbreviation.put("Barking and Dagenham", "BARK");
        boroughAbbreviation.put("Hounslow", "HOUN");
        boroughAbbreviation.put("Hammersmith and Fulham", "HAMM");
        boroughAbbreviation.put("Wandsworth", "WAND");
        boroughAbbreviation.put("City of London", "CITY");
        boroughAbbreviation.put("Greenwich", "GWCH");
        boroughAbbreviation.put("Bexley", "BEXL");
        boroughAbbreviation.put("Richmond upon Thames", "RICH");
        boroughAbbreviation.put("Merton", "MERT");
        boroughAbbreviation.put("Lambeth", "LAMB");
        boroughAbbreviation.put("Southwark", "STHW");
        boroughAbbreviation.put("Lewisham", "LEWS");
        boroughAbbreviation.put("Kingston upon Thames", "KING");
        boroughAbbreviation.put("Sutton", "SUTT");
        boroughAbbreviation.put("Croydon", "CROY");
        boroughAbbreviation.put("Bromley", "BROM");
    }

    public AirbnbListing(String id, String name, String host_id,
                         String host_name, String neighbourhood, double latitude,
                         double longitude, String room_type, int price,
                         int minimumNights, int numberOfReviews, String lastReview,
                         double reviewsPerMonth, int calculatedHostListingsCount, int availability365) {
        this.id = id;
        this.name = name;
        this.host_id = host_id;
        this.host_name = host_name;
        this.neighbourhood = neighbourhood;
        this.abbreviatedNeighbourhood = boroughAbbreviation.get(neighbourhood);
        this.latitude = latitude;
        this.longitude = longitude;
        this.room_type = room_type;
        this.price = price;
        this.minimumNights = minimumNights;
        this.numberOfReviews = numberOfReviews;
        this.lastReview = lastReview;
        this.reviewsPerMonth = reviewsPerMonth;
        this.calculatedHostListingsCount = calculatedHostListingsCount;
        this.availability365 = availability365;
        this.isFavourite = false;
        this.propertyPreviewPane = null;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHost_id() {
        return host_id;
    }

    public String getHost_name() {
        return host_name;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public String getAbbreviatedNeighbourhood() {
        return abbreviatedNeighbourhood;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getRoom_type() {
        return room_type;
    }

    public int getPrice() {
        return price;
    }

    public int getMinimumNights() {
        return minimumNights;
    }

    public int getNumberOfReviews() {
        return numberOfReviews;
    }

    public String getLastReview() {
        return lastReview;
    }

    public double getReviewsPerMonth() {
        return reviewsPerMonth;
    }

    public int getCalculatedHostListingsCount() {
        return calculatedHostListingsCount;
    }

    public int getAvailability365() {
        return availability365;
    }

    @Override
    public String toString() {
        return "AirbnbListing{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", host_id='" + host_id + '\'' +
                ", host_name='" + host_name + '\'' +
                ", neighbourhood='" + neighbourhood + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", room_type='" + room_type + '\'' +
                ", price=" + price +
                ", minimumNights=" + minimumNights +
                ", numberOfReviews=" + numberOfReviews +
                ", lastReview='" + lastReview + '\'' +
                ", reviewsPerMonth=" + reviewsPerMonth +
                ", calculatedHostListingsCount=" + calculatedHostListingsCount +
                ", availability365=" + availability365 +
                '}';
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public Pane getPropertyPreviewPane() {
        return propertyPreviewPane;
    }

    public void setPropertyPreviewPane(Pane propertyPreviewPane) {
        this.propertyPreviewPane = propertyPreviewPane;
    }
}


