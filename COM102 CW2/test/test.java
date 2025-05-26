import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class test {

    private pets genericPet;
    private dogs dogPet;
    private cats catPet;

    @Before
    public void setUp() {
        genericPet = new pets("Buddy", 2, "Brown", 15, 101);
        dogPet = new dogs("Rex", 5, "Black", 30, "Labrador", 1);
        catPet = new cats("Whiskers", 3, "White", 10, "Persian", 2);
    }

    @Test
    public void testPetsConstructorAndGetters() {
        assertEquals("Buddy", genericPet.getName());
        assertEquals(2, genericPet.getAge());
        assertEquals("Brown", genericPet.getColor());
        assertEquals(15, genericPet.getWeight());
        assertEquals(101, genericPet.getId());
    }

    @Test
    public void testPetsSetters() {
        genericPet.setName("Charlie");
        genericPet.setAge(4);
        genericPet.setColor("Black");
        genericPet.setWeight(20);
        genericPet.setId(105);

        assertEquals("Charlie", genericPet.getName());
        assertEquals(4, genericPet.getAge());
        assertEquals("Black", genericPet.getColor());
        assertEquals(20, genericPet.getWeight());
        assertEquals(105, genericPet.getId());
    }

    @Test
    public void testPetsSpeak() {
        assertEquals("i am pet", genericPet.Speak());
    }

    @Test
    public void testDogSpeak() {
        assertEquals("Woof! I am Rex my id is 1, i am  5 years old and my breed is Labrador", dogPet.Speak());
    }

    @Test
    public void testCatSpeak() {
        assertEquals("Miaow! My name is Whiskers,my id is 2,i am 3 years old and my breed is Persian", catPet.Speak());
    }

    @Test
    public void testDogBreedGetterSetter() {
        assertEquals("Labrador", dogPet.getBreed());
        dogPet.setBreed("Beagle");
        assertEquals("Beagle", dogPet.getBreed());
    }

    @Test
    public void testCatBreedGetterSetter() {
        assertEquals("Persian", catPet.getBreed());
        catPet.setBreed("Siamese");
        assertEquals("Siamese", catPet.getBreed());
    }

    @Test
    public void testInstanceTypes() {
        assertNotNull(genericPet);
        assertNotNull(dogPet);
        assertNotNull(catPet);
        assertTrue(true);
        assertTrue(true);
    }


    @Test
    public void testPrintLikeCode1() {
        System.out.println("Printing the values");
        System.out.println(dogPet.Speak());
        System.out.println(catPet.Speak());
        System.out.println(genericPet.Speak());

        System.out.println("The total number of cats are 1");
        System.out.println("The total number of dogs are 1");

        System.out.println("Dominant dog color = " + dogPet.getColor());
        System.out.println("Dominant cat color = " + catPet.getColor());

        System.out.println(dogPet.getName());
        System.out.println(catPet.getName());
    }
}
