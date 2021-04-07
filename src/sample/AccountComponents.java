package sample;

import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class AccountComponents

{

   private static AccountComponents instance;
   private ArrayList<Circle> accountCircles;
   private ArrayList<Label> userNameLabels;

   private AccountComponents()
   {
       accountCircles = new ArrayList<>();
       userNameLabels = new ArrayList<>();
       instance = this;
   }

   public static AccountComponents getInstance()
   {
       if (instance == null)
       {
           instance = new AccountComponents();
       }
       return instance;
   }

   public ArrayList<Circle> getAccountCircles()
   {
       return accountCircles;
   }

    public ArrayList<Label> getUsernameLabels()
    {
           return userNameLabels;
    }
}





