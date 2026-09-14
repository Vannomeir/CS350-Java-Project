import java.util.Scanner;

public class ExpenseTracker{
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        double income = 0.0;
        double expenses = 0.0;

        int choice;
        do{
            System.out.println("Expense Tracker Menu:");
            System.out.println("1. Enter Income");
            System.out.println("2. Add Expense");
            System.out.println("3. View Summary");
            System.out.println("4. Exit");

            System.out.print("Enter your choice: ");
            choice = input.nextInt();

            switch(choice){
                case 1:
                    System.out.print("Enter your income: ");
                    income = input.nextDouble();
                    break;
                case 2:
                    System.out.print("Enter your expense: ");
                    expenses += input.nextDouble();
                    break;
                case 3:
                    System.out.println("Income: K" + income);
                    System.out.println("Expenses: K" + expenses);
                    System.out.println("Balance: K" + (income - expenses));
                    break;
                case 4:
                    System.out.println("Exiting the Expense Tracker. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while(choice != 4);
    }
}
