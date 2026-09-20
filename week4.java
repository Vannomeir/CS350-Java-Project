import java.util.Scanner;
import java.util.ArrayList;

public class ExpenseTracker {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        double income = 0.0;

        // ArrayList for storing expenses
        ArrayList<Double> expenses = new ArrayList<>();

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


    // Adds a new expense to the ArrayList
    public static void addExpense(Scanner input, ArrayList<Double> expenses) {

        System.out.print("Enter your expense: ");

        double expense = input.nextDouble();

        expenses.add(expense);

        System.out.println("Expense added successfully.");
    }


    // Prints the income, expenses, and balance summary
    public static void viewSummary(double income, ArrayList<Double> expenses) {

        double totalExpenses = 0.0;

        // Calculate total expenses
        for (int i = 0; i < expenses.size(); i++) {

            totalExpenses += expenses.get(i);
        }

        System.out.println("\n----- Expense Summary -----");
        System.out.println("Income: K" + income);
        System.out.println("Total Expenses: K" + totalExpenses);
        System.out.println("Balance: K" + (income - totalExpenses));

        System.out.println("\nIndividual Expenses:");

        for (int i = 0; i < expenses.size(); i++) {

            System.out.println(
                "Expense " + (i + 1) + ": K" + expenses.get(i)
            );
        }
    }
}
