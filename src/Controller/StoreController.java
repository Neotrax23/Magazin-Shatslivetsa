package Controller;

import Model.*;
import java.util.List;

public class StoreController {
    private Store store;
    private ProductController productController;
    private FoodProductController foodProductController;
    private NonFoodProductController nonFoodProductController;
    private CashierController cashierController;
    private ReceiptController receiptController;

    public StoreController(Store store) {
        this.store = store;
        this.productController = new ProductController(store);
        this.foodProductController = new FoodProductController(store);
        this.nonFoodProductController = new NonFoodProductController(store);
        this.cashierController = new CashierController(store);
        this.receiptController = new ReceiptController(store);
    }

    public void addProduct(Product product) {
        productController.addProduct(product);
    }

    public List<Product> getAllProducts() {
        return productController.getProducts();
    }

    public Product getProductById(int id) {
        return productController.getProductById(id);
    }

    public void addCashier(Cashier cashier) {
        cashierController.addCashier(cashier);
    }

    public List<Cashier> getAllCashiers() {
        return cashierController.getCashiers();
    }

    public Cashier getCashierById(int id) {
        return cashierController.getCashierById(id);
    }

    public Receipt createReceipt(Cashier cashier) {
        return receiptController.createReceipt(cashier);
    }

    public void addProductToReceipt(Receipt receipt, Product product, int quantity) throws InsufficientQuantityException {
        receiptController.addProductToReceipt(receipt, product, quantity);
    }

    public double calculateTotalRevenue() {
        return receiptController.calculateTotalRevenue();
    }

    public double calculateTotalExpenses() {
        double salaryExpenses = cashierController.getCashiers().stream()
                .mapToDouble(Cashier::getMonthlySalary)
                .sum();
        double deliveryExpenses = productController.getProducts().stream()
                .mapToDouble(p -> p.getDeliveryPrice() * p.getQuantity())
                .sum();
        return salaryExpenses + deliveryExpenses;
    }

    public double calculateProfit() {
        return calculateTotalRevenue() - calculateTotalExpenses();
    }

    public int getTotalReceiptsIssued() {
        return receiptController.getTotalReceipts();
    }

    public Store getStore() {
        return store;
    }
}