package market;

public class ProductQuantity {
    private Product product;
    private int quantity;

    public ProductQuantity(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return "[" + product + ", quantity = " + quantity + ", Sum = " + getTotalPrice() + "]";
    }
}
