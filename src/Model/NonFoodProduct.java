package Model;
import java.time.LocalDate;

public class NonFoodProduct extends Product {
    public NonFoodProduct(int id, String name, double deliveryPrice, LocalDate expirationDate, int quantity) {
        super(id, name, deliveryPrice, expirationDate, quantity);
    }

    @Override
    public double calculateSellingPrice(Store store) {
        if (isExpired()) return 0;
        double basePrice = getDeliveryPrice() * (1 + store.getNonFoodMarkup());
        return isAboutToExpire(store) ? basePrice * (1 - store.getExpirationDiscount()) : basePrice;
    }
}