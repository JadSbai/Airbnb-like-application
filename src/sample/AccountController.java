 package sample;

 import javafx.scene.layout.BorderPane;
 import javafx.scene.layout.Pane;
 import javafx.scene.shape.Circle;
 import javafx.stage.Stage;

 import java.util.ArrayList;
 import java.util.HashMap;

 public abstract class AccountController extends Controller
 {
     private Stage accountStage;

     private BorderPane accountPanel;

     private ArrayList<Account> listOfAccounts;

     private HashMap<String, Account> accountsMap;

     public AccountController(Account account, Stage accountStage){
         super(account);
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

//     public void updateProfilePictures()
//     {
//         ArrayList<Circle> accountCircles = AccountCircles.getInstance();
//
//         for(Circle circle : )
//     }

 }
