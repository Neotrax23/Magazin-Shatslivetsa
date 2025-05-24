package Model;

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

    // Other methods and getters/setters remain the same
    public List<Product> getInventory() { return inventory; }
    public List<Cashier> getCashiers() { return cashiers; }
    public double getFoodMarkup() { return foodMarkup; }
    public double getNonFoodMarkup() { return nonFoodMarkup; }
    public double getExpirationDiscount() { return expirationDiscount; }
    public int getDaysBeforeExpiration() { return daysBeforeExpiration; }
}