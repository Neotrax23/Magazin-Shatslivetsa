package Controller;

import Model.Product;
import Model.Store;
import java.util.List;

public class ProductController {
    private Store store;

    public ProductController(Store store) {
        this.store = store;
    }

    public void addProduct(Product product) {
        store.addProduct(product);
    }

    public List<Product> getProducts() {
        return store.getInventory();
    }

    public Product getProductById(int id) {
        return store.getInventory().stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Store getStore() {
        return store;
    }
}