import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class StoreManagementSystem {
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
}