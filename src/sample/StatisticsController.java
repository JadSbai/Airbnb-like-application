//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package sample;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.util.*;



public class StatisticsController {


    // Declare the elements of the GUI

    @FXML
    private Button backButtonStatistics1;
    @FXML
    private Button forwardButtonStatistics1;
    @FXML
    private Button backButtonStatistics2;
    @FXML
    private Button forwardButtonStatistics2;
    @FXML
    private Button backButtonStatistics3;
    @FXML
    private Button forwardButtonStatistics3;
    @FXML
    private Button backButtonStatistics4;
    @FXML
    private Button forwardButtonStatistics4;

    @FXML
    public Label statistic1;
    @FXML
    private Label statistic2;
    @FXML
    private Label statistic3;
    @FXML
    private Label statistic4;

    private HashMap<String, Boolean> statistics;

    // Array extracted from the CSV with all of the properties
    private AirbnbDataLoader loader;
    private ArrayList<AirbnbListing> allProperties;

    // map all the neighbourhoods to their total price
    private HashMap<String, Integer> boroughs;

    // map all hosts to their number of properties
    private HashMap<String, Integer> hosts;

    // map all hosts to their price (minimumNights*Price)
    private HashMap<String, Integer> hostsPrice;

    // keeps track of the statistics list
    private int index;

    // list of properties within the current price range
    private ArrayList<AirbnbListing> properties;

    //
    private WelcomeController priceRange;

    public StatisticsController() {
        statistics = new HashMap<>();
        loader = new AirbnbDataLoader();
        properties = loader.load();
        //allProperties = loader.load();
        //priceRange = ;
    }

    public void initialize(){
        //if(priceRange.getMinPrice() != null && priceRange.getMaxPrice() != null)
            //setPriceRangeProperties();
        setTextToInitialLabels();
        addToStatisticsArray();
    }


    // !!! resize + price range
    // each time the price range is changed, setTextToInitialLabels and addToStatisticsArray are called; currently they
    // are called in the constructor
    // ==> everytime the price range is changed, the string is reinitialized and computated again


    // create list of properties within the price range
    /*private void setPriceRangeProperties() {
        for(AirbnbListing p: allProperties)
            if(p.getPrice() >= priceRange.getMinPrice() && p.getPrice() <= priceRange.getMaxPrice())
                properties.add(p);
    }*/


//set text to initial labels

    public void setTextToInitialLabels(){
        statistic1.setText("Average number of reviews" + '\n' + '\n' + getAverageNumberOfReviews());
        statistic2.setText("Available properties" + '\n' + '\n' + getNumberOfAvailableProperties());
        statistic3.setText("Number of entire home/apartments" + '\n' + '\n' + getNumberOfEntireHomeOrApartments());
        statistic4.setText("Most expensive borough" + '\n' + '\n' + getMostExpensiveBorough());
    }

    // the hashmap value is true if the label is being shown on the panel, false otherwise

    public void addToStatisticsArray(){
        statistics.put(statistic1.getText(), true);
        statistics.put(statistic2.getText(), true);
        statistics.put(statistic3.getText(), true);
        statistics.put(statistic4.getText(), true);

        statistics.put("Host with most properties" + '\n' + '\n' + getHostWithMostProperties(), false);
        statistics.put("Cheapest host" + '\n' + '\n' + getCheapestHost(), false);
        statistics.put("Most reviewed property" + '\n' + '\n' + getMostReviewedProperty(), false);
        statistics.put("Average price per night" + '\n' + '\n' + getAveragePricePerNight(), false);
    }


    @FXML
    private void backButtonStatistics1Pressed(ActionEvent e) {
        for (Map.Entry<String, Boolean> entry : statistics.entrySet()) {
            String statisticString = entry.getKey();
            Boolean statisticIsShown = entry.getValue();

            if (!statisticIsShown) { // checks if the statistic is not currently being shown
                // change statistic1 status => not shown anymore
                statistics.put(statistic1.getText(), false);
                // change statistic1 text to statisticString from statistic
                statistic1.setText(statisticString);
                // change statisticString status => changes to shown
                statistics.put(statisticString, true);
                break;
            }
        }
    }

    @FXML
    private void backButtonStatistics2Pressed(ActionEvent e) {
        for (Map.Entry<String, Boolean> entry : statistics.entrySet()) {
            String statisticString = entry.getKey();
            Boolean statisticIsShown = entry.getValue();

            if (!statisticIsShown) { // checks if the statistic is not currently being shown
                // change statistic1 status => not shown anymore
                statistics.put(statistic2.getText(), false);
                // change statistic1 text to statisticString from statistic
                statistic2.setText(statisticString);
                // change statisticString status => changes to shown
                statistics.put(statisticString, true);
                break;
            }
        }
    }

    @FXML
    private void backButtonStatistics3Pressed(ActionEvent e) {
        for (Map.Entry<String, Boolean> entry : statistics.entrySet()) {
            String statisticString = entry.getKey();
            Boolean statisticIsShown = entry.getValue();

            if (!statisticIsShown) { // checks if the statistic is not currently being shown
                // change statistic1 status => not shown anymore
                statistics.put(statistic3.getText(), false);
                // change statistic1 text to statisticString from statistic
                statistic3.setText(statisticString);
                // change statisticString status => changes to shown
                statistics.put(statisticString, true);

                break;
            }
        }
    }

    @FXML
    private void backButtonStatistics4Pressed(ActionEvent e) {
        for (Map.Entry<String, Boolean> entry : statistics.entrySet()) {
            String statisticString = entry.getKey();
            Boolean statisticIsShown = entry.getValue();

            if (!statisticIsShown) { // checks if the statistic is not currently being shown
                // change statistic1 status => not shown anymore
                statistics.put(statistic4.getText(), false);
                // change statistic1 text to statisticString from statistic
                statistic4.setText(statisticString);
                // change statisticString status => changes to shown
                statistics.put(statisticString, true);
                break;
            }
        }
    }

    // returns the average number of reviews
    private int getAverageNumberOfReviews() {
        int numberOfReviews = 0;
        int totalNumberOfReviews = 0;
        int numberOfProperties = 0;

        for(AirbnbListing p: properties) {
            numberOfReviews = p.getNumberOfReviews();
            totalNumberOfReviews += numberOfReviews;
            numberOfProperties++;
        }

        int average = totalNumberOfReviews / numberOfProperties;

        return average;
    }

    private int getNumberOfAvailableProperties() {
        int numberOfAvailableProperties = 0;
        for(AirbnbListing p: properties) {
            if(p.getAvailability365() > 0)
                numberOfAvailableProperties ++;
        }

        return numberOfAvailableProperties;
    }

    // The number of entire home and apartments (as opposed to private rooms).

    private int getNumberOfEntireHomeOrApartments() {
        int numberOfEntireHomeOrApartments = 0;
        for(AirbnbListing p: properties) {
            if(p.getRoom_type().equals("Entire home/apt"))
                numberOfEntireHomeOrApartments ++;
        }
        return numberOfEntireHomeOrApartments;
    }

    // The most expensive borough. // medie cu nr de propr/borough

    // most expensive based on the total price of the properties from the borough

    private String getMostExpensiveBorough() {
        boroughs = new HashMap<>();
        setTotalPriceForEachBorough();
        String mostExpensiveBorough = "borough";
        int mostExpensiveBoroughPrice = 0;
        for(Map.Entry<String, Integer> entry : boroughs.entrySet()) {
            String neighbourhood = entry.getKey();
            Integer boroughPrice = entry.getValue();
            if( boroughPrice > mostExpensiveBoroughPrice ) {
                mostExpensiveBoroughPrice = boroughPrice;
                mostExpensiveBorough = neighbourhood;
            }
        }
        return mostExpensiveBorough;
    }

    private void setTotalPriceForEachBorough() {
        int totalBoroughPrice = 0;
        for(AirbnbListing p: properties) {
            String neighbourhood = p.getNeighbourhood();
            if( !boroughs.containsKey(neighbourhood) ) {
                totalBoroughPrice = p.getMinimumPrice();
                boroughs.put(neighbourhood, totalBoroughPrice);
            }
            else {
                totalBoroughPrice = boroughs.get(neighbourhood) + p.getMinimumPrice();
                boroughs.put(neighbourhood, totalBoroughPrice);
            }
        }
    }


    // host with most properties

    private String getHostWithMostProperties() {
        hosts = new HashMap<>();
        setNumberOfPropertiesForEachHost();
        String hostWithMostProperties = "host";
        int mostNumberOfProperties = 0;
        for(Map.Entry<String, Integer> entry : hosts.entrySet()) {
            String hostId = entry.getKey();
            Integer numberOfProperties = entry.getValue();
            if( numberOfProperties > mostNumberOfProperties ) {
                mostNumberOfProperties = numberOfProperties;
                hostWithMostProperties = hostId;
            }
        }
        return hostWithMostProperties;
    }

    private void setNumberOfPropertiesForEachHost() {
        int numberOfProperties = 0;
        for(AirbnbListing p: properties) {
            String hostId = p.getHost_id() + " " + p.getHost_name();
            // first property found owned by this host
            if( !hosts.containsKey(hostId) ) {
                hosts.put(hostId, 1);
            }
            else {
                numberOfProperties = hosts.get(hostId);
                numberOfProperties ++;
                hosts.put(hostId, numberOfProperties);
            }
        }
    }


    //cheapest host

    private String getCheapestHost() {
        hostsPrice = new HashMap<>();
        setTotalPriceForEachHost();
        String cheapestHost = "host";
        int cheapestHostTotalPrice = 9999999;
        for(Map.Entry<String, Integer> entry : hostsPrice.entrySet()) {
            String hostId = entry.getKey();
            Integer hostTotalPrice = entry.getValue();
            if( hostTotalPrice < cheapestHostTotalPrice ) {
                cheapestHostTotalPrice = hostTotalPrice;
                cheapestHost = hostId;
            }
        }
        return cheapestHost;
    }

    private void setTotalPriceForEachHost() {
        int totalHostPrice = 0;
        for(AirbnbListing p: properties) {
            String hostId = p.getHost_id() + " " + p.getHost_name();
            if( !hostsPrice.containsKey(hostId) ) {
                totalHostPrice = p.getMinimumPrice();
                hostsPrice.put(hostId, totalHostPrice);
            }
            else {
                totalHostPrice = hostsPrice.get(hostId) + p.getMinimumPrice();
                hostsPrice.put(hostId, totalHostPrice);
            }
        }
    }


    // most number of reviews based on 10 cheapest hosts??

    // average price per night based on most 10 reviewed properties??

    private String getMostReviewedProperty() {
        String mostReviewedProperty = "";
        int maxNumberOfReviews = 0;
        for(AirbnbListing p: properties) {
            if(maxNumberOfReviews <= p.getNumberOfReviews()) {
                maxNumberOfReviews = p.getNumberOfReviews();
                mostReviewedProperty = p.getName();
            }
        }
        return mostReviewedProperty + " (" + maxNumberOfReviews + " reviews)";
    }

    // based on the price per night alone, not taking into account the minimum stay

    private int getAveragePricePerNight() {
        int totalPrice = 0;
        int numberOfProperties = 0;
        for(AirbnbListing p: properties)
        {
            totalPrice = totalPrice + p.getPrice();
            numberOfProperties ++;
        }
        return totalPrice/numberOfProperties;
    }

}