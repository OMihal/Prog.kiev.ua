import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Structure {
    public static void create(Connection conn) throws SQLException {
        destroy(conn);
        try (Statement st = conn.createStatement()) {
            createProduct(st);
            createCustomer(st);
            createOrders(st);
            createProductOrder(st);
        }
    }

    public static void destroy(Connection conn) throws SQLException {
        try (Statement st = conn.createStatement()) {
            destroyProductOrder(st);
            destroyOrders(st);
            destroyCustomer(st);
            destroyProduct(st);
        }
    }

    private static void createProduct(Statement st) throws SQLException {
        st.execute("CREATE TABLE product (" +
                "id INT NOT NULL AUTO_INCREMENT," +
                "category INT NOT NULL," +
                "part_number INT NOT NULL," +
                "name VARCHAR(100) NOT NULL," +
                "price DOUBLE," +

                "PRIMARY KEY(id)," +
                "UNIQUE KEY (category, part_number))"
        );
    }

    private static void destroyProduct(Statement st) throws SQLException {
        st.execute("DROP TABLE IF EXISTS product");
    }

    private static void createCustomer(Statement st) throws SQLException {
        st.execute("CREATE TABLE customer (" +
                "id INT NOT NULL AUTO_INCREMENT," +
                "tax_number VARCHAR(10) NOT NULL," +
                "name VARCHAR(100) NOT NULL," +
                "PRIMARY KEY (id)," +
                "UNIQUE KEY (tax_number))"
        );
    }

    private static void destroyCustomer(Statement st) throws SQLException {
        st.execute("DROP TABLE IF EXISTS customer");
    }

    private static void createOrders(Statement st) throws SQLException {
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
    }

    private static void destroyOrders(Statement st) throws SQLException {
        st.execute("DROP TABLE IF EXISTS orders");
    }

    private static void createProductOrder(Statement st) throws SQLException {
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

    private static void destroyProductOrder(Statement st) throws SQLException {
        st.execute("DROP TABLE IF EXISTS product_order");
    }
}
