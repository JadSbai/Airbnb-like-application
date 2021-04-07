package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class is the controller for the pane that previews a listing. Displays limited information and can be clicked
 * to open the view corresponding to that listing.
 * @author Jacqueline Ilie, Liam Clark Gutiérrez, Dexter Trower and Jad Sbaï
 * @version 07/04/2021
 */
public class ListingPreviewController extends ListingController {

    /**
     * Common instance of ControllerComponents held by all classes containing common elements like the account.
     */
    private ControllerComponents controllerComponents;
    /**
     * 4 labels to provide information about the listing being previewed
     */
    @FXML
    private Label hostName, price, reviews, minimumNights;

    /**
     * Controller for the view of the listing being previewed. Only instancied when/if clicked.
     */
    private ListingViewController viewController;

    /**
     * Stage for the view of the listing being previewed. Only instancied when/if clicked.
     */
    private Stage propertyViewStage;

    /**
     * Defines if the window for the previewed property is open or not
     */
    private boolean isPropertyWindowOpen;

    /**
     * Controller for AccountDetailsController used to reload the panel if favourites or bookings change.
     */
    private AccountDetailsController accountDetailsController;

    /**
     * @param controllerComponents common instance of ControllerComponents held by all classes containing common elements like the account.
     * @param listing listing being previewed
     * @param accountDetailsController controller for AccountDetailsController used to reload the panel if favourites or bookings change.
     */
    public ListingPreviewController(ControllerComponents controllerComponents, AirbnbListing listing, AccountDetailsController accountDetailsController)
    {
        super(listing);
        this.controllerComponents = controllerComponents;
        this.accountDetailsController = accountDetailsController;
    }

    /**
     * Initializes the labels of the prieview with the values from the listing being previewed.
     * @throws IOException {@link IOException} in some circumstance
     */
    public void initialize() throws IOException
    {
        this.hostName.setText(getListing().getHost_name());
        this.price.setText("£" + getListing().getPrice() +" / night");
        this.reviews.setText("" + getListing().getNumberOfReviews());
        this.minimumNights.setText("Min. nights: " + getListing().getMinimumNights());
        isPropertyWindowOpen = false;
    }

    /**
     * Opens the view that matches the preview being clicked.
     * @param event created when a preview is clicked
     * @throws IOException {@link IOException} in some circumstance
     */
    @FXML
    public void openProperty(MouseEvent event) throws IOException
    {
        if(!isPropertyWindowOpen) {

            ListingViewController listingViewController = new ListingViewController(controllerComponents, getListing(), accountDetailsController);
            FXMLLoader property = new FXMLLoader(getClass().getResource("ListingView.fxml"));
            property.setController(listingViewController);
            viewController = listingViewController;
            propertyViewStage = property.load();

            propertyViewStage.setTitle(getListing().getHostNameWithApostrophe() + " Airbnb");
            propertyViewStage.show();

            isPropertyWindowOpen = true;
            propertyViewStage.setOnCloseRequest(e -> {
                        isPropertyWindowOpen = false;
                        viewController.setFavouriteTextLabel("");
                    }
            );
        }
        else{
            propertyViewStage.close();
            propertyViewStage.show();
        }
    }

    /**
     * Reloads the viewController when something changes in the AccountDetailsController.
     * @throws IOException {@link IOException} in some circumstance
     */
    public void reload() throws IOException {
        ListingViewController listingViewController = new ListingViewController(controllerComponents, getListing(), accountDetailsController);
        FXMLLoader property = new FXMLLoader(getClass().getResource("ListingView.fxml"));
        property.setController(listingViewController);
        propertyViewStage = property.load();
        viewController = listingViewController;

        viewController.reload();
    }
    
    /**
     * Returns the stage for the view of the listing being previewed
     * @return Stage propertyViewStage
     */
    public Stage getPropertyViewStage(){
        return propertyViewStage;
    }

}
