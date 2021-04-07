package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WelcomeControllerRefactored
{
    @FXML
    private Pane welcomePane;

    private Pane instructionsPane;

    private BorderPane mainPane;

    public void initialize(BorderPane mainPane) throws IOException
    {
        this.mainPane = mainPane;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Instructions.fxml"));
        instructionsPane = loader.load();
        InstructionsController instructionsController = loader.getController();
        instructionsController.initialize(mainPane, welcomePane);
    }
    @FXML
    private void showInstructions(ActionEvent e) {
        mainPane.setCenter(instructionsPane);
    }

    @FXML
    private void openJJDL(ActionEvent event) throws IOException, URISyntaxException {
        URI uri = new URI("https://github.kcl.ac.uk/k20020900/PPA-CW4");
        java.awt.Desktop.getDesktop().browse(uri);
    }

    public void setPane(Pane welcomePane) {

    }
}
