
import java.util.Scanner;

public class ExpenseTracker {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        double income = 0.0;

        // Array that can store up to 10 expenses
        double[] expenses = new double[10];

        // Keeps track of how many expenses have been entered
        int expenseCount = 0;

        int choice;

        do {
            printMenu();
            choice = getChoice(input);

            switch (choice) {

                case 1:
                    income = enterIncome(input);
                    break;

                case 2:
                    if (expenseCount < expenses.length) {
                        expenses[expenseCount] = addExpense(input);
                        expenseCount++;

                        System.out.println("Expense added successfully.");
                    } else {
                        System.out.println("You have reached the maximum number of expenses.");
                    }
                    break;

                case 3:
                    viewSummary(income, expenses, expenseCount);
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


    // Prompts for and returns a new expense amount
    public static double addExpense(Scanner input) {

        System.out.print("Enter your expense: ");
        return input.nextDouble();
    }


    // Prints the income, expenses, and balance summary
    public static void viewSummary(double income, double[] expenses, int expenseCount) {

        double totalExpenses = 0.0;

        // Add all expenses stored in the array
        for (int i = 0; i < expenseCount; i++) {
            totalExpenses += expenses[i];
        }

        System.out.println("\n----- Expense Summary -----");
        System.out.println("Income: K" + income);
        System.out.println("Total Expenses: K" + totalExpenses);
        System.out.println("Balance: K" + (income - totalExpenses));

        System.out.println("\nIndividual Expenses:");

        for (int i = 0; i < expenseCount; i++) {
            System.out.println("Expense " + (i + 1) + ": K" + expenses[i]);
        }
    }
}
