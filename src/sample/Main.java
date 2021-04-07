package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * This class is the main class of the project. It sets up the application and launches it.
 * @author Jacqueline Ilie, Liam Clark Gutiérrez, Dexter Trower and Jad Sbaï
 * @version 29/03/2021
 */
public class Main extends Application {

    /**
     * This method is responsible for starting the application. It loads the appropriate fxml file and sets the stage and scene of the main frame.
     * It also applies a CSS stylesheet to the scene and displays the resulting stage and scene.
     * If the application is closed all other windows are simultaneously closed
     * @param primaryStage The main frame of the application in which panels will be loaded.
     * @throws IOException if the designated file is not loaded successfully
     * @throws URISyntaxException if the String indicated could not be parsed as a URI reference
     */
    public void start(Stage primaryStage) throws IOException, URISyntaxException {

        // We use the FXMLLoader class to load the fxml files created with the SceneBuilder
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        primaryStage = loader.load();
        MainController mainController = loader.getController();
        mainController.initialize(primaryStage);
        
        primaryStage.getIcons().add(new Image("sample/Images/AirbnbLogo.png"));
        primaryStage.show();
        
        primaryStage.setOnCloseRequest(windowEvent -> System.exit(0));

    }

    /**
     * Main method of the project. It launches the application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
