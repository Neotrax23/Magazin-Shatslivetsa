package Model;

import java.time.LocalDate;

public abstract class Product {
    private int id;
    private String name;
    private double deliveryPrice;
    private LocalDate expirationDate;
    private int quantity;

    public Product(int id, String name, double deliveryPrice, LocalDate expirationDate, int quantity) {
        this.id = id;
        this.name = name;
        this.deliveryPrice = deliveryPrice;
        this.expirationDate = expirationDate;
        this.quantity = quantity;
    }

    public abstract double calculateSellingPrice(Store store);

    public int getId() { return id; }
    public String getName() { return name; }
    public double getDeliveryPrice() { return deliveryPrice; }
    public LocalDate getExpirationDate() { return expirationDate; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public boolean isExpired() {
        return LocalDate.now().isAfter(expirationDate);
    }

    public boolean isAboutToExpire(Store store) {
        return LocalDate.now().plusDays(store.getDaysBeforeExpiration()).isAfter(expirationDate);
    }
}