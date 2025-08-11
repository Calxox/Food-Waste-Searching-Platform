package app;

/**
 * Class represeting a year from the Studio Project database
 *
 * @author Halil Ali, 2024. email: halil.ali@rmit.edu.au
 */

public class Activity {
   // year
   private String activity;

   /**
    * Create a Country and set the fields
    */
   public Activity(String activity) {
      this.activity = activity;
   }

   public String activity(){
      return activity;
   }

}
