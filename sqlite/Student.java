/**
 * Model class for a student and their skills.
 *
 * Demonstrates OOP encapsulation: all fields are private and
 * accessed only through getters/setters.
 */
public class Student {
    private String name;
    private String contact;
    private String teachSkill;
    private String learnSkill;

    public Student(String name, String contact, String teachSkill, String learnSkill) {
        this.name = name;
        this.contact = contact;
        this.teachSkill = teachSkill;
        this.learnSkill = learnSkill;
    }

    public String getName()       { return name; }
    public String getContact()    { return contact; }
    public String getTeachSkill() { return teachSkill; }
    public String getLearnSkill() { return learnSkill; }

    public void setTeachSkill(String teachSkill) { this.teachSkill = teachSkill; }
    public void setLearnSkill(String learnSkill) { this.learnSkill = learnSkill; }

    @Override
    public String toString() {
        return name + " teaches " + teachSkill + ", wants to learn " + learnSkill;
    }

    public void display() {
        System.out.println("Name: " + name);
        System.out.println("Contact: " + contact);
        System.out.println("Can Teach: " + teachSkill);
        System.out.println("Wants to Learn: " + learnSkill);
        System.out.println("----------------------");
    }
}
