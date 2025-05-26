// Base class representing a generic pet
public class pets {
    // Private attributes common to all pets
    private String name;
    private int age;
    private String color;
    private int weight;
    private int id;

    // Constructor to initialize a pet object with its properties
    public pets(String name, int age, String color, int weight, int id) {
        this.name = name;
        this.age = age;
        this.color = color;
        this.weight = weight;
        this.id = id;
    }

    // Setter method to change pet's name
    public void setName(String name) {
        this.name = name;
    }

    // Setter method to change pet's color
    public void setColor(String color) {
        this.color = color;
    }

    // Setter method to change pet's weight
    public void setWeight(int weight) {
        this.weight = weight;
    }

    // Getter method to retrieve pet's ID
    public int getId() {
        return id;
    }

    // Setter method to update pet's ID
    public void setId(int id) {
        this.id = id;
    }

    // Getter method to retrieve pet's name
    public String getName() {
        return name;
    }

    // Setter method to update pet's age
    public void setAge(int age) {
        this.age = age;
    }

    // Getter method to retrieve pet's age
    public int getAge() {
        return age;
    }

    // Getter method to retrieve pet's color
    public String getColor() {
        return color;
    }

    // Getter method to retrieve pet's weight
    public int getWeight() {
        return weight;
    }

    // Generic method for pet sound or message (can be overridden by subclasses)
    public String Speak() {
        return "i am pet";
    }
}
