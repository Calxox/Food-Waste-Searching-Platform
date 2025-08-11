package app;

/**
 * Class represeting a year from the Studio Project database
 *
 * @author Halil Ali, 2024. email: halil.ali@rmit.edu.au
 */

 public class Date {
   private int year;

   public Date(int year) {
       this.year = year;
   }

   public int getYear() {
       return year;
   }

   @Override
   public String toString() {
       return String.valueOf(year);
   }
   
}



