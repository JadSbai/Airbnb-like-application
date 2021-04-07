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
         ArrayList<Circle> accountCircles = AccountComponents.getInstance().getAccountCircles();

         for (Circle circle : accountCircles)
         {
             circle.setFill(new ImagePattern(controllerComponents.getAccount().getProfilePicture()));
         }

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

    protected void changeUsername(String username)
    {
        controllerComponents.getAccount().setUsername(username);
        updateProfileLabels();

    }

    public void updateProfileLabels()
    {
        String username = controllerComponents.getAccount().getUsername();
        ArrayList <Label> userNameLabels = AccountComponents.getInstance().getUsernameLabels();
        for (Label label: userNameLabels)
        {
         label.setText(username);
        }
    }
}
