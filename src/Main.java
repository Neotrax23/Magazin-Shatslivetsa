import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Main {
    private static Store store;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeStore();
        showMainMenu();
    }

    private static void initializeStore() {
        System.out.println("=== Магазин Щастливеца ===");

        double foodMarkup = getValidDoubleInput("Напиши процент надценка на храна : ") / 100;
        double nonFoodMarkup = getValidDoubleInput("Напиши процент надценка за стоки, които не са храни: ") / 100;
        double expirationDiscount = getValidDoubleInput("Enter expiration discount percentage: ") / 100;
        int daysBeforeExpiration = getValidIntInput("Enter days before expiration for discount: ");

        store = new Store(foodMarkup, nonFoodMarkup, expirationDiscount, daysBeforeExpiration);
        System.out.println("Store initialized successfully!\n");
    }

    private static int getValidIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }
    private static double getValidDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
    private static void showMainMenu() {
        while (true) {
            System.out.println("\n=== Main Menu ===");
            System.out.println("1. Manage Products");
            System.out.println("2. Manage Cashiers");
            System.out.println("3. Process Sale");
            System.out.println("4. View Store Statistics");
            System.out.println("5. Exit");

            int choice = getValidIntInput("Select option: ");

            switch (choice) {
                case 1:
                    manageProducts();
                    break;
                case 2:
                    manageCashiers();
                    break;
                case 3:
                    processSale();
                    break;
                case 4:
                    viewStatistics();
                    break;
                case 5:
                    System.out.println("Exiting system...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    private static void manageProducts() {
        while (true) {
            System.out.println("\n=== Product Management ===");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Back to Main Menu");

            int choice = getValidIntInput("Select option: ");

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    viewProducts();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    private static void addProduct() {
        System.out.println("\n=== Add New Product ===");

        int id = getValidIntInput("Enter product ID: ");

        System.out.print("Enter product name: ");
        String name = scanner.nextLine();

        double price = getValidDoubleInput("Enter delivery price: ");

        int quantity = getValidIntInput("Enter quantity: ");

        String isFood;
        while (true) {
            System.out.print("Is this a food product? (y/n): ");
            isFood = scanner.nextLine().trim().toLowerCase();
            if (isFood.equals("y") || isFood.equals("n")) {
                break;
            }
            System.out.println("Invalid input. Please enter 'y' or 'n'.");
        }

        Product product;
        if (isFood.equals("y")) {
            // Only ask for expiration date if it's a food product
            LocalDate expirationDate = getValidDateInput("Enter expiration date (YYYY-MM-DD): ");
            product = new FoodProduct(id, name, price, expirationDate, quantity);
        } else {
            // For non-food products, set a default expiration date far in the future
            product = new NonFoodProduct(id, name, price, LocalDate.now().plusYears(10), quantity);
        }

        store.addProduct(product);
        System.out.println("Product added successfully!");
    }
    private static LocalDate getValidDateInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return LocalDate.parse(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD format.");
            }
        }
    }
    private static void viewProducts() {
        System.out.println("\n=== Product Inventory ===");
        List<Product> products = store.getInventory();

        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        System.out.printf("%-5s %-20s %-10s %-15s %-10s %-15s %-10s%n",
                "ID", "Name", "Type", "Delivery Price", "Quantity", "Expiration", "Selling Price");

        for (Product p : products) {
            String type = (p instanceof FoodProduct) ? "Food" : "Non-Food";
            String expirationDisplay = (p instanceof FoodProduct) ? p.getExpirationDate().toString() : "Non applicable";
            String expired = (p instanceof FoodProduct && p.isExpired()) ? " (EXPIRED)" : "";

            System.out.printf("%-5d %-20s %-10s %-15.2f %-10d %-15s %-10.2f%s%n",
                    p.getId(), p.getName(), type, p.getDeliveryPrice(), p.getQuantity(),
                    expirationDisplay, p.calculateSellingPrice(store), expired);
        }

    }
    private static void manageCashiers() {
        while (true) {
            System.out.println("\n=== Cashier Management ===");
            System.out.println("1. Add Cashier");
            System.out.println("2. View Cashiers");
            System.out.println("3. Back to Main Menu");

            int choice = getValidIntInput("Select option: ");

            switch (choice) {
                case 1:
                    addCashier();
                    break;
                case 2:
                    viewCashiers();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    private static void addCashier() {
        System.out.println("\n=== Add New Cashier ===");

        int id = getValidIntInput("Enter cashier ID: ");

        System.out.print("Enter cashier name: ");
        String name = scanner.nextLine();

        double salary = getValidDoubleInput("Enter monthly salary: ");

        Cashier cashier = new Cashier(id, name, salary);
        store.addCashier(cashier);
        System.out.println("Cashier added successfully!");
    }
    private static void viewCashiers() {
        System.out.println("\n=== Cashiers ===");
        List<Cashier> cashiers = store.getCashiers();

        if (cashiers.isEmpty()) {
            System.out.println("No cashiers available.");
            return;
        }

        System.out.printf("%-5s %-20s %-15s%n", "ID", "Name", "Monthly Salary");
        for (Cashier c : cashiers) {
            System.out.printf("%-5d %-20s %-15.2f%n", c.getId(), c.getName(), c.getMonthlySalary());
        }
    }
    private static void processSale() {
        System.out.println("\n=== Process Sale ===");

        List<Cashier> cashiers = store.getCashiers();
        if (cashiers.isEmpty()) {
            System.out.println("No cashiers available. Please add cashiers first.");
            return;
        }

        System.out.println("Available Cashiers:");
        for (int i = 0; i < cashiers.size(); i++) {
            System.out.printf("%d. %s%n", i+1, cashiers.get(i).getName());
        }

        int cashierIndex;
        while (true) {
            cashierIndex = getValidIntInput("Select cashier (1-" + cashiers.size() + "): ") - 1;
            if (cashierIndex >= 0 && cashierIndex < cashiers.size()) {
                break;
            }
            System.out.println("Invalid selection. Please try again.");
        }

        Cashier selectedCashier = cashiers.get(cashierIndex);
        Receipt receipt = store.createReceipt(selectedCashier);

        while (true) {
            System.out.println("\nCurrent Receipt Total: $" + receipt.getTotalAmount());
            System.out.println("1. Add Product");
            System.out.println("2. Finish Sale");

            int choice = getValidIntInput("Select option: ");

            if (choice == 2) break;

            viewProducts();
            int productId = getValidIntInput("Enter product ID to add: ");

            int quantity = getValidIntInput("Enter quantity: ");

            Product selectedProduct = null;
            for (Product p : store.getInventory()) {
                if (p.getId() == productId) {
                    selectedProduct = p;
                    break;
                }
            }

            if (selectedProduct == null) {
                System.out.println("Product not found!");
                continue;
            }

            try {
                receipt.addProduct(selectedProduct, quantity, store);
                System.out.printf("Added %d x %s to receipt%n", quantity, selectedProduct.getName());
            } catch (InsufficientQuantityException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        System.out.println("\n=== Receipt Summary ===");
        System.out.println("Receipt #" + receipt.getReceiptNumber());
        System.out.println("Cashier: " + receipt.getCashier().getName());
        System.out.println("Date: " + receipt.getDateTime());

        System.out.println("\nItems Purchased:");
        System.out.printf("%-20s %-10s %-10s %-10s%n", "Product", "Price", "Qty", "Subtotal");
        for (Map.Entry<Product, Integer> entry : receipt.getProducts().entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            double price = p.calculateSellingPrice(store);
            System.out.printf("%-20s %-10.2f %-10d %-10.2f%n",
                    p.getName(), price, qty, price * qty);
        }

        System.out.println("\nTotal Amount: $" + receipt.getTotalAmount());
        saveReceiptToFile(receipt);
    }

    private static void saveReceiptToFile(Receipt receipt) {
        String filename = "receipt_" + receipt.getReceiptNumber() + ".txt";
        try (PrintWriter writer = new PrintWriter(filename)) {
            writer.println("=== Receipt ===");
            writer.println("Number: " + receipt.getReceiptNumber());
            writer.println("Cashier: " + receipt.getCashier().getName());
            writer.println("Date: " + receipt.getDateTime());

            writer.println("\nItems:");
            for (Map.Entry<Product, Integer> entry : receipt.getProducts().entrySet()) {
                Product p = entry.getKey();
                int qty = entry.getValue();
                double price = p.calculateSellingPrice(store);
                writer.printf("%s - %.2f x %d = %.2f%n",
                        p.getName(), price, qty, price * qty);
            }

            writer.println("\nTotal: " + receipt.getTotalAmount());
            System.out.println("Receipt saved to " + filename);
        } catch (FileNotFoundException e) {
            System.out.println("Error saving receipt: " + e.getMessage());
        }
    }
    private static void viewStatistics() {
        System.out.println("\n=== Store Statistics ===");
        System.out.printf("Total Revenue: $%.2f%n", store.calculateTotalRevenue());
        System.out.printf("Total Expenses: $%.2f%n", store.calculateTotalExpenses());
        System.out.printf("Profit: $%.2f%n", store.calculateProfit());

        System.out.println("\nInventory Value:");
        double totalInventoryValue = 0;
        for (Product p : store.getInventory()) {
            double value = p.getDeliveryPrice() * p.getQuantity();
            System.out.printf("%s: $%.2f%n", p.getName(), value);
            totalInventoryValue += value;
        }
        System.out.printf("Total Inventory Value: $%.2f%n", totalInventoryValue);

        System.out.println("\nTotal Receipts Issued: " + Receipt.getTotalReceipts());
    }
}