package Services;

import Model.*;

public class ReceiptService {
    private Store store;

    public ReceiptService(Store store) {
        this.store = store;
    }

    public Receipt createReceipt(Cashier cashier) {
        return store.createReceipt(cashier);
    }

    public void addProductToReceipt(Receipt receipt, Product product, int quantity) throws InsufficientQuantityException {
        receipt.addProduct(product, quantity, store);
    }
}