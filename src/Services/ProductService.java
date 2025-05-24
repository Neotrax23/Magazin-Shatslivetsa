package Services;

import Model.Product;
import Model.Store;

import java.util.List;

public class ProductService {
    private Store store;

    public ProductService(Store store) {
        this.store = store;
    }

    public void addProduct(Product product) {
        store.addProduct(product);
    }

    public List<Product> getProducts() {
        return store.getInventory();
    }
}