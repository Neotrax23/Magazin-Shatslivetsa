package Controller;

import Model.*;
import java.util.Map;

public class ReceiptController {
    private Store store;

    public ReceiptController(Store store) {
        this.store = store;
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

    public int getTotalReceipts() {
        return Receipt.getTotalReceipts();
    }

    public Map<Product, Integer> getReceiptProducts(Receipt receipt) {
        return receipt.getProducts();
    }

    public Store getStore() {
        return store;
    }
}