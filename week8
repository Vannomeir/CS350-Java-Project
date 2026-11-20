import java.util.ArrayList;
import java.util.Scanner;

/*
 * WEEK 8 - INHERITANCE & METHOD OVERRIDING
 * Builds on Week 7 by:
 *   - Superclass & Subclasses (extends): RecurringExpense and DiscountedExpense IS-A Expense
 *   - Constructor Chaining across Hierarchy: super(...) as the first statement
 *   - Encapsulation across Inheritance: private fields in Expense accessed via getters in subclasses
 *   - Method Overriding (@Override) & super.method() to specialize or extend parent behaviour
 *   - Composition (HAS-A): Budget HAS-A list of Expense objects (polymorphic collection)
 */
public class Week8 {

    // =========================================================================
    // 1. SUPERCLASS: Expense
    // =========================================================================
    public static class Expense {

        // ENCAPSULATION: private fields belong strictly to Expense
        private String name;
        private double price;
        private String category;

        public Expense() {
            this("Unnamed", 0.0, "General");
        }

        public Expense(String name, double price) {
            this(name, price, "General");
        }

        public Expense(String name, double price, String category) {
            setName(name);
            setPrice(price);
            setCategory(category);
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public String getCategory() {
            return category;
        }

        public void setName(String name) {
            if (name == null || name.trim().isEmpty()) {
                System.out.println("Invalid name. Using 'Unnamed'.");
                this.name = "Unnamed";
            } else {
                this.name = name.trim();
            }
        }

        public void setPrice(double price) {
            if (price < 0) {
                System.out.println("Price cannot be negative. Setting price to K0.0.");
                this.price = 0.0;
            } else {
                this.price = price;
            }
        }

        public void setCategory(String category) {
            if (category == null || category.trim().isEmpty()) {
                this.category = "General";
            } else {
                this.category = category.trim();
            }
        }

        // Base behaviour meant to be inherited or overridden by subclasses
        public String describe() {
            return getName() + " (" + getCategory() + ") - K" + String.format("%.2f", getPrice());
        }

        @Override
        public String toString() {
            return describe(); // Delegates to describe() so subclasses inherit toString() automatically
        }
    }

    // =========================================================================
    // 2. SUBCLASS A: RecurringExpense (IS-A Expense)
    // =========================================================================
    public static class RecurringExpense extends Expense {

        private String frequency; // e.g., "Monthly", "Weekly"

        public RecurringExpense(String name, double price, String category, String frequency) {
            super(name, price, category); // MUST be the first statement; initializes parent state
            setFrequency(frequency);
        }

        public String getFrequency() {
            return frequency;
        }

        public void setFrequency(String frequency) {
            if (frequency == null || frequency.trim().isEmpty()) {
                this.frequency = "Monthly";
            } else {
                this.frequency = frequency.trim();
            }
        }

        // OVERRIDING: Extends parent behaviour using super.describe()
        @Override
        public String describe() {
            return super.describe() + " [Recurring: " + frequency + "]";
        }
    }

    // =========================================================================
    // 3. SUBCLASS B: DiscountedExpense (IS-A Expense)
    // =========================================================================
    public static class DiscountedExpense extends Expense {

        private double discountPercent;

        public DiscountedExpense(String name, double originalPrice, String category, double discountPercent) {
            super(name, originalPrice, category); // Calls Expense(name, price, category)
            setDiscountPercent(discountPercent);
        }

        public double getDiscountPercent() {
            return discountPercent;
        }

        public void setDiscountPercent(double discountPercent) {
            if (discountPercent < 0 || discountPercent > 100) {
                System.out.println("Invalid discount. Setting to 0%.");
                this.discountPercent = 0.0;
            } else {
                this.discountPercent = discountPercent;
            }
        }

        // OVERRIDING: Replaces getPrice() calculation using parent's private price via super.getPrice()
        @Override
        public double getPrice() {
            double original = super.getPrice(); // Cannot access 'price' directly because it is private!
            return original - (original * (discountPercent / 100.0));
        }

        // OVERRIDING: Specializes description while keeping encapsulation intact
        @Override
        public String describe() {
            return getName() + " (" + getCategory() + ") - K" + String.format("%.2f", getPrice())
                    + " [Original: K" + String.format("%.2f", super.getPrice())
                    + ", Saved " + discountPercent + "%]";
        }
    }

    // =========================================================================
    // 4. COMPOSITION (HAS-A): Budget contains Expense (and any of its subclasses)
    // =========================================================================
    public static class Budget {

        private double income;
        private ArrayList<Expense> expenses; // Holds Expense, RecurringExpense, and DiscountedExpense

        public Budget() {
            this(0.0);
        }

        public Budget(double income) {
            setIncome(income);
            this.expenses = new ArrayList<>();
        }

        public double getIncome() {
            return income;
        }

        public void setIncome(double income) {
            if (income < 0) {
                System.out.println("Income cannot be negative. Setting income to K0.0.");
                this.income = 0.0;
            } else {
                this.income = income;
            }
        }

        public void addExpense(Expense e) {
            if (e != null) {
                expenses.add(e);
            }
        }

        public ArrayList<Expense> getExpenses() {
            return new ArrayList<>(expenses);
        }

        // Dynamic method selection (Week 8 Slide 16):
        // Calls DiscountedExpense.getPrice() for discounted items, and Expense.getPrice() for standard items
        public double getTotalExpenses() {
            double total = 0.0;
            for (Expense e : expenses) {
                total += e.getPrice();
            }
            return total;
        }

        public double getBalance() {
            return income - getTotalExpenses();
        }

        public void printSummary() {
            System.out.println("\n----- Expense Summary -----");
            System.out.println("Income: K" + String.format("%.2f", income));
            System.out.println("Total Expenses: K" + String.format("%.2f", getTotalExpenses()));
            System.out.println("Balance: K" + String.format("%.2f", getBalance()));

            System.out.println("\nIndividual Expenses:");
            for (int i = 0; i < expenses.size(); i++) {
                // Automatically calls each object's overridden describe() via toString()
                System.out.println("Expense " + (i + 1) + ": " + expenses.get(i));
            }
        }
    }

    // =========================================================================
    // 5. MAIN: Menu-driven Expense Tracker
    // =========================================================================
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Budget budget = new Budget();

        int choice;

        do {
            printMenu();
            choice = readInt(input, "Enter your choice: ");

            switch (choice) {
                case 1:
                    budget.setIncome(readDouble(input, "Enter your income: "));
                    break;

                case 2:
                    addExpenseMenu(input, budget);
                    break;

                case 3:
                    budget.printSummary();
                    break;

                case 4:
                    inheritanceDemo();
                    break;

                case 5:
                    System.out.println("Exiting the Expense Tracker. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 5);

        input.close();
    }

    public static void printMenu() {
        System.out.println("\nExpense Tracker Menu:");
        System.out.println("1. Enter Income");
        System.out.println("2. Add Expense (Standard, Recurring, or Discounted)");
        System.out.println("3. View Summary");
        System.out.println("4. Week 8 Inheritance & Overriding Demo");
        System.out.println("5. Exit");
    }

    public static void addExpenseMenu(Scanner input, Budget budget) {
        System.out.println("Select Expense Type: 1 = Standard | 2 = Recurring | 3 = Discounted");
        int type = readInt(input, "Type: ");

        System.out.print("Enter expense name: ");
        String name = input.nextLine();
        double price = readDouble(input, "Enter expense price (K): ");
        System.out.print("Enter category (press Enter to skip): ");
        String category = input.nextLine();

        if (type == 2) {
            System.out.print("Enter frequency (e.g., Monthly, Weekly): ");
            String freq = input.nextLine();
            budget.addExpense(new RecurringExpense(name, price, category, freq));
        } else if (type == 3) {
            double discount = readDouble(input, "Enter discount percentage (0-100): ");
            budget.addExpense(new DiscountedExpense(name, price, category, discount));
        } else {
            budget.addExpense(new Expense(name, price, category));
        }
        System.out.println("Expense added successfully.");
    }

    // Demonstrates Week 8 concepts: IS-A, super(), @Override, super.method(), and dynamic dispatch
    public static void inheritanceDemo() {
        System.out.println("\n--- Week 8 Inheritance & Overriding Demo ---");

        // Parent references pointing to child objects (Slide 16: Dynamic method selection)
        Expense e1 = new Expense("Groceries", 250.0, "Food");
        Expense e2 = new RecurringExpense("Rent", 1500.0, "Housing", "Monthly");
        Expense e3 = new DiscountedExpense("Laptop Bag", 400.0, "Accessories", 25.0);

        System.out.println("Standard   : " + e1.describe());
        System.out.println("Recurring  : " + e2.describe());
        System.out.println("Discounted : " + e3.describe());

        Budget demoBudget = new Budget(3000.0);
        demoBudget.addExpense(e1);
        demoBudget.addExpense(e2);
        demoBudget.addExpense(e3);
        demoBudget.printSummary(); // Total uses DiscountedExpense's overridden getPrice() (K300 instead of K400)
    }

    public static int readInt(Scanner input, String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(input.nextLine().trim());
        } catch (NumberFormatException ex) {
            return -1;
        }
    }

    public static double readDouble(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(input.nextLine().trim());
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
