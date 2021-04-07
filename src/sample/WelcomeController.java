package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * This class is the controller for the welcome panel. The welcome panel offers a visual home page for the application.
 * @author Jacqueline Ilie, Liam Clark Gutiérrez, Dexter Trower and Jad Sbaï
 * @version 07/04/2021
 */
public class WelcomeController
{
    /**
     * Welcome panel
     */
    @FXML
    private Pane welcomePane;

    /**
     * Instructions panel
     */
    private Pane instructionsPane;

    /**
     * Main border pane used for the main stagee of the application
     */
    private BorderPane mainPane;

    /**
     * Initializes the panel and creates the instance of InstructionsController.
     * @param mainPane main border pane
     * @throws IOException {@link URISyntaxException;} in some circumstance
     */
    public void initialize(BorderPane mainPane) throws IOException
    {
        this.mainPane = mainPane;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Instructions.fxml"));
        instructionsPane = loader.load();
        InstructionsController instructionsController = loader.getController();
        instructionsController.initialize(mainPane, welcomePane);
    }

    /**
     * Opens the instructions panel
     */
    @FXML
    private void showInstructions() {
        mainPane.setCenter(instructionsPane);
    }

    /**
     * Link for the repository of our project. Opens in your browser
     * @throws IOException {@link IOException} in some circumstance
     * @throws URISyntaxException {@link URISyntaxException;} in some circumstance
     */
    @FXML
    private void openJJDL() throws IOException, URISyntaxException {
        URI uri = new URI("https://github.kcl.ac.uk/k20020900/PPA-CW4");
        java.awt.Desktop.getDesktop().browse(uri);
    }
}
