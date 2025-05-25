package Controller;

import Model.FoodProduct;
import Model.Product;
import Model.Store;
import java.time.LocalDate;

public class FoodProductController extends ProductController {
    public FoodProductController(Store store) {
        super(store);
    }

    public Product createFoodProduct(int id, String name, double price, LocalDate expiration, int quantity) {
        return new FoodProduct(id, name, price, expiration, quantity);
    }
}