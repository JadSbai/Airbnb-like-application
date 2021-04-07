package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * This class is the controller for the instructions panel. Once opened from the welcome panel, the instructions panel
 * offers a user friendly visualization of the functionallity of the application.
 * @author Jacqueline Ilie, Liam Clark Gutiérrez, Dexter Trower and Jad Sbaï
 * @version 07/04/2021
 */
public class InstructionsController {

    /**
     *  Welcome pane
     */
    private Pane welcomePane;

    /**
     * Main border pane used for the main stagee of the application
     */
    private BorderPane mainPane;

    /**
     * Contructor for the welcome controller, sets the necesary panes
     * @param mainPane  main pane
     * @param welcomePane welcome pane
     */
    public void initialize(BorderPane mainPane, Pane welcomePane){
        this.mainPane = mainPane;
        this.welcomePane = welcomePane;
    }

    /**
     * Takes you back to the welcome pane when clicking the back button
     * @param event create when back button is clicked
     */
    @FXML
    private void goBackToWelcomePanel(ActionEvent event)
    {
        mainPane.setCenter(welcomePane);
    }

}
