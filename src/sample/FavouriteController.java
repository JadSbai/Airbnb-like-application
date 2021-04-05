package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class FavouriteController
{
    @FXML
    private ListView<Pane> listOfFavourites;
    @FXML
    private Label emptyListLabel;

    public void initialize()
    {

    }

    public void loadFavourites() throws IOException
    {
        emptyListLabel.setText("");
        //listOfFavourites.setItems(currentAccount.getListViewOfFavourites().getItems());
        if(listOfFavourites.getItems().isEmpty()){
            emptyListLabel.setText("You currently have no favourites. Click on the \"Save\" button to add a favourite.");
        }
    }
}
