//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package sample;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.*;
//import java.util.Map;
//import java.util.HashMap;


public class StatisticsController {


    // Declare all of the elements of the GUI

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

    // Array extracted from the CSV with all of the statistics required

    private AirbnbDataLoader loader;
    private ArrayList<AirbnbListing> properties;

    // map all the neighbourhoods to their total price
    private HashMap<String, Integer> boroughs;

    // map all hosts to their price (minimumNights*Price)
    private HashMap<String, Integer> hosts;

    // keeps track of the statistics list
    private int index;

    // !!List of properties within the price range -> create list and then iterate it in all of the methods instead
    // of the list of all properties
    //Controller priceRange;
    // when the price range changes ->> should make sure the statistics labels update to display the correct computtions

    public void initialize(AirbnbDataLoader dataLoader){ //inainte de constructor
        statistics = new HashMap<>();
        loader = dataLoader;
        properties = dataLoader.getListings();
        index = 0;
        setTextToInitialLabels();
        addToStatisticsArray();
    }

//set text to initial labels

    public void setTextToInitialLabels(){
        statistic1.setText("Average number of reviews" + '\n' + getAverageNumberOfReviews());
        statistic2.setText("Available properties" + '\n' + getNumberOfAvailableProperties());
        statistic3.setText("Number of entire home/apartments" + '\n' + getNumberOfEntireHomeOrApartments());
        statistic4.setText("Most expensive borough" + '\n' + getMostExpensiveBorough());
    }

    // the hashmap value is true if the label is being shown on the panel, false otherwise

    public void addToStatisticsArray(){
        statistics.put(statistic1.getText(), true);
        statistics.put(statistic2.getText(), true);
        statistics.put(statistic3.getText(), true);
        statistics.put(statistic4.getText(), true);

        statistics.put("Host with most properties" + '\n' + getHostWithMostProperties(), false);
        statistics.put("stat6", false);
        statistics.put("stat7", false);
        statistics.put("stat8", false);
    }

    // if + return for each corresponding button/statistic
//!!!!!!!!!
    // link each statistic to its corresponding method and concatenate texts!!

    // iterate where i left off in the statistics array?? displays only 2 stats on each statistic if others not changed
    //less code duplication -> all buttons share the same functionality

    @FXML
    private void backButtonStatistics1Pressed(ActionEvent e) {

        //testing
        for (Map.Entry<String, Boolean> entry1 : statistics.entrySet()) {
            System.out.println(entry1.getKey() + " " + entry1.getValue());
        }
        System.out.println();

        for (Map.Entry<String, Boolean> entry : statistics.entrySet()) {
            String statisticString = entry.getKey();
            Boolean statisticIsShown = entry.getValue();

            if (!statisticIsShown) { // checks if the statistic is not currently being shown

                System.out.println("stat1: " + statistic1.getText());//testing

                // change statistic1 status => not shown anymore
                statistics.put(statistic1.getText(), false);
                // change statistic1 text to statisticString from statistic
                statistic1.setText(statisticString);
                // change statisticString status => changes to shown
                statistics.put(statisticString, true);

                //System.out.println("current " + statisticString);
                //System.out.println();

                break;
            }
        }

           /*//just testing
           System.out.println("stat1: " + statistic1.getText() +  " " + statistics.get(statistic1.getText()) + " aloalo");
           System.out.println("list:");
           for (Map.Entry<String, Boolean> entry2 : statistics.entrySet()) {
               System.out.println(entry2.getKey());
           }*/
    }

    @FXML
    private void backButtonStatistics2Pressed(ActionEvent e) {

        //testing
        for (Map.Entry<String, Boolean> entry1 : statistics.entrySet()) {
            System.out.println(entry1.getKey() + " " + entry1.getValue());
        }
        System.out.println();

        for (Map.Entry<String, Boolean> entry : statistics.entrySet()) {
            String statisticString = entry.getKey();
            Boolean statisticIsShown = entry.getValue();

            if (!statisticIsShown) { // checks if the statistic is not currently being shown

                System.out.println("stat1: " + statistic2.getText());//testing

                // change statistic1 status => not shown anymore
                statistics.put(statistic2.getText(), false);
                // change statistic1 text to statisticString from statistic
                statistic2.setText(statisticString);
                // change statisticString status => changes to shown
                statistics.put(statisticString, true);

                System.out.println("current " + statisticString);
                System.out.println();

                break;
            }
        }

        /*//just testing
        System.out.println("stat1: " + statistic2.getText() +  " " + statistics.get(statistic2.getText()) + " aloalo");
        System.out.println("list:");
        for (Map.Entry<String, Boolean> entry2 : statistics.entrySet()) {
            System.out.println(entry2.getKey() + " " + entry2.getValue());
        }*/
    }

    @FXML
    private void backButtonStatistics3Pressed(ActionEvent e) {

        //testing
        /*for (Map.Entry<String, Boolean> entry1 : statistics.entrySet()) {
            System.out.println(entry1.getKey() + " " + entry1.getValue());
        }
        System.out.println();*/

        for (Map.Entry<String, Boolean> entry : statistics.entrySet()) {
            String statisticString = entry.getKey();
            Boolean statisticIsShown = entry.getValue();

            if (!statisticIsShown) { // checks if the statistic is not currently being shown

                System.out.println("stat1: " + statistic3.getText());//testing

                // change statistic1 status => not shown anymore
                statistics.put(statistic3.getText(), false);
                // change statistic1 text to statisticString from statistic
                statistic3.setText(statisticString);
                // change statisticString status => changes to shown
                statistics.put(statisticString, true);

                System.out.println("current " + statisticString);
                System.out.println();

                break;
            }
        }

        /*//just testing
        System.out.println("stat1: " + statistic3.getText() +  " " + statistics.get(statistic3.getText()) + " aloalo");
        System.out.println("list:");
        for (Map.Entry<String, Boolean> entry2 : statistics.entrySet()) {
            System.out.println(entry2.getKey() + " " + entry2.getValue());
        }*/
    }

    @FXML
    private void backButtonStatistics4Pressed(ActionEvent e) {

        /*//testing
        for (Map.Entry<String, Boolean> entry1 : statistics.entrySet()) {
            System.out.println(entry1.getKey() + " " + entry1.getValue());
        }
        System.out.println();*/

        for (Map.Entry<String, Boolean> entry : statistics.entrySet()) {
            String statisticString = entry.getKey();
            Boolean statisticIsShown = entry.getValue();

            if (!statisticIsShown) { // checks if the statistic is not currently being shown

                System.out.println("stat1: " + statistic4.getText());//testing

                // change statistic1 status => not shown anymore
                statistics.put(statistic4.getText(), false);
                // change statistic1 text to statisticString from statistic
                statistic4.setText(statisticString);
                // change statisticString status => changes to shown
                statistics.put(statisticString, true);

                System.out.println("current " + statisticString);
                System.out.println();

                break;
            }
        }

        /*//just testing
        System.out.println("stat1: " + statistic4.getText() +  " " + statistics.get(statistic4.getText()) + " aloalo");
        System.out.println("list:");
        for (Map.Entry<String, Boolean> entry2 : statistics.entrySet()) {
            System.out.println(entry2.getKey() + " " + entry2.getValue());
        }*/
    }

    // returns an int average
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
    // !!!based on the price range!!!

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
        //boroughs = new HashMap<>(); unde ar trb boroughs initializata??
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

    //get most number of reviews with last review after a certain date??

    // cheapest host

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
        return hostWithMostProperties + " " + mostNumberOfProperties;
    }

    private void setNumberOfPropertiesForEachHost() {
        //hosts = new HashMap<>();
        int numberOfProperties = 0;
        for(AirbnbListing p: properties) {
            String hostId = p.getHost_id();
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

    /*
    private String setTotalPriceForEachHost() {
        hosts = new HashMap<>();
        for(AirbnbListing p: properties) {
            String hostId = p.getHost_id();
            int totalHostPrice = 0;
            if( !hosts.containsKey(hostId) ) {
                totalHostPrice = p.getMinimumPrice();
                hosts.put(hostId, totalHostPrice);
            }
            else {
                totalHostPrice = hosts.get(hostId) + p.getMinimumPrice();
                hosts.put(hostId, totalHostPrice);
            }
        }
    }*/

    // most number of reviews based on 10 cheapest hosts??

    // average price per night based on most 10 reviewed properties??

    // most western??

    private String getMostReviewedProperty() {
        String mostReviewedProperty = "";
        int maxNumberOfReviews = 0;
        for(AirbnbListing p: properties) {
            if(maxNumberOfReviews <= p.getNumberOfReviews()) {
                maxNumberOfReviews = p.getNumberOfReviews();
                mostReviewedProperty = p.getName();
            }
        }
        return mostReviewedProperty + " " + maxNumberOfReviews;
    }


}