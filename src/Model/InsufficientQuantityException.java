package Model;

public class InsufficientQuantityException extends Exception {
    private Product product;
    private int missingQuantity;

    public InsufficientQuantityException(Product product, int missingQuantity) {
        super("Insufficient quantity for product: " + product.getName() + ". Missing: " + missingQuantity + " units.");
        this.product = product;
        this.missingQuantity = missingQuantity;
    }

    public Product getProduct() { return product; }
    public int getMissingQuantity() { return missingQuantity; }
}