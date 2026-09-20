import java.util.Scanner;
import java.util.ArrayList;

public class ExpenseTracker {

    // Simple class to hold an expense's name and price together
    public static class Expense {
        private String name;
        private double price;

        public Expense(String name, double price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        double income = 0.0;

        // ArrayList for storing expenses (now stores Expense objects, not just numbers)
        ArrayList<Expense> expenses = new ArrayList<>();

        int choice;

        do {
            printMenu();
            choice = getChoice(input);

            switch (choice) {

                case 1:
                    income = enterIncome(input);
                    break;

                case 2:
                    addExpense(input, expenses);
                    break;

                case 3:
                    viewSummary(income, expenses);
                    break;

                case 4:
                    System.out.println("Exiting the Expense Tracker. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 4);

        input.close();
    }


    // Displays the menu options
    public static void printMenu() {

        System.out.println("\nExpense Tracker Menu:");
        System.out.println("1. Enter Income");
        System.out.println("2. Add Expense");
        System.out.println("3. View Summary");
        System.out.println("4. Exit");
    }


    // Reads the user's menu choice
    public static int getChoice(Scanner input) {

        System.out.print("Enter your choice: ");
        return input.nextInt();
    }


    // Prompts for and returns the income amount
    public static double enterIncome(Scanner input) {

        System.out.print("Enter your income: ");
        return input.nextDouble();
    }


    // Adds a new expense (name + price) to the ArrayList
    public static void addExpense(Scanner input, ArrayList<Expense> expenses) {

        input.nextLine(); // clear the leftover newline from nextInt()/nextDouble()

        System.out.print("Enter expense name: ");
        String name = input.nextLine();

        System.out.print("Enter expense price: ");
        double price = input.nextDouble();

        expenses.add(new Expense(name, price));

        System.out.println("Expense added successfully.");
    }


    // Prints the income, expenses, and balance summary
    public static void viewSummary(double income, ArrayList<Expense> expenses) {

        double totalExpenses = 0.0;

        // Calculate total expenses
        for (int i = 0; i < expenses.size(); i++) {

            totalExpenses += expenses.get(i).getPrice();
        }

        System.out.println("\n----- Expense Summary -----");
        System.out.println("Income: K" + income);
        System.out.println("Total Expenses: K" + totalExpenses);
        System.out.println("Balance: K" + (income - totalExpenses));

        System.out.println("\nIndividual Expenses:");

        for (int i = 0; i < expenses.size(); i++) {

            Expense e = expenses.get(i);

            System.out.println(
                "Expense " + (i + 1) + ": " + e.getName() + " - K" + e.getPrice()
            );
        }
    }
}
