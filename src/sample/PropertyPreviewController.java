package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class PropertyPreviewController extends ListingController {

    @FXML
    private Label hostName, price, reviews, minimumNights;

    private PropertyViewController viewController;

    private Stage propertyStage;

    private boolean isPropertyWindowOpen;

    public PropertyPreviewController(Account account, AirbnbListing listing)
    {
        super(account, listing);
    }

    public void initialize() throws IOException
    {
        this.hostName.setText(getListing().getHost_name());
        this.price.setText("£" + getListing().getPrice() +" / night");
        this.reviews.setText("" + getListing().getNumberOfReviews());
        this.minimumNights.setText("Min. nights: " + getListing().getMinimumNights());
        isPropertyWindowOpen = false;
    }

    @FXML
    public void openProperty(MouseEvent e) throws IOException
    {
        if(!isPropertyWindowOpen) {

            PropertyViewController propertyViewController = new PropertyViewController(getAccount(), getListing());
            FXMLLoader property = new FXMLLoader(getClass().getResource("AirbnbView.fxml"));
            property.setController(propertyViewController);
            viewController = propertyViewController;
            propertyStage = property.load();

            propertyStage.setTitle(getListing().getHostNameWithApostrophe() + " Airbnb");
            propertyStage.show();

            isPropertyWindowOpen = true;
            propertyStage.setOnCloseRequest(event -> {
                        isPropertyWindowOpen = false;
                        viewController.setFavouriteTextLabel("");
                    }
            );
        }
        else{
            propertyStage.close();
            propertyStage.show();
        }
    }

    public void reload(AirbnbListing listing) throws IOException {
        PropertyViewController propertyViewController = new PropertyViewController(getAccount(),getListing());
        FXMLLoader property = new FXMLLoader(getClass().getResource("AirbnbView.fxml"));
        property.setController(propertyViewController);
        propertyStage = property.load();
        viewController = propertyViewController;

        viewController.reload();
    }

    public Stage getPropertyStage() {
        return propertyStage;
    }


}
