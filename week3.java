import java.util.Scanner;

public class ExpenseTracker {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        double income = 0.0;
        double expenses = 0.0;

        int choice;
        do {
            printMenu();
            choice = getChoice(input);

            switch (choice) {
                case 1:
                    income = enterIncome(input);
                    break;
                case 2:
                    expenses += addExpense(input);
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
        System.out.println("Expense Tracker Menu:");
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

    // Prompts for and returns a new expense amount to add
    public static double addExpense(Scanner input) {
        System.out.print("Enter your expense: ");
        return input.nextDouble();
    }

    // Prints the income, expenses, and balance summary
    public static void viewSummary(double income, double expenses) {
        System.out.println("Income: K" + income);
        System.out.println("Expenses: K" + expenses);
        System.out.println("Balance: K" + (income - expenses));
    }
}
