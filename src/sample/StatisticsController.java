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

/**
 * This class is in charge of the statistics panel. It computes the statistics based on the price range
 * inserted in the 'Welcome panel' and it keeps track of the ones that are currently being shown,
 * so that they do not appear more than once.
 * @author Jacqueline Ilie, Liam Clark Gutiérrez, Dexter Trower and Jad Sbaï
 * @version 05/04/2021
 */

public class StatisticsController {

    private ControllerComponents controllerComponents;
    /**
     * The GUI elements
     */

    /**
     * Eight buttons: one back button and one forward button for each of the 4 displayed statistics
     */
    @FXML
    private Button backButtonStatistics1, forwardButtonStatistics1;
    @FXML
    private Button backButtonStatistics2, forwardButtonStatistics2;
    @FXML
    private Button backButtonStatistics3, forwardButtonStatistics3;
    @FXML
    private Button backButtonStatistics4, forwardButtonStatistics4;

    /**
     * Four labels representing each of the statistic currently being shown on the panel
     */
    @FXML
    public Label statistic1, statistic2, statistic3, statistic4;

    /**
     * A HashMap that contains all of the statistics and keeps track whether the statistics are being
     * currently shown on the panel or not: true if they are, false otherwise
     */
    private HashMap<String, Boolean> statistics;

    /**
     * An ArrayList that contains all of the properties extracted from the CSV file
     */
    private ArrayList<AirbnbListing> allProperties;

    /**
     * A HashMap that contains all of the neighbourhoods and maps them to their total price
     */
    private HashMap<String, Integer> boroughs;

    /**
     * A HashMap that maps all hosts to their total number of properties
     */
    private HashMap<String, Integer> hostsNumberOfProperties;

    /**
     * A HashMap that maps all hosts to their total price
     */
    private HashMap<String, Integer> hostsPrice;

    /**
     * An ArrayList of all of the properties within the current selected price range
     */
    private ArrayList<AirbnbListing> propertiesAtPriceRange;

    /**
     * The minimum and maximum price selected for the properties
     */
    private int minPrice, maxPrice;

    /**
     * A HashMap that maps each button to its corresponding statistic label
     */
    private HashMap<Button, Label> buttonToLabelMap;

    /**
     * The constructor creates the statistics HashMap, loads all of the properties, creates a new ArrayList for the
     * properties within the specified price range and a HashMap to map each button to its statistic label.
     */
    public StatisticsController(ControllerComponents controllerComponents) {
        this.controllerComponents = controllerComponents;
        statistics = new HashMap<>();
        allProperties = ControllerComponents.getDataLoader().getListings();
        propertiesAtPriceRange = new ArrayList<>();
        buttonToLabelMap = new HashMap<>();
    }
        
    /**
    * The minimum and maximum price for the properties to be selected are being set.
    * All of the statistics are being computed based on the price range and the  four initial statistics are being
    * assigned to the labels. This method is called each time the price range is changed.
    */
    public void setPriceRange(int minPrice, int maxPrice)
    {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        setPriceRangeProperties();
        setTextToInitialLabels();
        addToStatisticsArray();
    }

    /**
     * The initial statistics are being displayed and the statistics HashMap is being initialized,
     * based on the currently selected price range
     */
    @FXML
    public void initialize()
    {
        buttonToLabelMap.put(backButtonStatistics1, statistic1);
        buttonToLabelMap.put(forwardButtonStatistics1, statistic1);

        buttonToLabelMap.put(backButtonStatistics2, statistic2);
        buttonToLabelMap.put(forwardButtonStatistics2, statistic2);

        buttonToLabelMap.put(backButtonStatistics3, statistic3);
        buttonToLabelMap.put(forwardButtonStatistics3, statistic3);

        buttonToLabelMap.put(backButtonStatistics4, statistic4);
        buttonToLabelMap.put(forwardButtonStatistics4, statistic4);
    }

    /**
     * The list of properties within the specified price range is being computed. Each time the price range is changed,
     * the list of properties is cleared and computed again.
     */
    private void setPriceRangeProperties() {
        propertiesAtPriceRange.clear();
        for(AirbnbListing p: allProperties)
            if(p.getPrice() >= minPrice && p.getPrice() <= maxPrice)
                propertiesAtPriceRange.add(p);
    }

    /**
     * The initial four statistics are being computed and set to each label
     */
    public void setTextToInitialLabels(){
        statistic1.setText("Average number of reviews" + '\n' + '\n' + getAverageNumberOfReviews());
        statistic2.setText("Available properties" + '\n' + '\n' + getNumberOfAvailableProperties());
        statistic3.setText("Number of entire home/apartments" + '\n' + '\n' + getNumberOfEntireHomeOrApartments());
        statistic4.setText("Most expensive borough" + '\n' + '\n' + getMostExpensiveBorough());
    }

    /**
     * The other four statistics are being computed and all of the eight statistics are being put
     * in the statistics HashMap. Each of the statistic' HashMap value is set to true if the statistic
     * is currently held in one of the labels and being shown, false otherwise. Each time the price range is changed,
     * the statistics HashMap is cleared and computed again in order to show the corresponding results.
     */
    public void addToStatisticsArray(){
        statistics.clear();

        statistics.put(statistic1.getText(), true);
        statistics.put(statistic2.getText(), true);
        statistics.put(statistic3.getText(), true);
        statistics.put(statistic4.getText(), true);

        statistics.put("Host with most properties" + '\n' + '\n' + getHostWithMostProperties(), false);
        statistics.put("Cheapest host" + '\n' + '\n' + getCheapestHost(), false);
        statistics.put("Most reviewed property" + '\n' + '\n' + getMostReviewedProperty(), false);
        statistics.put("Average price per night" + '\n' + '\n' + getAveragePricePerNight(), false);
    }

    /**
     * The back and forward buttons of each statistic both have the same functionality. Each pair of back and forward 
     * buttons are mapped to their corresponding statistic label. The method iterates through the statistics HashMap
     * to look for an available (not currently being displayed on the panel) statistic to change its label to.
     */
    @FXML
    private void statisticsButtonPressed(ActionEvent event)
    {
        Button buttonPressed = (Button) event.getSource();
        Label statisticsLabel = buttonToLabelMap.get(buttonPressed);

        for (Map.Entry<String, Boolean> entry : statistics.entrySet()) {
             String statisticString = entry.getKey();
             Boolean statisticIsShown = entry.getValue();
             // checks if statistic1 is not currently being shown
             if ( !statisticIsShown ) {
                  // update statistic1 status to indicate that it is not being displayed anymore
                  statistics.put(statisticsLabel.getText(), false);
                  // change statistic1 text
                  statisticsLabel.setText(statisticString);
                  // update the new statsistic status to indicate that it is currently being displayed
                  statistics.put(statisticString, true);
                  break;
             }
        }
    }

    /**
     * This method computes and returns the average number of reviews for all of the properties
     * within the selected price range
     * @return The average number of reviews
     */
    private int getAverageNumberOfReviews() {
        int numberOfReviews = 0;
        int totalNumberOfReviews = 0;
        int numberOfProperties = 0;

        for(AirbnbListing p: propertiesAtPriceRange) {
            numberOfReviews = p.getNumberOfReviews();
            totalNumberOfReviews += numberOfReviews;
            numberOfProperties++;
        }

        int average = totalNumberOfReviews / numberOfProperties;

        return average;
    }

    /**
     * This method computes and returns the number of available properties within the selected price range
     * @return The average number of reviews
     */
    private int getNumberOfAvailableProperties() {
        int numberOfAvailableProperties = 0;

        for(AirbnbListing p: propertiesAtPriceRange) {
            if(p.getAvailability365() > 0)
                numberOfAvailableProperties ++;
        }

        return numberOfAvailableProperties;
    }

    /**
     * This method computes and returns the number of entire home and apartments (as opposed to private rooms)
     * within the selected price range
     * @return The number of entire home and apartments
     */
    private int getNumberOfEntireHomeOrApartments() {
        int numberOfEntireHomeOrApartments = 0;

        for(AirbnbListing p: propertiesAtPriceRange) {
            if(p.getRoom_type().equals("Entire home/apt"))
                numberOfEntireHomeOrApartments ++;
        }

        return numberOfEntireHomeOrApartments;
    }

    /**
     * This method returns the most expensive borough
     * @return the most expensive borough
     */
    private String getMostExpensiveBorough() {
        boroughs = new HashMap<>();
        setTotalPriceForEachBorough();
        String mostExpensiveBorough = "borough";
        int mostExpensiveBoroughPrice = 0;

        // iterates through the boroughs and compares the current biggest price to each borough price
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

    /**
     * This method computes the total price of each borough, based on the total price of all of the
     * properties from each borough, taking into account only the properties within the specified price range
     */
    private void setTotalPriceForEachBorough() {
        int totalBoroughPrice = 0;
        for(AirbnbListing p: propertiesAtPriceRange) {
            String neighbourhood = p.getNeighbourhood();
            // checks if this is the first time a neighbourhood appears in the iteration
            if( !boroughs.containsKey(neighbourhood) ) {
                // if it is (the key is not yet contained in the HashMap),
                // then the total price of that neighbourhood is set to the price of the current property being iterated
                totalBoroughPrice = p.getMinimumPrice();
                boroughs.put(neighbourhood, totalBoroughPrice);
            }
            else {
                // if it is not (the key is already contained in the HashMap),
                // then the price of the current property being iterated is added up to the total price of the
                // neighbourhood, which has previously been computed and mapped to that neighbourhood
                totalBoroughPrice = boroughs.get(neighbourhood) + p.getMinimumPrice();
                boroughs.put(neighbourhood, totalBoroughPrice);
            }
        }
    }

    /**
     * This method returns the host with most properties
     * @return the host id and name with most properties
     */
    private String getHostWithMostProperties() {
        hostsNumberOfProperties = new HashMap<>();
        setNumberOfPropertiesForEachHost();
        String hostWithMostProperties = "host";
        int mostNumberOfProperties = 0;

        // iterates through the hosts and compares the current biggest number of properties to each host's number of properties
        for(Map.Entry<String, Integer> entry : hostsNumberOfProperties.entrySet()) {
            String hostId = entry.getKey();
            Integer numberOfProperties = entry.getValue();
            if( numberOfProperties > mostNumberOfProperties ) {
                mostNumberOfProperties = numberOfProperties;
                hostWithMostProperties = hostId;
            }
        }

        return hostWithMostProperties;
    }

    /**
     * This method computes the number of properties for each host
     */
    private void setNumberOfPropertiesForEachHost() {
        int numberOfProperties = 0;
        for(AirbnbListing p: propertiesAtPriceRange) {
            String host = p.getHost_id() + " " + p.getHost_name();
            // first property found owned by this host (the key is not yet contained in the HashMap)
            if( !hostsNumberOfProperties.containsKey(host) ) {
                hostsNumberOfProperties.put(host, 1);
            }
            // the number of properties owned by this host is incremented
            else {
                numberOfProperties = hostsNumberOfProperties.get(host);
                numberOfProperties ++;
                hostsNumberOfProperties.put(host, numberOfProperties);
            }
        }
    }

    /**
     * This method returns the cheapest host, taking into account only the properties within the specified price range
     * @return the cheapest host's id and name
     */
    private String getCheapestHost() {
        hostsPrice = new HashMap<>();
        setTotalPriceForEachHost();
        String cheapestHost = "host";
        int cheapestHostTotalPrice = 9999999;

        // iterates through the hosts and compares the current cheapest property to each host's total price
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
    /**
     * This method computes the total price of all the properties owned by each host
     */
    private void setTotalPriceForEachHost() {
        int totalHostPrice = 0;
        for(AirbnbListing p: propertiesAtPriceRange) {
            String host = p.getHost_id() + " " + p.getHost_name();
            // first property found by this host (the key is not yet contained in the HashMap)
            if( !hostsPrice.containsKey(host) ) {
                totalHostPrice = p.getMinimumPrice();
                hostsPrice.put(host, totalHostPrice);
            }
            // the key is already contained in the HashMap
            // then the price of the current property being iterated is added up to the total price of the
            // host, which has previously been computed and mapped to that host
            else {
                totalHostPrice = hostsPrice.get(host) + p.getMinimumPrice();
                hostsPrice.put(host, totalHostPrice);
            }
        }
    }

    /**
     * This method returns the most reviewed property within the specified price range
     * @return The most reviewed property's name and number of reviews
     */
    private String getMostReviewedProperty() {
        String mostReviewedProperty = "";
        int maxNumberOfReviews = 0;

        // iterates through the properties and compares the current most reviewed property to each property's number of reviews
        for(AirbnbListing p: propertiesAtPriceRange) {
            if(maxNumberOfReviews <= p.getNumberOfReviews()) {
                maxNumberOfReviews = p.getNumberOfReviews();
                mostReviewedProperty = p.getName();
            }
        }
        return mostReviewedProperty + " (" + maxNumberOfReviews + " reviews)";
    }

    /**
     * This method computes and return the average price per night of all the properties within the specified
     * price range, based on the price per night alone, not taking into account the minimum number of nights for a stay
     * @return The average price per night
     */
    private int getAveragePricePerNight() {
        int totalPrice = 0;
        int numberOfProperties = 0;
        int averagePricePerNight;

        for(AirbnbListing p: propertiesAtPriceRange)
        {
            totalPrice = totalPrice + p.getPrice();
            numberOfProperties ++;
        }

        averagePricePerNight = totalPrice/numberOfProperties;
        return averagePricePerNight;
    }

}