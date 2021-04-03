
package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;


public class MapController {

    private WelcomeController welcomeController;
    private AirbnbDataLoader dataLoader;
    private Account currentAccount;
    private PropertyListController listOfProperties;
    private ArrayList<AirbnbListing> boroughListings;
    private ArrayList<Stage> listOfPropertyListStages;
    private ArrayList<PropertyListController> listOfPropertyListControllers;
    private HashMap<String, Stage> isStageOpenMap;
    private boolean isPropertyListWindowOpen;

    @FXML
    private Button ENFI, BARN, HRGY, WALT, HRRW, BREN, CAMD, ISLI, HACK, REDB, HAVE, HILL, EALI, KENS, WSTM, TOWH, NEWH, BARK, HOUN, HAMM, WAND, CITY, GWCH, BEXL, RICH, MERT, LAMB, STHW, LEWS, KING, SUTT, CROY, BROM;

    private ArrayList<Button> hexagons;

    public void initialize(WelcomeController welcomeController, AirbnbDataLoader dataLoader){
        this.welcomeController = welcomeController;
        this.dataLoader = dataLoader;
        this.hexagons = new ArrayList<>(Arrays.asList(ENFI, BARN, HRGY, WALT, HRRW, BREN, CAMD, ISLI, HACK, REDB, HAVE, HILL, EALI, KENS, WSTM, TOWH, NEWH, BARK, HOUN, HAMM, WAND, CITY, GWCH, BEXL, RICH, MERT, LAMB, STHW, LEWS, KING, SUTT, CROY, BROM));
        listOfPropertyListStages = new ArrayList<>();
        listOfPropertyListControllers = new ArrayList<>();
        isStageOpenMap = new HashMap<>();
    }

    private void reloadBoroughListings(String boroughAbbreviation)
    {
        boroughListings = dataLoader.loadFromBoroughAtPrice(boroughAbbreviation, welcomeController.getMinPrice(), welcomeController.getMaxPrice());
    }

    @FXML
    public void boroughSearch(ActionEvent event) throws IOException
    {
        String boroughAbbreviation = ((Button) event.getSource()).getText();
        reloadBoroughListings(boroughAbbreviation);

        if(welcomeController.isNewSearch()){
            welcomeController.setNewSearch(false);
        }

        if(!listOfPropertyListStages.isEmpty()){

            FXMLLoader propertyList = getPropertyListLoader();
            Stage propertyListStage = getPropertyListStage(propertyList, boroughAbbreviation);

            if(!isStageOpenMap.containsKey(boroughAbbreviation)){

                initializePropertyListStage(boroughAbbreviation, propertyListStage, propertyList);
            }
            else{
                Stage boroughStage = isStageOpenMap.get(boroughAbbreviation);

                if(boroughStage == null) {
                    isStageOpenMap.put(boroughAbbreviation, propertyListStage);
                    initializePropertyListStage(boroughAbbreviation, propertyListStage, propertyList);
                }
                else{
                    boroughStage.close();
                    boroughStage.show();
                }
            }
        }
        else{
            loadAndInitializePropertyListStage(boroughAbbreviation);
        }
    }

    public void setColor(){
        int maxSize = 0;
        for (Button borough: hexagons) {
            int numberOfPropertiesInBorough = dataLoader.loadFromBoroughAtPrice(borough.getText(), welcomeController.getMinPrice(), welcomeController.getMaxPrice()).size();
            if(maxSize<numberOfPropertiesInBorough){
                maxSize = numberOfPropertiesInBorough;
            }
        }

        for (Button borough: hexagons) {
            int boroughSize = dataLoader.loadFromBoroughAtPrice(borough.getText(), welcomeController.getMinPrice(), welcomeController.getMaxPrice()).size();
            String hexTransparency = Integer.toHexString((int) ((boroughSize*0.001/maxSize)*255000));
            if(hexTransparency.toCharArray().length==1){
                hexTransparency = "0" + hexTransparency;
            }
            String colour = "#FF5A60";
            borough.setStyle("-fx-background-color: " + colour + hexTransparency + ";");
        }
    }


    public void loadCurrentAccount(Account currentAccount) throws IOException {
        setCurrentAccount(currentAccount);
        if(listOfProperties != null){
            listOfProperties.reload(boroughListings, currentAccount);
        }
    }

    public void setCurrentAccount(Account currentAccount)
    {
        this.currentAccount = currentAccount;
    }

    public WelcomeController getWelcomeController() {
        return welcomeController;
    }


    public PropertyListController getListOfProperties() {
        return listOfProperties;
    }

    public ArrayList<Stage> getListOfPropertyListStages() {
        return listOfPropertyListStages;
    }

    public void closeAllMapStages()
    {
        closeAllPropertyStages();
        closeAllPropertyListStages();
        clearAllTrackingLists();
    }

    private void clearAllTrackingLists()
    {
        listOfPropertyListStages.clear();
        listOfPropertyListControllers.clear();
        isStageOpenMap.clear();
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
        Stage stage = null;
        while (iterator.hasNext()) {
            stage = iterator.next();
            stage.close();
        }
    }

    private void loadAndInitializePropertyListStage(String boroughAbbreviation) throws IOException
    {
        FXMLLoader propertyList = getPropertyListLoader();
        Stage propertyListStage = getPropertyListStage(propertyList, boroughAbbreviation);
        initializePropertyListStage(boroughAbbreviation, propertyListStage, propertyList);
    }

    private void initializePropertyListStage(String boroughAbbreviation, Stage propertyListStage, FXMLLoader propertyList ) throws IOException
    {
        listOfPropertyListStages.add(propertyListStage);
        listOfProperties = propertyList.getController();
        listOfPropertyListControllers.add(listOfProperties);
        isStageOpenMap.put(boroughAbbreviation, propertyListStage);
        listOfProperties.initialize(boroughListings, currentAccount);

        propertyListStage.setOnCloseRequest(e -> {
                    isStageOpenMap.put(boroughAbbreviation, null);
                    listOfProperties.closePropertyStages();
                }
        );
        propertyListStage.show();

    }


    private FXMLLoader getPropertyListLoader()
    {
        return new FXMLLoader(getClass().getResource("AirbnbViewerList.fxml"));
    }

    private Stage getPropertyListStage(FXMLLoader propertyList, String boroughAbbreviation) throws IOException
    {
        Stage propertyListStage = propertyList.load();
        propertyListStage.setTitle("AirBnB's in " + boroughAbbreviation);

        return propertyListStage;
    }

    public AirbnbDataLoader getDataLoader() {
        return dataLoader;
    }
}
