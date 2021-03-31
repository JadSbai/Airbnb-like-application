package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.*;

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
     * @param primaryStage The main frame of the application in which panels will be loaded.
     * @throws IOException if the designated file is not loaded successfully
     * @throws URISyntaxException if the String indicated could not be parsed as a URI reference
     */
    public void start(Stage primaryStage) throws IOException, URISyntaxException {

        // We use the FXMLLoader class to load the fxml files created with the SceneBuilder
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        // The root of panel is retrieved from the fxml file
        Pane root = loader.load();
        MainController mainController = loader.getController();
        FXMLLoader popUpLoader = new FXMLLoader(getClass().getResource("accountPopUpMenu.fxml"));
        popUpLoader.setController(mainController.getAccountController());
        VBox popUpRoot = popUpLoader.load();
        root.getChildren().add(popUpRoot);

        primaryStage.setTitle("Airbnb London");
        // The scene is set with the retrieved root
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("WelcomePanelStyle.css").toURI().toString());
        primaryStage.show();
        primaryStage.setOnCloseRequest(windowEvent -> System.exit(0));

    }

    /**
     * This is the main method of the project. It launches the application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
