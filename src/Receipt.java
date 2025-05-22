import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Receipt implements Serializable {
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
        totalRevenue += totalAmount;
        product.setQuantity(product.getQuantity() - quantity);
    }

    // Getters and setters
    public static int getTotalReceipts() {
        return totalReceipts;
    }

    public static void setTotalReceipts(int totalReceipts) {
        Receipt.totalReceipts = totalReceipts;
    }

    public static double getTotalRevenue() {
        return totalRevenue;
    }

    public static void setTotalRevenue(double totalRevenue) {
        Receipt.totalRevenue = totalRevenue;
    }

    public int getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(int receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public Cashier getCashier() {
        return cashier;
    }

    public void setCashier(Cashier cashier) {
        this.cashier = cashier;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}