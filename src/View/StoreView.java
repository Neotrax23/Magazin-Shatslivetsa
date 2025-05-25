package View;

import Controller.*;
import Model.*;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class StoreView {
    private Scanner scanner;
    private StoreController controller;
    private ProductView productView;
    private FoodProductView foodProductView;
    private NonFoodProductView nonFoodProductView;
    private CashierView cashierView;
    private ReceiptView receiptView;

    public StoreView(StoreController controller, ProductView productView, CashierView cashierView, ReceiptView receiptView, FoodProductView foodProductView, NonFoodProductView nonFoodProductView) {
        this.scanner = new Scanner(System.in);
        this.controller = controller;
        this.productView = productView;
        this.foodProductView = foodProductView;
        this.nonFoodProductView = nonFoodProductView;
        this.cashierView = cashierView;
        this.receiptView = receiptView;
    }

    public void showMainMenu() {
        while (true) {
            System.out.println("\n=== Main Menu ===");
            System.out.println("1. Manage Products");
            System.out.println("2. Manage Cashiers");
            System.out.println("3. Process Sale");
            System.out.println("4. View Store Statistics");
            System.out.println("5. Exit");

            int choice = getValidIntInput("Select option: ");

            switch (choice) {
                case 1: manageProducts(); break;
                case 2: manageCashiers(); break;
                case 3: processSale(); break;
                case 4: viewStatistics(); break;
                case 5: System.out.println("Exiting system..."); return;
                default: System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void manageProducts() {
        while (true) {
            System.out.println("\n=== Product Management ===");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Back to Main Menu");

            int choice = getValidIntInput("Select option: ");

            switch (choice) {
                case 1: addProduct(); break;
                case 2: productView.viewAllProducts(); break;
                case 3: return;
                default: System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void addProduct() {
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
            if (isFood.equals("y") || isFood.equals("n")) break;
            System.out.println("Invalid input. Please enter 'y' or 'n'.");
        }

        Product product;
        if (isFood.equals("y")) {
            LocalDate expirationDate = foodProductView.getExpirationDateInput();
            product = new FoodProduct(id, name, price, expirationDate, quantity);
        } else {
            product = new NonFoodProduct(id, name, price, LocalDate.now().plusYears(10), quantity);
        }

        controller.addProduct(product);
        System.out.println("Product added successfully!");
    }

    private void manageCashiers() {
        while (true) {
            System.out.println("\n=== Cashier Management ===");
            System.out.println("1. Add Cashier");
            System.out.println("2. View Cashiers");
            System.out.println("3. Back to Main Menu");

            int choice = getValidIntInput("Select option: ");

            switch (choice) {
                case 1: addCashier(); break;
                case 2: cashierView.viewAllCashiers(); break;
                case 3: return;
                default: System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void addCashier() {
        System.out.println("\n=== Add New Cashier ===");
        int id = cashierView.getCashierIdInput();
        System.out.print("Enter cashier name: ");
        String name = scanner.nextLine();
        double salary = getValidDoubleInput("Enter monthly salary: ");

        Cashier cashier = new Cashier(id, name, salary);
        controller.addCashier(cashier);
        System.out.println("Cashier added successfully!");
    }

    private void processSale() {
        System.out.println("\n=== Process Sale ===");
        List<Cashier> cashiers = controller.getAllCashiers();

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
            if (cashierIndex >= 0 && cashierIndex < cashiers.size()) break;
            System.out.println("Invalid selection. Please try again.");
        }

        Cashier selectedCashier = cashiers.get(cashierIndex);
        Receipt receipt = controller.createReceipt(selectedCashier);

        while (true) {
            System.out.println("\nCurrent Receipt Total: $" + receipt.getTotalAmount());
            System.out.println("1. Add Product");
            System.out.println("2. Finish Sale");

            int choice = getValidIntInput("Select option: ");
            if (choice == 2) break;

            productView.viewAllProducts();
            int productId = productView.getProductIdInput();
            int quantity = getValidIntInput("Enter quantity: ");

            Product selectedProduct = controller.getProductById(productId);
            if (selectedProduct == null) {
                System.out.println("Product not found!");
                continue;
            }

            try {
                controller.addProductToReceipt(receipt, selectedProduct, quantity);
                System.out.printf("Added %d x %s to receipt%n", quantity, selectedProduct.getName());
            } catch (InsufficientQuantityException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        receiptView.printReceiptDetails(receipt);
        receiptView.saveReceiptToFile(receipt);
    }

    private void viewStatistics() {
        System.out.println("\n=== Store Statistics ===");
        System.out.printf("Total Revenue: $%.2f%n", controller.calculateTotalRevenue());
        System.out.printf("Total Expenses: $%.2f%n", controller.calculateTotalExpenses());
        System.out.printf("Profit: $%.2f%n", controller.calculateProfit());

        System.out.println("\nInventory Value:");
        double totalInventoryValue = 0;
        for (Product p : controller.getAllProducts()) {
            double value = p.getDeliveryPrice() * p.getQuantity();
            System.out.printf("%s: $%.2f%n", p.getName(), value);
            totalInventoryValue += value;
        }
        System.out.printf("Total Inventory Value: $%.2f%n", totalInventoryValue);

        System.out.println("\nTotal Receipts Issued: " + controller.getTotalReceiptsIssued());
    }

    private int getValidIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    private double getValidDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}