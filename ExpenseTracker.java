import java.io.*;
import java.util.*;

public class ExpenseTracker {

    private static List<String> expenses = new ArrayList<>();
    private static double totalAmountSpent = 0.0;
    private static final String FILE_NAME = "expenses.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Load expenses from file when the program starts
        loadExpensesFromFile();

        while (true) {
            System.out.println("\n*** Expense Tracker ***");
            System.out.println("1. Add Expense");
            System.out.println("2. View All Expenses");
            System.out.println("3. View Total Amount Spent");
            System.out.println("4. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // To consume the newline character

            switch (choice) {
                case 1:
                    addExpense(scanner);
                    break;
                case 2:
                    viewExpenses();
                    break;
                case 3:
                    viewTotalAmount();
                    break;
                case 4:
                    System.out.println("Exiting... Goodbye!");
                    saveExpensesToFile(); // Save expenses to file before exiting
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    // Method to add expense
    private static void addExpense(Scanner scanner) {
        System.out.print("Enter expense description (e.g., food, transport): ");
        String description = scanner.nextLine();
        System.out.print("Enter amount spent: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // To consume the newline character

        expenses.add(description + ": " + amount);
        totalAmountSpent += amount;

        // Save the expense to the file
        saveExpenseToFile(description, amount);

        System.out.println("Expense added successfully!");
    }

    // Method to view all expenses
    private static void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded yet.");
        } else {
            System.out.println("\nExpenses: ");
            for (String expense : expenses) {
                System.out.println(expense);
            }
        }
    }

    // Method to view total amount spent
    private static void viewTotalAmount() {
        System.out.println("\nTotal amount spent: $" + totalAmountSpent);
    }

    // Method to save a single expense to the file
    private static void saveExpenseToFile(String description, double amount) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(description + ": " + amount);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // Method to load all expenses from the file when the program starts
    private static void loadExpensesFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                expenses.add(line);
                // Extracting the amount from the string to update the total spent
                String[] parts = line.split(": ");
                if (parts.length == 2) {
                    totalAmountSpent += Double.parseDouble(parts[1]);
                }
            }
        } catch (IOException e) {
            // If the file doesn't exist or there’s an error, it simply won't load expenses.
            System.out.println("No previous expenses found or error reading file.");
        }
    }

    // Method to save all expenses to the file before exiting
    private static void saveExpensesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String expense : expenses) {
                writer.write(expense);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving expenses to file: " + e.getMessage());
        }
    }
}
