import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.List;
import java.util.ArrayList;

public class JavaBites {

    // Data is stored in memory and lost when program exits.
    private static Map<String, Customer> customers = new HashMap<>();
    private static List<MenuItem> menu = new ArrayList<>();
    private static Map<String, Order> currentCustomerOrders = new HashMap<>();

    private static Scanner scanner = new Scanner(System.in);

    private static void initializeMenu() {
        menu.add(new MenuItem("M01", "Paneer Tikka", 12.99));
        menu.add(new MenuItem("M02", "Butter Chicken", 15.50));
        menu.add(new MenuItem("M03", "Dal Makhani", 10.00));
        menu.add(new MenuItem("D01", "Coke", 2.50));
        menu.add(new MenuItem("D02", "Water", 1.00));
    }

    public static void registerNewCustomer() {
        System.out.println("\n--- New Customer Registration ---");
        System.out.print("Enter your First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter your Last Name: ");
        String lastName = scanner.nextLine();

        String phoneNumber;
        while (true) {
            System.out.print("Enter your Phone Number (used for login): ");
            phoneNumber = scanner.nextLine();
            if (customers.containsKey(phoneNumber)) {
                System.out.println("This phone number is already registered. Please try logging in or use a different number.");
            } else if (phoneNumber.matches("\\d{10}")) {
                break;
            } else {
                System.out.println("Invalid phone number format. Please enter 10 digits.");
            }
        }

        System.out.print("Enter your Email: ");
        String email = scanner.nextLine();
        if (!email.contains("@") || !email.contains(".")) {
            System.out.println("Warning: Email format might be incorrect.");
        }

        Customer newCustomer = new Customer(firstName, lastName, phoneNumber, email);
        customers.put(phoneNumber, newCustomer);

        System.out.println("\nAccount created successfully!");
        System.out.println("Name: " + newCustomer.getFullName());
        System.out.println("PhoneNumber: " + newCustomer.getPhoneNumber());
        System.out.println("Welcome to JavaBites, " + firstName + "!");
    }

    public static Customer login() {
        System.out.println("\n--- Customer Login ---");
        System.out.print("Enter your Phone Number: ");
        String phoneNumber = scanner.nextLine();
        Customer customer = customers.get(phoneNumber);
        if (customer != null) {
            System.out.println("Login successful! Welcome back, " + customer.getFirstName() + ".");
            return customer;
        } else {
            System.out.println("Login failed. No account found. Please register.");
            return null;
        }
    }

    public static void displayFoodMenu() {
        System.out.println("\n--- JavaBites Menu ---");
        if (menu.isEmpty()) {
            System.out.println("Sorry, the menu is empty today!");
            return;
        }
        for (int i = 0; i < menu.size(); i++) {
            System.out.println((i + 1) + ". " + menu.get(i).getName() + " - $" + String.format("%.2f", menu.get(i).getPrice()));
        }
        System.out.println("----------------------");
    }

    public static void orderFood(Customer customer) {
        Order currentOrder = currentCustomerOrders.get(customer.getPhoneNumber());
        if (currentOrder == null || currentOrder.isPaid()) {
            currentOrder = new Order(customer.getPhoneNumber());
            currentCustomerOrders.put(customer.getPhoneNumber(), currentOrder);
        }

        displayFoodMenu();
        if (menu.isEmpty()) return;

        while (true) {
            System.out.print("Enter item number to order (or 0 to finish): ");
            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 0) {
                    break;
                }
                if (choice > 0 && choice <= menu.size()) {
                    MenuItem selectedItem = menu.get(choice - 1);
                    currentOrder.addItem(selectedItem);
                    System.out.println(selectedItem.getName() + " added.");
                } else {
                    System.out.println("Invalid item number.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Enter a number.");
                scanner.nextLine();
            }
        }
        System.out.println("Current order total: $" + String.format("%.2f", currentOrder.getTotalAmount()));
    }

    public static void viewOrder(Customer customer) {
        Order currentOrder = currentCustomerOrders.get(customer.getPhoneNumber());
        if (currentOrder == null || currentOrder.getItems().isEmpty()) {
            System.out.println("\nYour order is empty.");
        } else {
            System.out.println("\n--- Your Current Order ---");
            System.out.println(currentOrder);
        }
    }

    public static void provideFeedback(Customer customer) {
        Order currentOrder = currentCustomerOrders.get(customer.getPhoneNumber());
        if (currentOrder == null || currentOrder.getItems().isEmpty()) {
            System.out.println("\nNo active order for feedback.");
            return;
        }
        if (currentOrder.isPaid()) {
            System.out.println("\nOrder already paid. Feedback before payment.");
            return;
        }
        System.out.print("Enter your feedback: ");
        String feedbackText = scanner.nextLine();
        currentOrder.setFeedback(feedbackText);
        System.out.println("Feedback received. Thank you!");
    }

    public static void payBill(Customer customer) {
        Order currentOrder = currentCustomerOrders.get(customer.getPhoneNumber());
        if (currentOrder == null || currentOrder.getItems().isEmpty()) {
            System.out.println("\nNo order to pay.");
            return;
        }
        if (currentOrder.isPaid()) {
            System.out.println("\nOrder already paid.");
            return;
        }

        currentOrder.calculateTotalAmount();
        double billAmount = currentOrder.getTotalAmount();
        String promoInfo = "";

        if (customer.getMealCount() == 9 && billAmount > 0) {
            billAmount = 0.0;
            promoInfo += "10th Meal Free! ";
            System.out.println("Congrats! Your 10th meal is FREE!");
        }
        currentOrder.setFinalAmountDue(billAmount);

        System.out.print("Payment method (Cash/Card): ");
        String paymentMethod = scanner.nextLine().trim().toLowerCase();

        if (billAmount > 0 && paymentMethod.equals("card")) {
            double cardDiscount = billAmount * 0.10;
            billAmount -= cardDiscount;
            promoInfo += String.format("Card Discount (10%%): -$%.2f. ", cardDiscount);
            System.out.println("10% card discount applied.");
        }
        currentOrder.setFinalAmountDue(billAmount);
        currentOrder.setPromotionAppliedInfo(promoInfo);

        System.out.println("\n--- Final Bill ---");
        System.out.println(currentOrder);

        if (currentOrder.getFinalAmountDue() == 0 && currentOrder.getTotalAmount() > 0) {
            System.out.println("Your meal is free!");
        } else {
            System.out.printf("Please pay: $%.2f%n", currentOrder.getFinalAmountDue());
        }

        System.out.print("Confirm payment? (yes/no): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("yes")) {
            currentOrder.setPaid(true, paymentMethod);
            System.out.println("Payment successful via " + paymentMethod + "!");

            if (promoInfo.contains("10th Meal Free")) {
                customer.setMealCount(0);
            } else {
                customer.incrementMealCount();
            }
            System.out.println(customer.getFirstName() + ", meal count: " + customer.getMealCount());
        } else {
            System.out.println("Payment cancelled.");
        }
    }

    public static void customerActionsMenu(Customer customer) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n--- Welcome " + customer.getFirstName() + " (Meals: " + customer.getMealCount() +
                    (customer.getMealCount() == 9 ? " - Next is FREE!)" : ")") + " ---");
            System.out.println("1. Order Food");
            System.out.println("2. View Current Order");
            System.out.println("3. Provide Feedback");
            System.out.println("4. Pay Bill");
            System.out.println("5. Logout");
            System.out.print("Enter choice: ");
            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1: orderFood(customer); break;
                    case 2: viewOrder(customer); break;
                    case 3: provideFeedback(customer); break;
                    case 4: payBill(customer); break;
                    case 5: loggedIn = false; System.out.println("Logged out."); break;
                    default: System.out.println("Invalid choice.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Enter a number.");
                scanner.nextLine();
            }
        }
    }

    public static void main(String[] args) {
        initializeMenu();

        while (true) {
            System.out.println("\n------ Welcome to JavaBites ------");
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1: registerNewCustomer(); break;
                    case 2:
                        Customer c = login();
                        if (c != null) customerActionsMenu(c);
                        break;
                    case 3:
                        System.out.println("Exiting. Goodbye!");
                        scanner.close();
                        System.exit(0);
                        break;
                    default: System.out.println("Invalid choice.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Enter a number.");
                scanner.nextLine();
            }
        }
    }
}
