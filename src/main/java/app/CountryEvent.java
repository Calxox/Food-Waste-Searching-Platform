package app;

/**
 * Class represeting a year from the Studio Project database
 *
 * @author Halil Ali, 2024. email: halil.ali@rmit.edu.au
 */

public class CountryEvent {
   private double startYearLoss;
   private double endYearLoss;
   private double changeOfLoss;
   private String activity;
   private String foodSupply;
   private String causeOfLost;
   

   /**
    * Create a Country and set the fields
    */
      public CountryEvent() {
      startYearLoss = 0;
      endYearLoss = 0;
      changeOfLoss  = 0;
      activity = "none";
      foodSupply = "none";
      causeOfLost = "none";
   }

   public CountryEvent(double startYearLoss, double endYearLoss, double changeOfLoss, String activity, String foodSupply, String causeOfLost) {
      this.startYearLoss = startYearLoss;
      this.endYearLoss = endYearLoss;
      this.changeOfLoss = changeOfLoss;
      this.activity = activity;
      this.foodSupply = foodSupply;
      this.causeOfLost = causeOfLost;
   }

   public double getStartYearLoss(){
      return this.startYearLoss;
   }

   public double getEndYearLoss(){
      return this.endYearLoss;
   }

   public double getChangeOfLoss(){
      return this.changeOfLoss;
   }

   public String getActivity(){
      return this.activity;
   }

   public String getFoodSupply(){
      return this.foodSupply;
   }

   public String getCauseOfLost(){
      return this.causeOfLost;
   }

}
