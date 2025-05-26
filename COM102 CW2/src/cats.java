// Class representing cats, which is a specific type of pet
public class cats extends pets {
    // Additional attribute specific to cats
    private String breed;

    // Constructor to initialize cat properties along with base pet properties
    public cats(String name, int age, String color, int weight, String breed, int id) {
        // Call the constructor of the base 'pets' class
        super(name, age, color, weight, id);
        this.breed = breed;
    }

    // Override the Speak method to return a cat-specific message
    @Override
    public String Speak() {
        return "Miaow! My name is " + getName() +
                ", my id is " + getId() +
                ", I am " + getAge() +
                " years old and my breed is " + breed;
    }

    // Getter method to retrieve the cat's breed
    public String getBreed() {
        return breed;
    }

    // Setter method to update the cat's breed
    public void setBreed(String breed) {
        this.breed = breed;
    }
}
