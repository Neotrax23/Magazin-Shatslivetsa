package View;

import Controller.ReceiptController;
import Model.Product;
import Model.Receipt;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;

public class ReceiptView {
    private Scanner scanner;
    private ReceiptController controller;

    public ReceiptView(ReceiptController controller) {
        this.scanner = new Scanner(System.in);
        this.controller = controller;
    }

    public void printReceiptDetails(Receipt receipt) {
        System.out.println("\n=== Receipt Summary ===");
        System.out.println("Receipt #" + receipt.getReceiptNumber());
        System.out.println("Cashier: " + receipt.getCashier().getName());
        System.out.println("Date: " + receipt.getDateTime());

        System.out.println("\nItems Purchased:");
        System.out.printf("%-20s %-10s %-10s %-10s%n",
                "Product", "Price", "Qty", "Subtotal");

        Map<Product, Integer> products = controller.getReceiptProducts(receipt);
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            double price = p.calculateSellingPrice(controller.getStore());
            System.out.printf("%-20s %-10.2f %-10d %-10.2f%n",
                    p.getName(), price, qty, price * qty);
        }

        System.out.println("\nTotal Amount: $" + receipt.getTotalAmount());
    }

    public void saveReceiptToFile(Receipt receipt) {
        String filename = "receipt_" + receipt.getReceiptNumber() + ".txt";
        try (PrintWriter writer = new PrintWriter(filename)) {
            writer.println("=== Receipt ===");
            writer.println("Number: " + receipt.getReceiptNumber());
            writer.println("Cashier: " + receipt.getCashier().getName());
            writer.println("Date: " + receipt.getDateTime());

            writer.println("\nItems:");
            Map<Product, Integer> products = controller.getReceiptProducts(receipt);
            for (Map.Entry<Product, Integer> entry : products.entrySet()) {
                Product p = entry.getKey();
                int qty = entry.getValue();
                double price = p.calculateSellingPrice(controller.getStore());
                writer.printf("%s - %.2f x %d = %.2f%n",
                        p.getName(), price, qty, price * qty);
            }

            writer.println("\nTotal: " + receipt.getTotalAmount());
            System.out.println("Receipt saved to " + filename);
        } catch (FileNotFoundException e) {
            System.out.println("Error saving receipt: " + e.getMessage());
        }
    }
}