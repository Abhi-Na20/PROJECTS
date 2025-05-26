import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Account {
    private final String accountNumber;
    private final String accountHolderName;
    private String pin; // PIN: Store securely.
    private double balance;
    private final List<String> transactionHistory;

    public Account(String accountNumber, String accountHolderName, String pin, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.pin = pin;
        this.transactionHistory = new ArrayList<>();

        if (initialDeposit >= 0) {
            this.balance = initialDeposit;
            addTransaction("Initial deposit: $" + String.format("%.2f", initialDeposit));
        } else {
            this.balance = 0;
            // Necessary to record the outcome even if deposit was invalid
            addTransaction("Account created. Invalid initial deposit ($" + String.format("%.2f", initialDeposit) + "). Balance set to $0.00.");
        }
    }

    private void addTransaction(String transactionDetails) {
        this.transactionHistory.add(transactionDetails);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    public List<String> getTransactionHistory() {
        // Return an unmodifiable view to protect the internal list
        return Collections.unmodifiableList(transactionHistory);
    }

    public void setPin(String newPin) {
        this.pin = newPin;
    }

    // For loading transaction history from a persisted state
    public void setTransactionHistoryForLoad(List<String> loadedTransactions) {
        this.transactionHistory.clear();
        if (loadedTransactions != null) {
            this.transactionHistory.addAll(loadedTransactions);
        }
    }

    public boolean validatePIN(String enteredPin) {
        return this.pin != null && this.pin.equals(enteredPin);
    }

    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            addTransaction("Deposited: $" + String.format("%.2f", amount));
        }
        // No action or message for non-positive deposit amount to keep it simple
    }

    public boolean withdraw(double amount) {
        if (amount <= 0) {
            return false; // Non-positive withdrawal amount not allowed
        }
        if (this.balance >= amount) {
            this.balance -= amount;
            addTransaction("Withdrew: $" + String.format("%.2f", amount));
            return true;
        } else {
            addTransaction("Failed withdrawal (insufficient funds): $" + String.format("%.2f", amount));
            return false;
        }
    }

    // For saving account data to a file string
    public String toFileString() {
        return accountNumber + "," + accountHolderName + "," + pin + "," + String.format("%.2f", balance);
    }
}