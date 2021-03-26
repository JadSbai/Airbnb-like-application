package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static sample.Controller.MAX_VALUE;

public class Main extends Application {


    //Controller controller;
    MapController mapController;


    @Override
    public void start(Stage primaryStage) throws IOException {

        //mapController = new MapController();
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("airbnb.fxml"));
        FXMLLoader map = new FXMLLoader(getClass().getResource("map.fxml"));
        ScrollPane root = map.load();
        primaryStage.setTitle("Airbnb London");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        //scene.getStylesheets().add(getClass().getResource("WelcomePanelStyle.css").toURI().toString());
        primaryStage.show();

        mapController = map.getController();
        mapController.initialize();

        //controller = loader.getController();
        //controller.initialize();



    }

    public static void main(String[] args) {
        launch(args);
    }
}
