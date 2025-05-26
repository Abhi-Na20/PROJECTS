import java.io.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

// Main class for managing a pet clinic
public class clinic {
    // Static variable to keep track of number of pets
    static private int petscount = 0;

    // Scanner object for user input
    static Scanner sc = new Scanner(System.in);

    // Method to load pet data from a file
    public static void loading(pets[] pet) {
        int count = petscount;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/PetDetails.txt"));
            String line;

            // Read each line from the file
            while ((line = reader.readLine()) != null) {
                if (count < pet.length) {
                    // Split line by comma to extract attributes
                    String[] parts = line.split(",");
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    int age = Integer.parseInt(parts[2]);
                    String color = parts[3];
                    int weight = Integer.parseInt(parts[4]);
                    String breed = parts[5];
                    String type = parts[6];

                    // Create appropriate object based on type
                    if (type.equals("Dog")) {
                        dogs obj = new dogs(name, age, color, weight, breed, id);
                        pet[count] = obj;
                    } else if (type.equals("Cat")) {
                        cats obj = new cats(name, age, color, weight, breed, id);
                        pet[count] = obj;
                    }

                    petscount = count + 1;
                    count++;
                }
            }
        } catch (IOException e) {
            System.out.println("There is an error");
        }
    }

    // Method to add a new pet
    public static void add(pets[] pet) {
        System.out.println("Enter the type of pet");
        System.out.println("1. Dog");
        System.out.println("2. Cat");
        String type = sc.next();

        // Collect pet details based on type
        if (type.equals("1")) {
            System.out.println("Enter the name :");
            String name = sc.next();
            System.out.println("Enter the age :");
            int age = sc.nextInt();
            System.out.println("Enter the color :");
            String color = sc.next();
            System.out.println("Enter the weight :");
            int weight = sc.nextInt();
            System.out.println("Enter the breed :");
            String breed = sc.next();
            dogs obj = new dogs(name, age, color, weight, breed, petscount + 1);
            pet[petscount] = obj;
            petscount++;
        } else if (type.equals("2")) {
            System.out.println("Enter the name :");
            String name = sc.next();
            System.out.println("Enter the age :");
            int age = sc.nextInt();
            System.out.println("Enter the color :");
            String color = sc.next();
            System.out.println("Enter the weight :");
            int weight = sc.nextInt();
            System.out.println("Enter the breed :");
            String breed = sc.next();
            cats obj = new cats(name, age, color, weight, breed, petscount + 1);
            pet[petscount] = obj;
            petscount++;
        } else {
            System.out.println("Error!! Wrong pet type");
        }

        // Confirm addition
        if (petscount > 0 && pet[petscount - 1] != null) {
            System.out.println("Pet details added successfully.");
        }
    }

    // Method to delete a pet by ID
    public static void delete(pets[] pet) {
        if (petscount == 0) {
            System.out.println("There are no pets to delete");
            return;
        }

        // Display all pets
        System.out.println("Printing the values");
        for (int i = 0; i < petscount; i++) {
            System.out.println(pet[i].Speak());
        }

        // Get pet ID to delete
        System.out.println("Which pet do you want to delete:");
        int id2 = sc.nextInt();
        boolean found = false;

        // Find and remove the pet, shift remaining pets left
        for (int i = 0; i < petscount; i++) {
            int id1 = pet[i].getId();
            if (id1 == id2) {
                for (int j = i; j < petscount - 1; j++) {
                    pet[j] = pet[j + 1];
                    int id = pet[j].getId();
                    pet[j].setId(id - 1); // Reassign IDs
                }
                pet[petscount - 1] = null;
                petscount--;
                found = true;
                break;
            }
        }

        // Notify result
        if (found) {
            System.out.println("Pet deleted successfully.");
        } else {
            System.out.println("No pet found with ID = " + id2);
        }
    }

    // Method to modify pet details
    public static void modify(pets[] pet) {
        if (petscount == 0) {
            System.out.println("There are no pets");
            return;
        }

        print(pet);
        System.out.println("Which pet do you want to modify:");
        int id3 = sc.nextInt();
        boolean found = false;

        for (int i = 0; i < petscount; i++) {
            if (pet[i].getId() == id3) {
                found = true;

                // Ask what attribute to modify
                System.out.println("What do you want to modify:");
                System.out.println("1. Name");
                System.out.println("2. Age");
                System.out.println("3. Color");
                System.out.println("4. Weight");
                System.out.println("5. Breed");
                int choice = sc.nextInt();

                // Perform modification based on user choice
                switch (choice) {
                    case 1:
                        System.out.println("Enter the new name:");
                        String name = sc.next();
                        pet[i].setName(name);
                        System.out.println("Pet name modified successfully.");
                        break;
                    case 2:
                        System.out.println("Enter the new age:");
                        int age = sc.nextInt();
                        pet[i].setAge(age);
                        System.out.println("Pet age modified successfully.");
                        break;
                    case 3:
                        System.out.println("Enter the new color:");
                        String color = sc.next();
                        pet[i].setColor(color);
                        System.out.println("Pet color modified successfully.");
                        break;
                    case 4:
                        System.out.println("Enter the new weight:");
                        int weight = sc.nextInt();
                        pet[i].setWeight(weight);
                        System.out.println("Pet weight modified successfully.");
                        break;
                    case 5:
                        System.out.println("Enter the new breed:");
                        String breed = sc.next();
                        if (pet[i] instanceof dogs) {
                            ((dogs) pet[i]).setBreed(breed);
                        } else if (pet[i] instanceof cats) {
                            ((cats) pet[i]).setBreed(breed);
                        }
                        System.out.println("Pet breed modified successfully.");
                        break;
                    default:
                        System.out.println("Invalid choice");
                        break;
                }
                break;
            }
        }

        if (!found) {
            System.out.println("No pet with ID = " + id3);
        }
    }

    // Method to print details of all pets
    public static void print(pets[] pet) {
        System.out.println("Printing the values");
        for (int i = 0; i < petscount; i++) {
            System.out.println(pet[i].Speak());
        }
    }

    // Method to generate and save report
    public static void report(pets[] pet) {
        int catcount = 0, dogcount = 0;
        Map<String, Integer> dogcolor = new HashMap<>();
        Map<String, Integer> catcolor = new HashMap<>();
        String d = "", c = "";

        // Count number of dogs and cats and their colors
        for (int i = 0; i < petscount; i++) {
            String color = pet[i].getColor();
            if (pet[i] instanceof dogs) {
                dogcount++;
                dogcolor.put(color, dogcolor.getOrDefault(color, 0) + 1);
            } else if (pet[i] instanceof cats) {
                catcount++;
                catcolor.put(color, catcolor.getOrDefault(color, 0) + 1);
            }
        }

        // Find most common dog color
        int dogcountcolor = 0;
        for (Map.Entry<String, Integer> dogs : dogcolor.entrySet()) {
            if (dogs.getValue() > dogcountcolor) {
                dogcountcolor = dogs.getValue();
                d = dogs.getKey();
            }
        }

        // Find most common cat color
        int catcountcolor = 0;
        for (Map.Entry<String, Integer> cats : catcolor.entrySet()) {
            if (cats.getValue() > catcountcolor) {
                catcountcolor = cats.getValue();
                c = cats.getKey();
            }
        }

        // Display report
        System.out.println("The total number of cats are " + catcount);
        System.out.println("The total number of dogs are " + dogcount);
        System.out.println("Dominant dog color = " + d);
        System.out.println("Dominant cat color = " + c);

        // Write report to file
        try {
            PrintWriter writer1 = new PrintWriter(new FileWriter("src/ClinicsDetails.txt"), false);
            writer1.println("PetsClinic,dogs = " + dogcount + ",cats = " + catcount);
            writer1.println("dominantDogColor = " + d + ",dominantCatColor = " + c);
            writer1.close();
        } catch (IOException e) {
            System.out.println("Could not write into ClinicsDetails.txt");
        }
    }

    // Method to view all pet names
    public static void viewall(pets[] pet) {
        for (int i = 0; i < petscount; i++) {
            System.out.println(pet[i].getName());
        }
    }

    // Method to search pet by name and color
    public static void search(pets[] pet) {
        System.out.println("Enter the name of the pet:");
        String name = sc.next();
        System.out.println("Enter the color of the pet:");
        String color = sc.next();
        boolean found = false;

        // Find matching pet
        for (int i = 0; i < petscount; i++) {
            if (pet[i].getName().equals(name) && pet[i].getColor().equals(color)) {
                System.out.println(pet[i].Speak());
                found = true;
            }
        }

        if (!found) {
            System.out.println("================================");
            System.out.println("There is no combination of : name = " + name + ", color = " + color);
            System.out.println("================================");
        }
    }

    // Main method - Entry point
    public static void main(String[] args) {
        petscount = 0;
        pets[] pet = new pets[100]; // Array to store pets
        loading(pet); // Load pets from file
        System.out.println("Welcome to the pet clinic");

        // Menu loop
        while (true) {
            System.out.println("1. Add the pet");
            System.out.println("2. Search");
            System.out.println("3. Modify the pet");
            System.out.println("4. Print the pet");
            System.out.println("5. Report");
            System.out.println("6. View all the data");
            System.out.println("7. Delete the pet");
            System.out.println("8. Exit");
            System.out.print("Enter the choice: ");
            int choice = sc.nextInt();

            // Handle user choice
            switch (choice) {
                case 1:
                    add(pet);
                    break;
                case 2:
                    search(pet);
                    break;
                case 3:
                    modify(pet);
                    break;
                case 4:
                    print(pet);
                    break;
                case 5:
                    report(pet);
                    break;
                case 6:
                    viewall(pet);
                    break;
                case 7:
                    delete(pet);
                    break;
                case 8:
                    // Save pet data before exiting
                    try {
                        PrintWriter writer = new PrintWriter(new FileWriter("src/PetDetails.txt"), false);
                        for (int i = 0; i < petscount; i++) {
                            pets p = pet[i];
                            if (p != null) {
                                if (p instanceof dogs) {
                                    writer.println(p.getId() + "," + p.getName() + "," + p.getAge() + "," + p.getColor() + "," + p.getWeight() + "," + ((dogs) p).getBreed() + ",Dog");
                                } else if (p instanceof cats) {
                                    writer.println(p.getId() + "," + p.getName() + "," + p.getAge() + "," + p.getColor() + "," + p.getWeight() + "," + ((cats) p).getBreed() + ",Cat");
                                }
                            }
                        }
                        writer.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    System.exit(0); // Exit program
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }
}
