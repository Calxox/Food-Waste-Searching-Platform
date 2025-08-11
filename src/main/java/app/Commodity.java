package app;

public class Commodity {
   private String groupCode;
    private String cpc_code;
    private String name;

    public Commodity(String groupCode, String cpc_code, String name) {
         this.groupCode = groupCode;
        this.cpc_code = cpc_code;
        this.name = name;
     }
  
     public String getCPC_Code() {
        return cpc_code;
     }
  
     public String getName() {
        return name;
     }


     @Override
   public String toString() {
       return name;
   }
}
