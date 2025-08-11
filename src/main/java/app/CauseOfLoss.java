package app;

/**
 * Class represeting a year from the Studio Project database
 *
 * @author Halil Ali, 2024. email: halil.ali@rmit.edu.au
 */

public class CauseOfLoss {
   // year
   private String causeOfLoss;

   /**
    * Create a Country and set the fields
    */
   public CauseOfLoss(String causeOfLoss) {
      this.causeOfLoss = causeOfLoss;
   }

   public String activity(){
      return causeOfLoss;
   }

}
