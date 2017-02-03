package market.db.mysql;

import market.Product;
import market.db.ProductDAO;

import java.sql.*;

class MySqlProductDAO implements ProductDAO {
    private Connection connection;

    public MySqlProductDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int insertProduct(int category, int partNumber, String name, double price) throws SQLException {
        String sql = "INSERT INTO product (category, part_number, name, price) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, category);
            ps.setInt(2, partNumber);
            ps.setString(3, name);
            ps.setDouble(4, price);
            ps.executeUpdate();
        }
        return get(category, partNumber);
    }
    @Override
    public Product findProduct(int id) throws SQLException  {
        String sql = "SELECT id, category, part_number, name, price FROM product WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Product(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getDouble(5));
                }
            }
        }
        return null;
    }

    private int get(int category, int partNumber) throws SQLException {
        String sql = "SELECT id FROM product WHERE category = ? AND part_number = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, category);
            ps.setInt(2, partNumber);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    private static Product get(Connection conn, int productId) throws SQLException {
        String sql = "SELECT id, category, part_number, name, price FROM product WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Product(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getDouble(5));
                }
            }
        }
        return null;
    }
    static void createTable(Connection connection) throws SQLException {
        try (Statement st = connection.createStatement()) {
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
    }
    static void destroyTable(Connection connection) throws SQLException {
        try (Statement st = connection.createStatement()) {
            st.execute("DROP TABLE IF EXISTS product");
        }
    }
}
