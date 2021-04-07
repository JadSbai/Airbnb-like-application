 package sample;


import javafx.scene.paint.ImagePattern;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
 import javafx.stage.Stage;

 import java.util.ArrayList;
 import java.util.HashMap;

 public abstract class AccountController
 {
     private ControllerComponents controllerComponents;
     
     private Stage accountStage;

     private BorderPane accountPanel;

     private ArrayList<Account> listOfAccounts;

     private HashMap<String, Account> accountsMap;

     public AccountController(ControllerComponents controllerComponents, Stage accountStage)
     {
         this.controllerComponents = controllerComponents;
         listOfAccounts = new ArrayList<>();
         accountsMap = new HashMap<>();
         this.accountStage = accountStage;
         if(accountStage != null){
             this.accountPanel = (BorderPane) accountStage.getScene().getRoot();
         }
     }

     public Stage getAccountStage(){
         return accountStage;
     }

     public void setAccountStage(Stage accountStage){
         this.accountStage = accountStage;
     }

     /**
      * List of current accounts created in the app
      */
     public ArrayList<Account> getListOfAccounts() {
         return listOfAccounts;
     }

     /**
      * Maps the email of each account to the corresponding account object
      */
     public HashMap<String, Account> getAccountsMap() {
         return accountsMap;
     }

     public BorderPane getAccountPanel() {
         return accountPanel;
     }

    public void updateProfilePictures()
    {
        ArrayList<Circle> accountCircles = AccountCircles.getInstance().getAccountCircles();

        for (Circle circle : accountCircles)
        {
            circle.setFill(new ImagePattern(controllerComponents.getAccount().getProfilePicture()));
        }

    }

 }
