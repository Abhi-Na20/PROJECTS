// Class representing dogs, which is a specific type of pet
public class dogs extends pets {
    // Additional attribute specific to dogs
    private String breed;

    // Constructor to initialize dog properties along with base pet properties
    public dogs(String name, int age, String color, int weight, String breed, int id) {
        // Call the constructor of the base 'pets' class
        super(name, age, color, weight, id);
        this.breed = breed;
    }

    // Override the Speak method to return a dog-specific message
    @Override
    public String Speak() {
        return "Woof! I am " + getName() + " my id is " + getId() + ", I am " + getAge() + " years old and my breed is " + breed;
    }

    // Getter method to retrieve the dog's breed
    public String getBreed() {
        return breed;
    }

    // Setter method to update the dog's breed
    public void setBreed(String breed) {
        this.breed = breed;
    }
}
