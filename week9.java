import java.util.ArrayList;
import java.util.Scanner;

/*
 * WEEK 9 - POLYMORPHISM & ABSTRACTION
 * Builds on Week 8 by:
 *   - Abstract Class (abstract class Expense): Defines a common template that cannot be instantiated directly
 *   - Abstract Methods: Forces every subclass to implement getFinalCost() and getExpenseType()
 *   - Interfaces (implements): Adds capability contracts (Exportable, Taxable) across classes
 *   - Polymorphism & Dynamic Dispatch: Budget stores parent Expense references and invokes subclass
 *     implementations dynamically at runtime
 */
public class Week9 {
    public interface Exportable {
        String toCsvRow();
    }

    public interface Taxable {
        double VAT_RATE = 0.16; 
        double calculateTax();
    }

    public static abstract class Expense implements Exportable {

        private String name;
        private double price;
        private String category;

        public Expense(String name, double price, String category) {
            setName(name);
            setPrice(price);
            setCategory(category);
        }

        public String getName() {
            return name;
        }

        // Returns the base/sticker price
        public double getBasePrice() {
            return price;
        }

        public String getCategory() {
            return category;
        }

        public void setName(String name) {
            if (name == null || name.trim().isEmpty()) {
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

        // ABSTRACT METHODS: Subclasses MUST override and implement these
        public abstract double getFinalCost();
        public abstract String getExpenseType();

        // TEMPLATE METHOD: Uses polymorphic calls to getExpenseType() and getFinalCost()
        public String describe() {
            return "[" + getExpenseType() + "] " + getName() + " (" + getCategory() + ") - K"
                    + String.format("%.2f", getFinalCost());
        }

        // Interface implementation inherited by all Expense subclasses
        @Override
        public String toCsvRow() {
            return getExpenseType() + "," + getName() + "," + getCategory() + ","
                    + String.format("%.2f", getFinalCost());
        }

        @Override
        public String toString() {
            return describe();
        }
    }
    public static class StandardExpense extends Expense implements Taxable {

        private boolean includesVat;

        public StandardExpense(String name, double price, String category, boolean includesVat) {
            super(name, price, category);
            this.includesVat = includesVat;
        }

        @Override
        public double calculateTax() {
            return includesVat ? getBasePrice() * VAT_RATE : 0.0;
        }

        @Override
        public double getFinalCost() {
            return getBasePrice() + calculateTax();
        }

        @Override
        public String getExpenseType() {
            return includesVat ? "Standard + VAT" : "Standard";
        }
    }

    public static class RecurringExpense extends Expense {

        private String frequency;
        private int monthsCommitted;

        public RecurringExpense(String name, double monthlyPrice, String category, String frequency, int monthsCommitted) {
            super(name, monthlyPrice, category);
            this.frequency = (frequency == null || frequency.trim().isEmpty()) ? "Monthly" : frequency.trim();
            this.monthsCommitted = Math.max(1, monthsCommitted);
        }

        public String getFrequency() {
            return frequency;
        }

        public int getMonthsCommitted() {
            return monthsCommitted;
        }

        @Override
        public double getFinalCost() {
            return getBasePrice() * monthsCommitted;
        }

        @Override
        public String getExpenseType() {
            return "Recurring (" + frequency + " x" + monthsCommitted + ")";
        }
    }

    public static class DiscountedExpense extends Expense {

        private double discountPercent;

        public DiscountedExpense(String name, double originalPrice, String category, double discountPercent) {
            super(name, originalPrice, category);
            if (discountPercent < 0 || discountPercent > 100) {
                System.out.println("Invalid discount. Setting to 0%.");
                this.discountPercent = 0.0;
            } else {
                this.discountPercent = discountPercent;
            }
        }

        @Override
        public double getFinalCost() {
            return getBasePrice() * (1.0 - (discountPercent / 100.0));
        }

        @Override
        public String getExpenseType() {
            return "Discounted -" + discountPercent + "%";
        }

        @Override
        public String describe() {
            return super.describe() + " (Was K" + String.format("%.2f", getBasePrice()) + ")";
        }
    }

    public static class Budget {

        private double income;
        private ArrayList<Expense> expenses; // Polymorphic collection

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

        // Accepts ANY subclass of Expense (Standard, Recurring, Discounted)
        public void addExpense(Expense e) {
            if (e != null) {
                expenses.add(e);
            }
        }

        // DYNAMIC DISPATCH: Calls the appropriate subclass's getFinalCost() at runtime
        public double getTotalExpenses() {
            double total = 0.0;
            for (Expense e : expenses) {
                total += e.getFinalCost();
            }
            return total;
        }

        // Interface-based polymorphism: checks which items implement Taxable
        public double getTotalVatPaid() {
            double totalVat = 0.0;
            for (Expense e : expenses) {
                if (e instanceof Taxable taxableItem) {
                    totalVat += taxableItem.calculateTax();
                }
            }
            return totalVat;
        }

        public double getBalance() {
            return income - getTotalExpenses();
        }

        public void printSummary() {
            System.out.println("\n----- Polymorphic Expense Summary -----");
            System.out.println("Income         : K" + String.format("%.2f", income));
            System.out.println("Total Expenses : K" + String.format("%.2f", getTotalExpenses()));
            System.out.println("Total VAT Paid : K" + String.format("%.2f", getTotalVatPaid()));
            System.out.println("Balance        : K" + String.format("%.2f", getBalance()));

            System.out.println("\nIndividual Expenses:");
            for (int i = 0; i < expenses.size(); i++) {
                System.out.println((i + 1) + ". " + expenses.get(i).describe());
            }
        }

        // Uses the Exportable interface contract
        public void exportToCsv() {
            System.out.println("\n--- CSV Export (Exportable Interface) ---");
            System.out.println("Type,Name,Category,FinalCost");
            for (Exportable item : expenses) {
                System.out.println(item.toCsvRow());
            }
        }
    }

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
                    budget.exportToCsv();
                    break;

                case 5:
                    polymorphismDemo();
                    break;

                case 6:
                    System.out.println("Exiting the Expense Tracker. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 6);

        input.close();
    }

    public static void printMenu() {
        System.out.println("\nExpense Tracker Menu (Week 9 - Polymorphism & Abstraction):");
        System.out.println("1. Enter Income");
        System.out.println("2. Add Expense (Standard, Recurring, or Discounted)");
        System.out.println("3. View Summary");
        System.out.println("4. Export Expenses to CSV");
        System.out.println("5. Week 9 Polymorphism & Abstraction Demo");
        System.out.println("6. Exit");
    }

    public static void addExpenseMenu(Scanner input, Budget budget) {
        System.out.println("Select Type: 1 = Standard | 2 = Recurring | 3 = Discounted");
        int type = readInt(input, "Type: ");

        System.out.print("Enter expense name: ");
        String name = input.nextLine();
        double price = readDouble(input, "Enter base price (K): ");
        System.out.print("Enter category (press Enter to skip): ");
        String category = input.nextLine();

        if (type == 2) {
            System.out.print("Enter frequency (e.g., Monthly): ");
            String freq = input.nextLine();
            int months = readInt(input, "Enter number of months: ");
            budget.addExpense(new RecurringExpense(name, price, category, freq, months));
        } else if (type == 3) {
            double discount = readDouble(input, "Enter discount percentage (0-100): ");
            budget.addExpense(new DiscountedExpense(name, price, category, discount));
        } else {
            System.out.print("Does this item include 16% VAT? (y/n): ");
            boolean vat = input.nextLine().trim().equalsIgnoreCase("y");
            budget.addExpense(new StandardExpense(name, price, category, vat));
        }
        System.out.println("Expense added successfully.");
    }

    public static void polymorphismDemo() {
        System.out.println("\n--- Week 9 Polymorphism & Abstraction Demo ---");

        // Compile-time error if uncommented: 'Expense is abstract; cannot be instantiated'
        // Expense invalid = new Expense("Generic", 100.0, "Misc");

        // Parent reference (Expense) pointing to different child objects (Polymorphism)
        Expense e1 = new StandardExpense("Electronics", 1000.0, "Tech", true);
        Expense e2 = new RecurringExpense("Internet", 350.0, "Utilities", "Monthly", 3);
        Expense e3 = new DiscountedExpense("Textbooks", 600.0, "Education", 20.0);

        Budget demoBudget = new Budget(5000.0);
        demoBudget.addExpense(e1);
        demoBudget.addExpense(e2);
        demoBudget.addExpense(e3);

        demoBudget.printSummary();
        demoBudget.exportToCsv();
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
