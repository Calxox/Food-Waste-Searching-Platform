package app;

public class Persona {
    private String ID;
    private String name;
    private String age;
    private String description;
    private String needsAndGoals;
    private String skillsAndExperiences;
    private String personaImg;

    public Persona(String ID, String name, String age, String description, String needsAndGoals, String skillsAndExperiences, String personaImg)
    {
        this.ID = ID;
        this.name = name;
        this.age = age;
        this.description=description;
        this.needsAndGoals = needsAndGoals;
        this.skillsAndExperiences = skillsAndExperiences;
        this.personaImg = personaImg;
    }
    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getDescription() {
        return description;
    }

    public String getNeedsAndGoals() {
        return needsAndGoals;
    }

    public String getSkillsAndExperiences() {
        return skillsAndExperiences;
    }
    public String getPersonaImg()
    {
        return personaImg;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", description='" + description + '\'' +
                ", needsAndGoals='" + needsAndGoals + '\'' +
                ", skillsAndExperiences='" + skillsAndExperiences + '\'' +
                '}';
    }


}
