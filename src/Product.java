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
}