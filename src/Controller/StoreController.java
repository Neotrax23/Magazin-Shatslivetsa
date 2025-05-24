package Controller;

import Model.*;

import java.util.List;

public class StoreController {
    private Store store;
    public Store getStore() {
        return this.store;
    }
    public StoreController(Store store) {
        this.store = store;
    }

    public void addProduct(Product product) {
        store.addProduct(product);
    }

    public List<Product> getProducts() {
        return store.getInventory();
    }

    public void addCashier(Cashier cashier) {
        store.addCashier(cashier);
    }

    public List<Cashier> getCashiers() {
        return store.getCashiers();
    }

    public Receipt createReceipt(Cashier cashier) {
        return store.createReceipt(cashier);
    }

    public void addProductToReceipt(Receipt receipt, Product product, int quantity) throws InsufficientQuantityException {
        receipt.addProduct(product, quantity, store);
    }

    public double calculateTotalRevenue() {
        return Receipt.getTotalRevenue();
    }

    public double calculateTotalExpenses() {
        double salaryExpenses = store.getCashiers().stream().mapToDouble(Cashier::getMonthlySalary).sum();
        double deliveryExpenses = store.getInventory().stream().mapToDouble(p -> p.getDeliveryPrice() * p.getQuantity()).sum();
        return salaryExpenses + deliveryExpenses;
    }

    public double calculateProfit() {
        return calculateTotalRevenue() - calculateTotalExpenses();
    }

    public int getTotalReceipts() {
        return Receipt.getTotalReceipts();
    }
}