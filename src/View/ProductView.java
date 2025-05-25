package View;

import Controller.ProductController;
import Model.FoodProduct;
import Model.Product;
import java.util.List;
import java.util.Scanner;

public class ProductView {
    private Scanner scanner;
    private ProductController controller;

    public ProductView(ProductController controller) {
        this.scanner = new Scanner(System.in);
        this.controller = controller;
    }

    public void viewAllProducts() {
        List<Product> products = controller.getProducts();
        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        System.out.printf("%-5s %-20s %-10s %-15s %-10s %-15s %-10s%n",
                "ID", "Name", "Type", "Delivery Price", "Quantity", "Expiration", "Selling Price");

        for (Product p : products) {
            String type = (p instanceof FoodProduct) ? "Food" : "Non-Food";
            String expirationDisplay = (p instanceof FoodProduct) ?
                    p.getExpirationDate().toString() : "Non applicable";
            String expired = (p instanceof FoodProduct && p.isExpired()) ? " (EXPIRED)" : "";

            System.out.printf("%-5d %-20s %-10s %-15.2f %-10d %-15s %-10.2f%s%n",
                    p.getId(), p.getName(), type, p.getDeliveryPrice(), p.getQuantity(),
                    expirationDisplay, p.calculateSellingPrice(controller.getStore()), expired);
        }
    }

    public int getProductIdInput() {
        System.out.print("Enter product ID: ");
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

}