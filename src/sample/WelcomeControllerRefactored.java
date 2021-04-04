package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class WelcomeControllerRefactored extends MainControllerRefactored
{

    @FXML
    private VBox welcomeVBox;

    @FXML
    private Label welcomeLabel;

    private VBox infoBox;

    protected void initialize() throws IOException
    {
        Label instructionsLabel = new Label();
        instructionsLabel.setText("Instructions...");
        Button okButton = new Button();
        okButton.setOnAction(this::okAction);
        okButton.getStyleClass().add("buttons"); // This piece of styling shouldn't be here I guess ?
        infoBox = new VBox(instructionsLabel, okButton);
        infoBox.getStyleClass().add("vboxes");
    }

    @FXML
    private void printInstructions(ActionEvent e)
    {
        getWelcomeRoot().getChildren().clear();
        getWelcomeRoot().getChildren().add(infoBox);
    }

    private void okAction(ActionEvent e)
    {
        getWelcomeRoot().getChildren().clear();
        getWelcomeRoot().getChildren().add(welcomeVBox);
    }
}
