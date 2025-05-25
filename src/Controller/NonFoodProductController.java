package Controller;

import Model.NonFoodProduct;
import Model.Product;
import Model.Store;
import java.time.LocalDate;

public class NonFoodProductController extends ProductController {
    public NonFoodProductController(Store store) {
        super(store);
    }

    public Product createNonFoodProduct(int id, String name, double price, LocalDate expiration, int quantity) {
        return new NonFoodProduct(id, name, price, expiration, quantity);
    }
}