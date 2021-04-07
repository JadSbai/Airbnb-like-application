package sample;

import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class AccountCircles

{

   private static AccountCircles instance;
   private ArrayList<Circle> accountCircles;

   private AccountCircles()
   {
       accountCircles = new ArrayList<>();
       instance = this;
   }

   public static AccountCircles getInstance()
   {
       if (instance == null)
       {
           instance = new AccountCircles();
       }
       return instance;
   }

   public ArrayList<Circle> getAccountCircles()
   {
       return accountCircles;
   }

}





