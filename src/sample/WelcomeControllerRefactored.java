package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class WelcomeControllerRefactored
{
    private ControllerComponents controllerComponents;
    @FXML
    private VBox welcomeVBox;

    @FXML
    private Label welcomeLabel;

    private VBox infoBox;

    private Pane welcomeRoot;
    
    public WelcomeControllerRefactored(ControllerComponents controllerComponents){
        this.controllerComponents = controllerComponents;
    }

    public void initialize() throws IOException
    {
        Label instructionsLabel = new Label();
        instructionsLabel.setText("Instructions...");
        Button okButton = new Button();
        okButton.setOnAction(this::okAction);
        okButton.getStyleClass().add("buttons"); // This piece of styling shouldn't be here I guess ?
        infoBox = new VBox(instructionsLabel, okButton);
        infoBox.getStyleClass().add("vboxes");
    }
    
    public void setWelcomeRoot(Pane root)
        {
            this.welcomeRoot = root;
        }

    @FXML
    private void printInstructions(ActionEvent e)
    {
        welcomeRoot.getChildren().clear();
        welcomeRoot.getChildren().add(infoBox);
    }

    private void okAction(ActionEvent e)
    {
        welcomeRoot.getChildren().clear();
        welcomeRoot.getChildren().add(welcomeVBox);
    }
}
