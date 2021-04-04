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


public class MapControllerRefactored extends MainControllerRefactored
{
    @FXML
    private Button ENFI, BARN, HRGY, WALT, HRRW, BREN, CAMD, ISLI, HACK, REDB, HAVE, HILL, EALI, KENS, WSTM, TOWH, NEWH, BARK, HOUN, HAMM, WAND, CITY, GWCH, BEXL, RICH, MERT, LAMB, STHW, LEWS, KING, SUTT, CROY, BROM;

    private ArrayList<AirbnbListing> boroughListings;

    private PropertyListController listOfProperties;
    private ArrayList<PropertyListController> listOfPropertyListControllers;

    private Stage propertyListStage;
    private ArrayList<Stage> listOfPropertyListStages;

    private ArrayList<Button> hexagons;


    protected void initialize() throws IOException
    {
        hexagons = new ArrayList<>(Arrays.asList(ENFI, BARN, HRGY, WALT, HRRW, BREN, CAMD, ISLI, HACK, REDB, HAVE, HILL, EALI, KENS, WSTM, TOWH, NEWH, BARK, HOUN, HAMM, WAND, CITY, GWCH, BEXL, RICH, MERT, LAMB, STHW, LEWS, KING, SUTT, CROY, BROM));
        listOfPropertyListStages = new ArrayList<>();
        listOfPropertyListControllers = new ArrayList<>();
    }

    @FXML
    public void boroughSearch(ActionEvent event) throws IOException
    {
        String boroughAbbreviation = ((Button) event.getSource()).getText();
        reloadBoroughListings(boroughAbbreviation);

        if(isNewSearch()){
            setNewSearch(false);
        }

        if(!listOfPropertyListStages.isEmpty()){

            FXMLLoader propertyList = getPropertyListLoader();
            setPropertyListStage(propertyList, boroughAbbreviation);

            if(!propertyListStage.isShowing()) {
                initializePropertyListStage(propertyList);
            }
            else{
                propertyListStage.close();
                propertyListStage.show();
            }
        }
        else{
            loadAndInitializePropertyListStage(boroughAbbreviation);
        }
    }

    private void reloadBoroughListings(String boroughAbbreviation)
    {
        boroughListings = dataLoader.loadFromBoroughAtPrice(boroughAbbreviation, getMinPrice(), getMaxPrice());
    }

    private void loadAndInitializePropertyListStage(String boroughAbbreviation) throws IOException
    {
        FXMLLoader propertyList = getPropertyListLoader();
        setPropertyListStage(propertyList, boroughAbbreviation);
        initializePropertyListStage(propertyList);
    }

    private void initializePropertyListStage(FXMLLoader propertyList ) throws IOException
    {
        listOfPropertyListStages.add(propertyListStage);
        listOfProperties = propertyList.getController();
        listOfPropertyListControllers.add(listOfProperties);
        listOfProperties.initialize(boroughListings, getCurrentAccount());
        propertyListStage.show();
    }

    private FXMLLoader getPropertyListLoader()
    {
        return new FXMLLoader(getClass().getResource("AirbnbViewerList.fxml"));
    }

    private void setPropertyListStage(FXMLLoader propertyList, String boroughAbbreviation) throws IOException
    {
        propertyListStage = propertyList.load();
        propertyListStage.setTitle("AirBnB's in " + AirbnbListing.getFullBoroughName(boroughAbbreviation));
    }

    public void setColor(){
        int maxSize = 0;
        for (Button borough: hexagons) {
            int numberOfPropertiesInBorough = dataLoader.loadFromBoroughAtPrice(borough.getText(), getMinPrice(), getMaxPrice()).size();
            if(maxSize<numberOfPropertiesInBorough){
                maxSize = numberOfPropertiesInBorough;
            }
        }

        for (Button borough: hexagons) {
            int boroughSize = dataLoader.loadFromBoroughAtPrice(borough.getText(), getMinPrice(), getMaxPrice()).size();
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

    public void loadCurrentAccount(Account currentAccount) throws IOException {
        setCurrentAccount(currentAccount);
        if(listOfProperties != null){
            listOfProperties.reload(boroughListings, currentAccount);
        }
    }
}
