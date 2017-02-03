package mysql;

import domain.Customer;
import domain.Order;
import domain.Product;
import domain.ProductQuantity;
import dao.CustomerDAO;
import dao.OrderDAO;
import dao.ProductDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class MySqlOrderDAO implements OrderDAO {
    private Connection connection;

    public MySqlOrderDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int insertOrder(String orderNumber, Customer customer, Collection<ProductQuantity> products) throws SQLException {
        // insert blank order
        String sql = "INSERT INTO orders (order_number, customer_id) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, orderNumber);
            ps.setInt(2, customer.getId());
            ps.executeUpdate();
        }
        // get blank order id
        int orderId = getOrderId(orderNumber);

        // insert order and products
        sql = "INSERT INTO product_order (order_id, product_id, product_quantity) VALUES(?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (ProductQuantity pq : products) {
                ps.setInt(1, orderId);
                ps.setInt(2, pq.getProduct().getId());
                ps.setInt(3, pq.getQuantity());
                ps.executeUpdate();
            }
        }
        return orderId;
    }

    @Override
    public Order findOrder(int orderId, CustomerDAO customerDAO, ProductDAO productDAO) throws SQLException {
        String sql = "SELECT order_number, customer_id  FROM orders WHERE id = ?";

        String orderNumber;
        int customerId;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    orderNumber = rs.getString(1);
                    customerId = rs.getInt(2);
                } else {
                    throw new SQLException("order not found");
                }
            }
        }
        // get customer
        Customer customer = customerDAO.findCustomer(customerId);
        // get products
        Collection<ProductQuantity> products = getProductsByOrderId(orderId, productDAO);

        return new Order(orderId, orderNumber, customer, products);
    }

    private int getOrderId(String orderNumber) throws SQLException {
        String sql = "SELECT id FROM orders WHERE order_number = ?";
        int orderId = -1;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, orderNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    orderId = rs.getInt(1);
                }
            }
        }
        return orderId;
    }

    public Collection<ProductQuantity> getProductsByOrderId(int orderId, ProductDAO producrDAO) throws SQLException {
        List<ProductQuantity> products = new ArrayList<>();

        String sql = "SELECT product_id, product_quantity FROM product_order WHERE order_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int productId = rs.getInt(1);
                    Product product = producrDAO.findProduct(productId);
                    products.add(new ProductQuantity(product, rs.getInt(2)));
                }
            }
        }
        return products;
    }

    static void createTable(Connection connection) throws SQLException {
        try (Statement st = connection.createStatement()) {
            st.execute("CREATE TABLE orders (" +
                    "id INT NOT NULL AUTO_INCREMENT," +
                    "order_number VARCHAR(50) NOT NULL," +
                    "customer_id INT NOT NULL," +

                    "PRIMARY KEY(id)," +
                    "UNIQUE KEY(order_number)," +
                    "INDEX (customer_id)," +

                    "FOREIGN KEY (customer_id) " +
                    "REFERENCES customer(id))"
            );

            st.execute("CREATE TABLE product_order (" +
                    "id INT NOT NULL AUTO_INCREMENT," +
                    "order_id INT NOT NULL," +
                    "product_id INT NOT NULL," +
                    "product_quantity INT NOT NULL," +

                    "PRIMARY KEY(id)," +
                    "UNIQUE KEY (order_id, product_id)," +

                    "FOREIGN KEY (order_id) " +
                    "REFERENCES orders(id)," +

                    "FOREIGN KEY (product_id) " +
                    "REFERENCES product(id))"
            );
        }
    }
    static void destroyTable(Connection connection) throws SQLException {
        try (Statement st = connection.createStatement()) {
            st.execute("DROP TABLE IF EXISTS product_order");
            st.execute("DROP TABLE IF EXISTS orders");
        }
    }

}
