package sample;

import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

/**
 * Follows a singleton design pattern. Only one instance is
 * created, which gives global access to the Circles and Labels where the
 * current account's profile picture and username are set so that they can
 * easily be modified when the account is changed.
 * @author Jacqueline Ilie, Liam Clark Gutiérrez, Dexter Trower and Jad Sbaï
 * @version 07/04/2021
 */
public class AccountComponents

{

   /**
   *    The static instance of accountComponents that all classes can access.
   */
   private static AccountComponents instance;

   /**
   *    The list of account circles that will be modified.
   */
   private ArrayList<Circle> accountCircles;
    /**
     *  The list of username labels that will be modified.
     */
   private ArrayList<Label> userNameLabels;

    /**
     *  The constructor initializes the lists.
     *  Constructor is private so it can only be initialized by this class.
     */
   private AccountComponents()
   {
       accountCircles = new ArrayList<>();
       userNameLabels = new ArrayList<>();
   }

    /**
     * Augmented accessor method to make sure only one instance of this class is created.
     * If there is an existing instance of this class, return it, otherwise create
     * a new instance and return it.
     * @return The one instance of this class that can be accessed by other classes.
     */
   public static AccountComponents getInstance()
   {
       if (instance == null)
       {
           instance = new AccountComponents();
       }
       return instance;
   }

    /**
     * Returns the list of account circles that have been instantiated via FXML loaders.
     * @return the list of account circles.
     */
   public ArrayList<Circle> getAccountCircles()
   {
       return accountCircles;
   }

    /**
     * Returns the list of username labels that have been instantiated via FXML loaders,
     * so they can be modified when a new user signs in.
     * @return the list of username labels.
     */
    public ArrayList<Label> getUsernameLabels()
    {
           return userNameLabels;
    }
}





