package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.*;

public class Main extends Application {




    @Override
    public void start(Stage primaryStage) throws Exception{


        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        //FXMLLoader map = new FXMLLoader(getClass().getResource("map.fxml"));
        Pane root = loader.load();
        primaryStage.setTitle("Airbnb London");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("WelcomePanelStyle.css").toURI().toString());
        primaryStage.show();
        MainController controller = loader.getController();
        controller.initialize();



    }



    public static void main(String[] args) {
        launch(args);
    }
}
