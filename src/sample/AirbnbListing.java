package sample;


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
    private final String hostNameWithApostrophe;

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
     * The minimum price for the booking, calculated based on the minimum number of nights and the price per night's stay
     */
    private int minimumPrice;

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

    /**
     * Static HashMap used to establish the abbreviation of the borough used for the map.
     */
    private static final HashMap<String, String> boroughAbbreviations;
    static {
        boroughAbbreviations = new HashMap<>();
        setBoroughAbbreviationNames();
    }

    private static final HashMap<String, String> boroughNames;
    static {
        boroughNames = new HashMap<>();
        setBoroughNames();
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
        this.hostNameWithApostrophe = setHostNameWithApostrophe();
        this.neighbourhood = neighbourhood;
        this.abbreviatedNeighbourhood = boroughAbbreviations.get(neighbourhood);
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
    }

    private String setHostNameWithApostrophe() {
        if(host_name.endsWith("s")) {
            return host_name + "'";
        } else {
            return host_name + "'s";
        }
    }

    public static String getFullBoroughName(String boroughAbbreviation){
        return boroughNames.get(boroughAbbreviation);
    }

    private static void setBoroughAbbreviationNames() {
        boroughAbbreviations.put("Enfield", "ENFI");
        boroughAbbreviations.put("Barnet", "BARN");
        boroughAbbreviations.put("Haringey", "HRGY");
        boroughAbbreviations.put("Waltham Forest", "WALT");
        boroughAbbreviations.put("Harrow", "HRRW");
        boroughAbbreviations.put("Brent", "BREN");
        boroughAbbreviations.put("Camden", "CAMD");
        boroughAbbreviations.put("Islington", "ISLI");
        boroughAbbreviations.put("Hackney", "HACK");
        boroughAbbreviations.put("Redbridge", "REDB");
        boroughAbbreviations.put("Havering", "HAVE");
        boroughAbbreviations.put("Hillingdon", "HILL");
        boroughAbbreviations.put("Ealing", "EALI");
        boroughAbbreviations.put("Kensington and Chelsea", "KENS");
        boroughAbbreviations.put("Westminster", "WSTM");
        boroughAbbreviations.put("Tower Hamlets", "TOWH");
        boroughAbbreviations.put("Newham", "NEWH");
        boroughAbbreviations.put("Barking and Dagenham", "BARK");
        boroughAbbreviations.put("Hounslow", "HOUN");
        boroughAbbreviations.put("Hammersmith and Fulham", "HAMM");
        boroughAbbreviations.put("Wandsworth", "WAND");
        boroughAbbreviations.put("City of London", "CITY");
        boroughAbbreviations.put("Greenwich", "GWCH");
        boroughAbbreviations.put("Bexley", "BEXL");
        boroughAbbreviations.put("Richmond upon Thames", "RICH");
        boroughAbbreviations.put("Merton", "MERT");
        boroughAbbreviations.put("Lambeth", "LAMB");
        boroughAbbreviations.put("Southwark", "STHW");
        boroughAbbreviations.put("Lewisham", "LEWS");
        boroughAbbreviations.put("Kingston upon Thames", "KING");
        boroughAbbreviations.put("Sutton", "SUTT");
        boroughAbbreviations.put("Croydon", "CROY");
        boroughAbbreviations.put("Bromley", "BROM");
    }

    private static void setBoroughNames() {
        boroughNames.put("ENFI", "Enfield");
        boroughNames.put("BARN", "Barnet");
        boroughNames.put("HRGY", "Haringey");
        boroughNames.put("WALT", "Waltham Forest");
        boroughNames.put("HRRW", "Harrow");
        boroughNames.put("BREN", "Brent");
        boroughNames.put("CAMD", "Camden");
        boroughNames.put("ISLI", "Islington");
        boroughNames.put("HACK", "Hackney");
        boroughNames.put("REDB", "Redbridge");
        boroughNames.put("HAVE", "Havering");
        boroughNames.put("HILL", "Hillingdon");
        boroughNames.put("EALI", "Ealing");
        boroughNames.put("KENS", "Kensington and Chelsea");
        boroughNames.put("WSTM", "Westminster");
        boroughNames.put("TOWH", "Tower Hamlets");
        boroughNames.put("NEWH", "Newham");
        boroughNames.put("BARK", "Barking and Dagenham");
        boroughNames.put("HOUN", "Hounslow");
        boroughNames.put("HAMM", "Hammersmith and Fulham");
        boroughNames.put("WAND", "Wandsworth");
        boroughNames.put("CITY", "City of London");
        boroughNames.put("GWCH", "Greenwich");
        boroughNames.put("BEXL", "Bexley");
        boroughNames.put("RICH", "Richmond upon Thames");
        boroughNames.put("MERT", "Merton");
        boroughNames.put("LAMB", "Lambeth");
        boroughNames.put("STHW", "Southwark");
        boroughNames.put("LEWS", "Lewisham");
        boroughNames.put("KING", "Kingston upon Thames");
        boroughNames.put("SUTT", "Sutton");
        boroughNames.put("CROY", "Croydon");
        boroughNames.put("BROM", "Bromley");
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHostNameWithApostrophe() {
        return hostNameWithApostrophe;
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

    public int getMinimumPrice() {
        minimumPrice = price * minimumNights;
        return minimumPrice;
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

}


