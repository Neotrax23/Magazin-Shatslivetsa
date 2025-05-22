import java.time.LocalDate;

public class FoodProduct extends Product {
    public FoodProduct(int id, String name, double deliveryPrice, LocalDate expirationDate, int quantity) {
        super(id, name, deliveryPrice, expirationDate, quantity);
    }

    @Override
    public double calculateSellingPrice(Store store) {
        if (isExpired()) return 0;

        double basePrice = getDeliveryPrice() * (1 + store.getFoodMarkup());
        return isAboutToExpire(store) ? basePrice * (1 - store.getExpirationDiscount()) : basePrice;
    }
}