package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;


public class MapController
{
    private ControllerComponents controllerComponents;
    @FXML
    private Button ENFI, BARN, HRGY, WALT, HRRW, BREN, CAMD, ISLI, HACK, REDB, HAVE, HILL, EALI, KENS, WSTM, TOWH, NEWH, BARK, HOUN, HAMM, WAND, CITY, GWCH, BEXL, RICH, MERT, LAMB, STHW, LEWS, KING, SUTT, CROY, BROM;

    private PropertyListController listOfProperties;
    private ArrayList<PropertyListController> listOfPropertyListControllers;

    private Stage propertyListStage;
    private ArrayList<Stage> listOfPropertyListStages;

    private ArrayList<Button> hexagons;

    private int minPrice, maxPrice;

    private AccountDetailsController accountDetailsController;

    public MapController(ControllerComponents controllerComponents){
        this.controllerComponents = controllerComponents;
    }


    public void initialize() throws IOException
    {
        hexagons = new ArrayList<>(Arrays.asList(ENFI, BARN, HRGY, WALT, HRRW, BREN, CAMD, ISLI, HACK, REDB, HAVE, HILL, EALI, KENS, WSTM, TOWH, NEWH, BARK, HOUN, HAMM, WAND, CITY, GWCH, BEXL, RICH, MERT, LAMB, STHW, LEWS, KING, SUTT, CROY, BROM));
        listOfPropertyListStages = new ArrayList<>();
        listOfPropertyListControllers = new ArrayList<>();
    }

    @FXML
    public void boroughSearch(ActionEvent event) throws IOException
    {
        String boroughAbbreviation = ((Button) event.getSource()).getText();
        ArrayList<AirbnbListing> boroughListings = ControllerComponents.getDataLoader().loadFromBoroughAtPrice(boroughAbbreviation, minPrice, maxPrice);

        if(!listOfPropertyListStages.isEmpty()){


            PropertyListController propertyListController = new PropertyListController(controllerComponents, accountDetailsController);
            FXMLLoader propertyList = new FXMLLoader(getClass().getResource("AirbnbViewerList.fxml"));
            setPropertyListStage(propertyList, propertyListController, boroughAbbreviation);

            if(!propertyListStage.isShowing()) {
                initializePropertyListStage(propertyList, boroughListings);
            }
            else{
                propertyListStage.close();
                propertyListStage.show();
            }
        }
        else{
            loadAndInitializePropertyListStage(boroughAbbreviation, boroughListings);
        }
    }

    private void loadAndInitializePropertyListStage(String boroughAbbreviation, ArrayList<AirbnbListing> boroughListings) throws IOException
    {
        PropertyListController propertyListController = new PropertyListController(controllerComponents, accountDetailsController);
        FXMLLoader propertyList = new FXMLLoader(getClass().getResource("AirbnbViewerList.fxml"));
        setPropertyListStage(propertyList, propertyListController, boroughAbbreviation);
        initializePropertyListStage(propertyList, boroughListings);
    }

    private void setPropertyListStage(FXMLLoader propertyList, PropertyListController controller, String boroughAbbreviation) throws IOException
    {
        propertyList.setController(controller);
        propertyListStage = propertyList.load();
        propertyListStage.setTitle("AirBnB's in " + AirbnbListing.getFullBoroughName(boroughAbbreviation));
    }

    private void initializePropertyListStage(FXMLLoader propertyList, ArrayList<AirbnbListing> boroughListings ) throws IOException
    {
        listOfPropertyListStages.add(propertyListStage);
        listOfProperties = propertyList.getController();
        listOfPropertyListControllers.add(listOfProperties);
        listOfProperties.initialize(boroughListings);
        propertyListStage.show();
   }

    public void setColor(){
        int maxSize = 0;
        for (Button borough: hexagons) {
            int numberOfPropertiesInBorough = ControllerComponents.getDataLoader().loadFromBoroughAtPrice(borough.getText(), minPrice, maxPrice).size();
            if(maxSize<numberOfPropertiesInBorough){
                maxSize = numberOfPropertiesInBorough;
            }
        }

        for (Button borough: hexagons) {
            int boroughSize = ControllerComponents.getDataLoader().loadFromBoroughAtPrice(borough.getText(), minPrice, maxPrice).size();
            String hexTransparency = Integer.toHexString((int) ((boroughSize*0.001/maxSize)*255000));
            if(hexTransparency.toCharArray().length==1){
                hexTransparency = "0" + hexTransparency;
            }
            String colour = "#FF5A60";
            borough.setStyle("-fx-background-color: " + colour + hexTransparency + ";");
        }
    }

    public void closeAllMapStages()
    {
        closeAllPropertyStages();
        closeAllPropertyListStages();
        clearAllTrackingLists();
    }

    private void closeAllPropertyStages()
    {
        for(PropertyListController propertyListController : listOfPropertyListControllers){
            if(propertyListController != null){
                propertyListController.closePropertyStages();
            }
        }
    }

    private void closeAllPropertyListStages()
    {
        Iterator<Stage> iterator = listOfPropertyListStages.iterator();
        Stage stage;
        while (iterator.hasNext()) {
            stage = iterator.next();
            stage.close();
        }
    }

    private void clearAllTrackingLists()
    {
        listOfPropertyListStages.clear();
        listOfPropertyListControllers.clear();
    }

    public void loadCurrentAccount() throws IOException {
        for(PropertyListController propertyListController : listOfPropertyListControllers){
            if(propertyListController != null){
                propertyListController.reload();
            }
            
        }
    }
    public void setPriceRange(int minPrice, int maxPrice)
    {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }
    public void addAccountController(AccountDetailsController accountDetailsController){
        this.accountDetailsController = accountDetailsController;
    }
}
