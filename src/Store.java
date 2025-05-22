import java.util.ArrayList;
import java.util.List;

public class Store {
    private List<Product> inventory;
    private List<Cashier> cashiers;
    private List<Receipt> receipts;
    private double foodMarkup;
    private double nonFoodMarkup;
    private double expirationDiscount;
    private int daysBeforeExpiration;

    public Store(double foodMarkup, double nonFoodMarkup, double expirationDiscount, int daysBeforeExpiration) {
        this.inventory = new ArrayList<>();
        this.cashiers = new ArrayList<>();
        this.receipts = new ArrayList<>();
        this.foodMarkup = foodMarkup;
        this.nonFoodMarkup = nonFoodMarkup;
        this.expirationDiscount = expirationDiscount;
        this.daysBeforeExpiration = daysBeforeExpiration;
    }

    public void addProduct(Product product) {
        inventory.add(product);
    }

    public void addCashier(Cashier cashier) {
        cashiers.add(cashier);
    }

    public Receipt createReceipt(Cashier cashier) {
        Receipt receipt = new Receipt(cashier);
        receipts.add(receipt);
        return receipt;
    }

    public double calculateTotalExpenses() {
        double salaryExpenses = cashiers.stream().mapToDouble(Cashier::getMonthlySalary).sum();
        double deliveryExpenses = inventory.stream().mapToDouble(p -> p.getDeliveryPrice() * p.getQuantity()).sum();
        return salaryExpenses + deliveryExpenses;
    }

    public double calculateTotalRevenue() {
        return Receipt.getTotalRevenue();
    }

    public double calculateProfit() {
        return calculateTotalRevenue() - calculateTotalExpenses();
    }

    // Getters and setters
    public List<Product> getInventory() {
        return inventory;
    }

    public void setInventory(List<Product> inventory) {
        this.inventory = inventory;
    }

    public List<Cashier> getCashiers() {
        return cashiers;
    }

    public void setCashiers(List<Cashier> cashiers) {
        this.cashiers = cashiers;
    }

    public List<Receipt> getReceipts() {
        return receipts;
    }

    public void setReceipts(List<Receipt> receipts) {
        this.receipts = receipts;
    }

    public double getFoodMarkup() {
        return foodMarkup;
    }

    public void setFoodMarkup(double foodMarkup) {
        this.foodMarkup = foodMarkup;
    }

    public double getNonFoodMarkup() {
        return nonFoodMarkup;
    }

    public void setNonFoodMarkup(double nonFoodMarkup) {
        this.nonFoodMarkup = nonFoodMarkup;
    }

    public double getExpirationDiscount() {
        return expirationDiscount;
    }

    public void setExpirationDiscount(double expirationDiscount) {
        this.expirationDiscount = expirationDiscount;
    }

    public int getDaysBeforeExpiration() {
        return daysBeforeExpiration;
    }

    public void setDaysBeforeExpiration(int daysBeforeExpiration) {
        this.daysBeforeExpiration = daysBeforeExpiration;
    }
}