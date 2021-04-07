import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sample.AirbnbListing;
import sample.ControllerComponents;
import sample.StatisticsController;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


public class StatisticsControllerTest {
    private static final StatisticsController sc = new StatisticsController(new ControllerComponents(null));
    private final ArrayList<AirbnbListing> properties = sc.getProperties();
    private static ArrayList<AbstractMap.SimpleEntry<Integer, Integer>> priceRanges;
    private static final HashMap<Button, Label> buttonToLabelMap = sc.getButtonToLabelMap();
    private static Set<Button> buttons;
    private static List<Label> labels;

    @BeforeAll
    static void setup() throws IOException {
        // Statement to get away with Toolkit not initialized error.
        JFXPanel fxPanel = new JFXPanel();
        FXMLLoader loader = new FXMLLoader(StatisticsControllerTest.class.getResource("sample/StatisticsPanel.fxml"));
        loader.setController(sc);
        loader.load();
        generateAllRanges(0, 450, 50);
        sc.setPriceRange(0, 50);
        buttons = new HashSet<>(List.of(sc.getBackButtonStatistics1(), sc.getBackButtonStatistics2(), sc.getBackButtonStatistics3(),
                sc.getBackButtonStatistics4(), sc.getForwardButtonStatistics1(), sc.getForwardButtonStatistics2(), sc.getForwardButtonStatistics3(), sc.getForwardButtonStatistics4()));
        labels = new ArrayList<>(List.of(sc.getStatistic1(), sc.getStatistic2(), sc.getStatistic3(), sc.getStatistic4(), sc.getStatistic1(), sc.getStatistic2(), sc.getStatistic3(), sc.getStatistic4()));
    }

    static void generateAllRanges(int lowerBound, int upperBound, int step) {
        priceRanges = new ArrayList<>();
        for (int i = lowerBound; i <= upperBound; i += step) {
            for (int j = i + step; j <= upperBound; j += step) {
                priceRanges.add(new AbstractMap.SimpleEntry<>(i, j));
            }
        }
    }

    private Object extractMethodResult(String methodName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = StatisticsController.class.getDeclaredMethod(methodName);
        method.setAccessible(true);
        return method.invoke(sc);
    }

    private String getHostIdentity(AirbnbListing l) {
        return l.getHost_id() + " " + l.getHost_name();
    }


    @Test
    public void the_button_to_label_map_is_initialized_properly() {
        for (AbstractMap.SimpleEntry<Integer, Integer> e : priceRanges) {
            sc.setPriceRange(e.getKey(), e.getValue());
            Collection<Label> labelMapValues = buttonToLabelMap.values();
            assert buttonToLabelMap.keySet().equals(buttons);
            assert labelMapValues.containsAll(labels) && labels.containsAll(labelMapValues);
            assert (buttonToLabelMap.size() == 8);
        }
    }

    @Test
    public void there_are_eight_stats_that_can_be_displayed_in_total() {
        for (AbstractMap.SimpleEntry<Integer, Integer> e : priceRanges) {
            sc.setPriceRange(e.getKey(), e.getValue());
            HashMap<String, Boolean> statistics = sc.getStatistics();
            assert (statistics.size() == 8);
        }
    }

    // No stat is displayed twice
    @Test
    public void four_distinct_stats_are_displayed() {
        for (AbstractMap.SimpleEntry<Integer, Integer> e : priceRanges) {
            sc.setPriceRange(e.getKey(), e.getValue());
            HashMap<String, Boolean> statistics = sc.getStatistics();
            int numberOfShowedStats = (int) statistics.entrySet().stream().filter(Map.Entry::getValue).count();
            assert (numberOfShowedStats == 4);
        }
    }

    private int getAverageNumberOfReviews() {
        int totalNumberOfReviews = properties.stream().mapToInt(AirbnbListing::getNumberOfReviews).sum();
        return totalNumberOfReviews / properties.size();
    }

    @Test
    public void average_number_of_reviews_is_correct() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        for (AbstractMap.SimpleEntry<Integer, Integer> e : priceRanges) {
            sc.setPriceRange(e.getKey(), e.getValue());
            int averageNumberOfReviews = getAverageNumberOfReviews();
            int supposedAverageNumberOfReviews = (int) extractMethodResult("getAverageNumberOfReviews");
            assert supposedAverageNumberOfReviews == averageNumberOfReviews;
        }

    }

    private int getAveragePricePerNight() {
        int totalPricePerNight = properties.stream().mapToInt(AirbnbListing::getPrice).sum();
        return totalPricePerNight / properties.size();
    }

    @Test
    public void average_price_per_night_is_correct() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        for (AbstractMap.SimpleEntry<Integer, Integer> e : priceRanges) {
            sc.setPriceRange(e.getKey(), e.getValue());
            int averagePricePerNight = getAveragePricePerNight();
            int supposedAveragePricePerNight = (int) extractMethodResult("getAveragePricePerNight");
            assert supposedAveragePricePerNight == averagePricePerNight;
        }
    }

    private int getNumberOfAvailableProperties() {
        return (int) properties.stream().filter(p -> p.getAvailability365() > 0).count();
    }

    @Test
    public void number_of_available_properties_is_correct() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        for (AbstractMap.SimpleEntry<Integer, Integer> e : priceRanges) {
            sc.setPriceRange(e.getKey(), e.getValue());
            int numberOfAvailableProperties = getNumberOfAvailableProperties();
            int supposedNumberOfAvailableProperties = (int) extractMethodResult("getNumberOfAvailableProperties");
            assert numberOfAvailableProperties == supposedNumberOfAvailableProperties;
        }

    }

    private HashMap<String, Integer> getNumberOfPropertiesPerHost() {
        HashMap<String, Integer> hostIdsOccurences = new HashMap<>();
        for (AirbnbListing l : properties) {
            String hostId = getHostIdentity(l);
            int value = hostIdsOccurences.containsKey(hostId) ? hostIdsOccurences.get(hostId) + 1 : 1;
            hostIdsOccurences.put(hostId, value);
        }
        return hostIdsOccurences;
    }

    @Test
    public void number_of_properties_per_host_is_correct() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        for (AbstractMap.SimpleEntry<Integer, Integer> e : priceRanges) {
            sc.setPriceRange(e.getKey(), e.getValue());
            HashMap<String, Integer> hostIdsOccurences = getNumberOfPropertiesPerHost();
            assert (hostIdsOccurences.equals(sc.getHostsNumberOfProperties()));
        }
    }


    private String getHostWithMostProperties() {
        return Collections.max(sc.getHostsNumberOfProperties().entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
    }

    @Test
    public void host_with_most_properties_is_correct() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        for (AbstractMap.SimpleEntry<Integer, Integer> e : priceRanges) {
            sc.setPriceRange(e.getKey(), e.getValue());
            String hostWithMostProperties = getHostWithMostProperties();
            String supposedHostWithMostProperties = (String) extractMethodResult("getHostWithMostProperties");
            assert hostWithMostProperties.equals(supposedHostWithMostProperties);
        }
    }

    private int getNumberOfEntireHomeApartments() {
        return (int) properties.stream().filter(p -> p.getRoom_type().equals("Entire home/apt")).count();
    }

    @Test
    public void number_of_entire_home_apartments_is_correct() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        for (AbstractMap.SimpleEntry<Integer, Integer> e : priceRanges) {
            sc.setPriceRange(e.getKey(), e.getValue());
            int numberOfEntireHomeApartments = getNumberOfEntireHomeApartments();
            int supposedNumberOfEntireHomeApartments = (int) extractMethodResult("getNumberOfEntireHomeOrApartments");
            assert numberOfEntireHomeApartments == supposedNumberOfEntireHomeApartments;
        }
    }

    private HashMap<String, Integer> getPricePerBorough() {
        HashMap<String, Integer> boroughTotalPrices = new HashMap<>();
        for (AirbnbListing l : properties) {
            String borough = l.getNeighbourhood();
            int price = l.getMinimumPrice();
            int totalPrice = boroughTotalPrices.containsKey(borough) ? boroughTotalPrices.get(borough) + price : price;
            boroughTotalPrices.put(borough, totalPrice);
        }
        return boroughTotalPrices;
    }

    @Test
    public void total_price_per_borough_is_correct() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        for (AbstractMap.SimpleEntry<Integer, Integer> e : priceRanges) {
            sc.setPriceRange(e.getKey(), e.getValue());
            HashMap<String, Integer> totalPricePerBorough = getPricePerBorough();
            assert (totalPricePerBorough.equals(sc.getBoroughs()));
        }
    }

    private String getMostExpensiveBorough() {
        return Collections.max(sc.getBoroughs().entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
    }

    @Test
    public void most_expensive_borough_is_correct() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        for (AbstractMap.SimpleEntry<Integer, Integer> e : priceRanges) {
            sc.setPriceRange(e.getKey(), e.getValue());
            String mostExpensiveBorough = getMostExpensiveBorough();
            String supposedMostExpensiveBorough = (String) extractMethodResult("getMostExpensiveBorough");
            assert mostExpensiveBorough.equals(supposedMostExpensiveBorough);
        }
    }

    private AirbnbListing getMostReviewedProperty() {
        return properties.stream().max(Comparator.comparing(AirbnbListing::getNumberOfReviews)).orElseThrow(NoSuchElementException::new);
    }

    @Test
    public void most_reviewed_property_is_correct() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        for (AbstractMap.SimpleEntry<Integer, Integer> e : priceRanges) {
            sc.setPriceRange(e.getKey(), e.getValue());
            AirbnbListing mostReviewedProperty = getMostReviewedProperty();
            int mostReviews = mostReviewedProperty.getNumberOfReviews();
            String mostReviewedPropertyName = mostReviewedProperty.getName() + " (" + mostReviews + " reviews)";
            String supposedMostReviewedPropertyName = (String) extractMethodResult("getMostReviewedProperty");
            assert mostReviewedPropertyName.equals(supposedMostReviewedPropertyName);
        }
    }

    private HashMap<String, Integer> getTotalPricePerHost() {
        HashMap<String, Integer> hostsTotalPrices = new HashMap<>();
        for (AirbnbListing l : properties) {
            String hostId = getHostIdentity(l);
            int price = l.getMinimumPrice();
            int totalPrice = hostsTotalPrices.containsKey(hostId) ? hostsTotalPrices.get(hostId) + price : price;
            hostsTotalPrices.put(hostId, totalPrice);
        }
        return hostsTotalPrices;
    }

    @Test
    public void total_price_per_host_is_correct() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        for (AbstractMap.SimpleEntry<Integer, Integer> e : priceRanges) {
            sc.setPriceRange(e.getKey(), e.getValue());
            HashMap<String, Integer> totalPricePerHost = getTotalPricePerHost();
            assert (totalPricePerHost.equals(sc.getHostsPrice()));
        }
    }

    private String getCheapestHost() {
        return Collections.min(sc.getHostsPrice().entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
    }

    @Test
    public void cheapest_host_is_correct() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        for (AbstractMap.SimpleEntry<Integer, Integer> e : priceRanges) {
            sc.setPriceRange(e.getKey(), e.getValue());
            String cheapestHost = getCheapestHost();
            String supposedCheapestHost = (String) extractMethodResult("getCheapestHost");
            assert cheapestHost.equals(supposedCheapestHost);
        }
    }
}