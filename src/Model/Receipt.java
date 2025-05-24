package Model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Receipt {
    private static int totalReceipts = 0;
    private static double totalRevenue = 0;

    private int receiptNumber;
    private Cashier cashier;
    private LocalDateTime dateTime;
    private Map<Product, Integer> products;
    private double totalAmount;

    public Receipt(Cashier cashier) {
        this.receiptNumber = ++totalReceipts;
        this.cashier = cashier;
        this.dateTime = LocalDateTime.now();
        this.products = new HashMap<>();
        this.totalAmount = 0;
    }

    public void addProduct(Product product, int quantity, Store store) throws InsufficientQuantityException {
        if (product.getQuantity() < quantity) {
            throw new InsufficientQuantityException(product, quantity - product.getQuantity());
        }
        products.put(product, quantity);
        totalAmount += product.calculateSellingPrice(store) * quantity;
        totalRevenue += product.calculateSellingPrice(store) * quantity;
        product.setQuantity(product.getQuantity() - quantity);
    }

    // Getters
    public static int getTotalReceipts() { return totalReceipts; }
    public static double getTotalRevenue() { return totalRevenue; }
    public int getReceiptNumber() { return receiptNumber; }
    public Cashier getCashier() { return cashier; }
    public LocalDateTime getDateTime() { return dateTime; }
    public Map<Product, Integer> getProducts() { return products; }
    public double getTotalAmount() { return totalAmount; }
}