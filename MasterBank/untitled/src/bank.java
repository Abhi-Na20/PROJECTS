import java.io.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.List;
import java.util.ArrayList;


public class bank {
    private static Map<String, Account> accounts = new HashMap<>();
    private static final String ACCOUNTS_FILE = "Accounts.txt";
    private static final String TRANSACTIONS_FILE_SUFFIX = "_transactions.txt";

    private static List<String> availableAccountNumbers = new ArrayList<>();
    private static final long MIN_ACCOUNT_NUMBER = 100000L;
    private static final long MAX_ACCOUNT_NUMBER = 100100L; // Allows for 101 unique accounts

    static Scanner sc = new Scanner(System.in);

    private static void initializeAvailableAccountNumbers() {
        for (long i = MIN_ACCOUNT_NUMBER; i <= MAX_ACCOUNT_NUMBER; i++) {
            availableAccountNumbers.add(String.valueOf(i));
        }
    }

    private static int readInt(String prompt) {
        int value;
        while (true) {
            System.out.print(prompt);
            try {
                value = sc.nextInt();
                sc.nextLine(); // Consume newline
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Enter a whole number.");
                sc.nextLine(); // Consume invalid input
            }
        }
    }

    private static double readDouble(String prompt) {
        double value;
        while (true) {
            System.out.print(prompt);
            try {
                value = sc.nextDouble();
                sc.nextLine(); // Consume newline
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Enter a number (e.g., 123.45).");
                sc.nextLine(); // Consume invalid input
            }
        }
    }


    public static void loadAccounts() {
        File file = new File(ACCOUNTS_FILE);
        if (!file.exists()) {
            System.out.println(ACCOUNTS_FILE + " not found. Starting with no existing accounts.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String[] parts = line.split(",");
                    if (parts.length >= 4) {
                        String accNumStr = parts[0].trim();
                        String name = parts[1].trim();
                        String pin = parts[2].trim();
                        double balance = Double.parseDouble(parts[3].trim());

                        long accNumLong = Long.parseLong(accNumStr);
                        if (accNumLong >= MIN_ACCOUNT_NUMBER && accNumLong <= MAX_ACCOUNT_NUMBER) {
                            Account acc = new Account(accNumStr, name, pin, balance);
                            accounts.put(accNumStr, acc);
                            availableAccountNumbers.remove(accNumStr);
                            loadTransactionHistory(acc);
                        } else {
                            System.err.println("Skipped loading account (out of range): " + accNumStr);
                        }
                    } else {
                        System.err.println("Skipped loading account (malformed line): " + line);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Skipped loading account (invalid number format): " + line);
                } catch (Exception e) {
                    System.err.println("Error processing line '" + line + "' from " + ACCOUNTS_FILE + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading accounts from " + ACCOUNTS_FILE + ": " + e.getMessage());
        }
    }

    public static void saveAccounts() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ACCOUNTS_FILE, false))) {
            for (Account acc : accounts.values()) {
                writer.println(acc.toFileString());
                saveTransactionHistory(acc);
            }
        } catch (IOException e) {
            System.err.println("Error saving accounts to " + ACCOUNTS_FILE + ": " + e.getMessage());
        }
    }

    private static void loadTransactionHistory(Account acc) {
        File historyFile = new File(acc.getAccountNumber() + TRANSACTIONS_FILE_SUFFIX);
        if (!historyFile.exists()) return;

        List<String> loadedTransactions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(historyFile))) {
            String transactionLine;
            while ((transactionLine = reader.readLine()) != null) {
                loadedTransactions.add(transactionLine);
            }
            acc.setTransactionHistoryForLoad(loadedTransactions);
        } catch (IOException e) {
            System.err.println("Error loading transaction history for " + acc.getAccountNumber() + ": " + e.getMessage());
        }
    }

    private static void saveTransactionHistory(Account acc) {
        File historyFile = new File(acc.getAccountNumber() + TRANSACTIONS_FILE_SUFFIX);
        try (PrintWriter writer = new PrintWriter(new FileWriter(historyFile, false))) {
            for (String transaction : acc.getTransactionHistory()) {
                writer.println(transaction);
            }
        } catch (IOException e) {
            System.err.println("Error saving transaction history for " + acc.getAccountNumber() + ": " + e.getMessage());
        }
    }

    public static void createAccount() {
        if (availableAccountNumbers.isEmpty()) {
            System.out.println("Sorry, no more account numbers available in the predefined range.");
            return;
        }

        System.out.print("Enter First Name: ");
        String firstName = sc.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = sc.nextLine();
        String fullName = firstName + " " + lastName;

        String pin;
        while (true) {
            System.out.print("Enter a 4-digit PIN: ");
            pin = sc.nextLine();
            if (pin.matches("\\d{4}")) break;
            System.out.println("Invalid PIN format. Must be 4 digits.");
        }

        double initialDeposit = readDouble("Enter initial deposit ($0.00 or more): $");
        if (initialDeposit < 0) {
            System.out.println("Initial deposit cannot be negative. Account creation cancelled.");
            return;
        }

        String accountNumber = availableAccountNumbers.remove(0); // Get the next available

        Account newAccount = new Account(accountNumber, fullName, pin, initialDeposit);
        accounts.put(accountNumber, newAccount);

        System.out.println("\nAccount created successfully!");
        System.out.println("Account Holder: " + fullName);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Keep your account number and PIN safe.");
    }

    public static Account authenticateUser() {
        System.out.print("Enter Account Number: ");
        String accNum = sc.nextLine();
        System.out.print("Enter PIN: ");
        String pinInput = sc.nextLine();

        Account account = accounts.get(accNum);

        if (account != null && account.validatePIN(pinInput)) {
            System.out.println("\nLogin successful. Welcome, " + account.getAccountHolderName() + "!");
            return account;
        }
        System.out.println("Login failed. Invalid account number or PIN.");
        return null;
    }

    public static void deposit(Account currentAccount) {
        double amount = readDouble("Enter amount to deposit: $");
        if (amount < 0) { // Basic check before calling account method
            System.out.println("Deposit amount cannot be negative.");
            return;
        }
        currentAccount.deposit(amount); // Account method handles logic and transaction logging
        // Provide feedback as simplified Account class does not print
        System.out.println("Successfully deposited $" + String.format("%.2f", amount) + ". New balance: $" + String.format("%.2f", currentAccount.getBalance()));
    }

    public static void withdraw(Account currentAccount) {
        double amount = readDouble("Enter amount to withdraw: $");
        if (amount < 0) { // Basic check
            System.out.println("Withdrawal amount cannot be negative.");
            return;
        }
        boolean success = currentAccount.withdraw(amount); // Account method handles logic and logging
        if (success) {
            System.out.println("Successfully withdrew $" + String.format("%.2f", amount) + ". New balance: $" + String.format("%.2f", currentAccount.getBalance()));
        } else {
            // Account.withdraw logs "Failed withdrawal..." if insufficient funds
            System.out.println("Withdrawal failed. Amount may be invalid or exceed balance.");
        }
    }

    public static void checkBalance(Account currentAccount) {
        System.out.printf("Your current balance is: $%.2f%n", currentAccount.getBalance());
    }

    public static void checkTransactionHistory(Account currentAccount) {
        System.out.println("\n--- Transaction History for Account " + currentAccount.getAccountNumber() + " ---");
        List<String> history = currentAccount.getTransactionHistory();
        if (history.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (String transaction : history) {
                System.out.println("- " + transaction);
            }
        }
        System.out.println("------------------------------------------");
    }

    public static void transferFunds(Account currentAccount) {
        System.out.print("Enter recipient's account number: ");
        String recipientAccNum = sc.nextLine();

        if (recipientAccNum.equals(currentAccount.getAccountNumber())) {
            System.out.println("Cannot transfer to your own account.");
            return;
        }

        Account recipientAccount = accounts.get(recipientAccNum);
        if (recipientAccount == null) {
            System.out.println("Recipient account not found.");
            return;
        }

        double amount = readDouble("Enter amount to transfer: $");
        if (amount <= 0) {
            System.out.println("Transfer amount must be positive.");
            return;
        }

        if (currentAccount.withdraw(amount)) { // This logs the withdrawal in currentAccount
            recipientAccount.deposit(amount);  // This logs the deposit in recipientAccount
            System.out.printf("Transfer of $%.2f to account %s successful.%n", amount, recipientAccNum);
        } else {
            System.out.println("Transfer failed (e.g., insufficient funds).");
        }
    }

    public static void main(String[] args) {
        initializeAvailableAccountNumbers();
        loadAccounts();
        System.out.println("\nWelcome to the Simple ATM!");

        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Create New Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1: createAccount(); saveAccounts(); break; // Save after creating account
                case 2:
                    Account currentUser = authenticateUser();
                    if (currentUser != null) loggedInMenu(currentUser);
                    break;
                case 3:
                    saveAccounts();
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    sc.close();
                    return;
                default: System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void loggedInMenu(Account currentUser) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n--- Account Menu (Account: " + currentUser.getAccountNumber() + ") ---");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. Transfer Funds");
            System.out.println("5. View Transaction History");
            System.out.println("6. Logout");
            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1: deposit(currentUser); break;
                case 2: withdraw(currentUser); break;
                case 3: checkBalance(currentUser); break;
                case 4: transferFunds(currentUser); break;
                case 5: checkTransactionHistory(currentUser); break;
                case 6:
                    System.out.println("Logged out successfully.");
                    loggedIn = false; // Exit loop
                    break;
                default: System.out.println("Invalid choice. Try again.");
            }
            if (loggedIn) saveAccounts(); // Save after each successful operation within logged in menu
        }
    }
}