import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    public static Collection<ProductQuantity> getProductsByOrderId(Connection conn, int orderId) throws SQLException {
        List<ProductQuantity> products = new ArrayList<>();

        String sql = "SELECT product_id, product_quantity FROM product_order WHERE order_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int productId = rs.getInt(1);
                    Product product = Product.get(conn, productId);
                    products.add(new ProductQuantity(product, rs.getInt(2)));
                }
            }
        }
        return products;
    }
}
