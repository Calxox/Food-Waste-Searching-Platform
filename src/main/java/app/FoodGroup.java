package app;

public class FoodGroup {
    private String groupCode;
    private String name;

    public FoodGroup(String groupCode, String name)
    {
        this.groupCode = groupCode;
        this.name = name;
    }
    public String getGroupCode()
    {
        return groupCode;
    }
    public String getGroupName()
    {
        return name;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
