package app;

/**
 * Class represeting a year from the Studio Project database
 *
 * @author Halil Ali, 2024. email: halil.ali@rmit.edu.au
 */

public class LossPercent {
   // year
   private double lossPercent;

   /**
    * Create a Country and set the fields
    */
   public LossPercent(double lossPercent) {
      this.lossPercent = lossPercent;
   }

   public double lossPercent(){
      return lossPercent;
   }

}
