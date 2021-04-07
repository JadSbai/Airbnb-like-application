 package sample;


import javafx.scene.control.Label;
import javafx.scene.paint.ImagePattern;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

 /**
  * This abstract class is the superclass to all account-related controllers and holds common fields used in all of those.
  * It contains methods that serve general purposes such as password verification...
  * @author Jacqueline Ilie, Liam Clark Gutiérrez, Dexter Trower and Jad Sbaï
  * @version 08/04/2021
  */
 public abstract class AccountController
 {

    /**
    * A ControllerComponents object
    */
     private ControllerComponents controllerComponents;

     /**
     * The stage where the account's settings and details will be displayed
     */
     private Stage accountStage;

    /**
    * The BorderPane where the account settings and details will be loaded
    */
     private BorderPane accountPanel;

    /**
    * A list of all the app's accounts
    */
     private ArrayList<Account> listOfAccounts;

    /**
    * HashMap that maps each email to its account
    */
     private HashMap<String, Account> emailToAccountMap;


     /**
      * The constructor initializes all the fields
      * @param controllerComponents The common controller components instance
      * @param accountStage The account stage used to display the account's
      */
     public AccountController(ControllerComponents controllerComponents, Stage accountStage)
     {
         this.controllerComponents = controllerComponents;
         listOfAccounts = new ArrayList<>();
         emailToAccountMap = new HashMap<>();
         this.accountStage = accountStage;
         if(accountStage != null){
             this.accountPanel = (BorderPane) accountStage.getScene().getRoot();
         }
     }

    /**
    * This method closes the current account's propertyView windows, booking details windows and the account stage
    */
     protected void closeAllAccountWindows()
     {
         for (ListingPreviewController listingPreviewController : controllerComponents.getAccount().getListOfPropertyPreviewControllers()) {
             if (listingPreviewController.getPropertyViewStage() != null && listingPreviewController.getPropertyViewStage().isShowing()) {
                 listingPreviewController.getPropertyViewStage().close();
             }
         }
         for(Stage stage : controllerComponents.getAccount().getListOfBookingDetailsStages()){
             stage.close();
         }
         accountStage.close();
     }

     /**
      * This method returns whether the password entered in the "create account" window is valid or not, i.e., if it is strong enough
      * and is equal to the confirmation password
      * If it is empty, prints an error (in the corresponding error label)
      * @param password The password entered
      * @param confirmPassword the confirmation password entered
      * @return true if the password entered is strong and is equal to the confirmation password, false otherwise
      */
     public static boolean checkPassword(String password, String confirmPassword, Label label) {
         label.setText("");
         if (password.length() == 0) {
             label.setText("Please enter a password");
             return false;
         } else {
             return checkPasswordStrength(password, label) && checkPasswordEquality(password, confirmPassword, label);
         }
     }
     
     /**
      * This method returns whether the password entered in the "create account" window is valid or not, i.e., if it follows the right pattern to be strong enough
      * If it is not valid, it throws an alert and prints an error (in the corresponding error label)
      * @param password The password entered
      * @return true if the password entered is deemed strong and false otherwise
      */
     private static boolean checkPasswordStrength(String password, Label label) {
         label.setText("");
         String regex = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!?.><:;])(?=\\S+$).{8,}";
         Pattern pattern = Pattern.compile(regex);
         Matcher matcher = pattern.matcher(password);
         if (!matcher.matches()) {
             label.setText("This password is too weak.");
             return false;
         } else {
             return true;
         }
     }

     /**
      * This method returns whether the password and confirmation password are equal or not. Prints an error (in the corresponding error label) if not.
      * If it is not valid, prints an error in the corresponding error label
      * @param password The password entered
      * @param confirmPassword The confirmation password entered
      * @return true if the password and confirmation password match, false otherwise
      */
     private static boolean checkPasswordEquality(String password, String confirmPassword, Label label) {
         label.setText("");
         if (!password.equals(confirmPassword)) {
             label.setText("Passwords do not match");
             return false;
         } else {
             return true;
         }
     }

     /**
      * This method changes and updates the account's username
      */
     protected void changeUsername(String username)
     {
         controllerComponents.getAccount().setUsername(username);
         // updates all the username labels
         updateProfileLabels();

     }

     /**
      * This method sets all the username labels used throughout the app to the one specified in the current account loaded
      */
     public void updateProfileLabels()
     {
         String username = controllerComponents.getAccount().getUsername();
         ArrayList <Label> userNameLabels = AccountComponents.getInstance().getUsernameLabels();
         for (Label label: userNameLabels)
         {
             label.setText(username);
         }
     }

     /**
      * This method updates all the profile pictures throughout the app to the one specified in the current account loaded
      */
     public void updateProfilePictures()
     {
         // Use the unique accountComponents instance passed everywhere
         ArrayList<Circle> accountCircles = AccountComponents.getInstance().getAccountCircles();

         for (Circle circle : accountCircles)
         {
             circle.setFill(new ImagePattern(controllerComponents.getAccount().getProfilePicture()));
         }
     }
     

    /**
     * This method returns the current account stage
    * @return accountStage
    */
     public Stage getAccountStage(){
         return accountStage;
     }

     /**
      * this method returns the app's list of accounts
      * @return The ListOfAccounts
      */
     public ArrayList<Account> getListOfAccounts() {
         return listOfAccounts;
     }

     /**
      * This method returns the map mapping the email of each account to the corresponding account
      * @return the EmailToAccountMap
      */
     public HashMap<String, Account> getEmailToAccountMap() {
         return emailToAccountMap;
     }

    /**
     *This method returns the current account panel
    * @return the accountPanel
    */
     public BorderPane getAccountPanel() {
         return accountPanel;
     }

     /**
      * This method sets the current account stage to the one specified
      * @param accountStage The specified stage
      */
     public void setAccountStage(Stage accountStage){
         this.accountStage = accountStage;
     }

    
}
