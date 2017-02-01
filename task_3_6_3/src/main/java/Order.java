import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class Order {
    private int id;
    private String number;
    private Customer customer;
    private Collection<ProductQuantity> products;

    public Order(int id, String number, Customer customer, Collection<ProductQuantity> products) {
        this.id = id;
        this.number = number;
        this.customer = customer;
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Collection<ProductQuantity> getProducts() {
        return products;
    }

    public double getOrderPrice() {
        double sum = 0;
        if (products != null) {
            for (ProductQuantity s : products) {
                sum += s.getTotalPrice();
            }
        }
        return sum;
    }

    public static Order create(Connection conn, String number, Customer customer, Collection<ProductQuantity> products)
            throws SQLException {

        conn.setAutoCommit(false);
        try {
            // insert blank order
            String sql = "INSERT INTO orders (order_number, customer_id) VALUES (?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, number);
                ps.setInt(2, customer.getId());
                ps.executeUpdate();
            }
            // get blank order id
            int orderId = getOrderId(conn, number);

            // insert order and products
            sql = "INSERT INTO product_order (order_id, product_id, product_quantity) VALUES(?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                for (ProductQuantity pq : products) {
                    ps.setInt(1, orderId);
                    ps.setInt(2, pq.getProduct().getId());
                    ps.setInt(3, pq.getQuantity());
                    ps.executeUpdate();
                }
            }
            conn.commit();
        } catch (SQLException ex) {
            conn.rollback();
            throw ex;
        }
        return getByNumber(conn, number);
    }

    public static Order getByNumber(Connection conn, String orderNumber) throws SQLException {
        // get order id
        int orderId = getOrderId(conn, orderNumber);
        // get customer
        int customerId = getCustomerByOrderId(conn, orderId);
        Customer customer = Customer.getById(conn, customerId);
        // get products
        Collection<ProductQuantity> products = ProductQuantity.getProductsByOrderId(conn, orderId);

        return new Order(orderId, orderNumber, customer, products);
    }

    private static int getOrderId(Connection conn, String orderNumber) throws SQLException {
        String sql = "SELECT id FROM orders WHERE order_number = ?";
        int orderId = -1;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, orderNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    orderId = rs.getInt(1);
                } else {
                    throw new SQLException(orderNumber + " - order not found!");
                }
            }
        }
        return orderId;
    }

    private static int getCustomerByOrderId(Connection conn, int orderId) throws SQLException {
        String sql = "SELECT customer_id FROM orders WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new SQLException("order not found!");
                }
            }
        }
    }
}
