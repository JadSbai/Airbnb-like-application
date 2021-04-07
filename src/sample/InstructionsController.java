package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class InstructionsController {

    private Pane welcomePane;

    private BorderPane mainPane;

    public void initialize(BorderPane mainPane, Pane welcomePane){
        this.mainPane = mainPane;
        this.welcomePane = welcomePane;
    }

    @FXML
    private void goBackToWelcomePanel(ActionEvent e)
    {
        mainPane.setCenter(welcomePane);
    }

}
