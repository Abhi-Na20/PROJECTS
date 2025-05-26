public class Customer {
    String firstName;
    String lastName;
    String phoneNumber;
    String email;
    int mealCount;

    public Customer(String firstName, String lastName, String phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.mealCount = 0;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmail() { return email; }
    public int getMealCount() { return mealCount; }
    public void setMealCount(int mealCount) { this.mealCount = mealCount; }
    public void incrementMealCount() { this.mealCount++; }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}