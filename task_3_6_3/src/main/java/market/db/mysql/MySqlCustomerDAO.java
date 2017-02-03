package market.db.mysql;

import market.Customer;
import market.db.CustomerDAO;

import java.sql.*;

class MySqlCustomerDAO implements CustomerDAO {
    private Connection connection;

    public MySqlCustomerDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int insertCustomer(String taxNumber, String name) throws SQLException {
        String sql = "INSERT INTO customer (tax_number, name) VALUES (?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, taxNumber);
            ps.setString(2, name);
            ps.executeUpdate();
        }
        return getByTaxNumber(taxNumber);
    }

    @Override
    public Customer findCustomer(int id) throws SQLException {
        String sql = "SELECT id, tax_number, name FROM customer WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    return new Customer(rs.getInt(1), rs.getString(2), rs.getString(3));
                }
            }
        }
        return null;
    }

    private int getByTaxNumber(String taxNumber) throws SQLException {
        String sql = "SELECT id FROM customer WHERE tax_number = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, taxNumber);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    static void createTable(Connection connection) throws SQLException {
        try (Statement st = connection.createStatement()) {
            st.execute("CREATE TABLE customer (" +
                    "id INT NOT NULL AUTO_INCREMENT," +
                    "tax_number VARCHAR(10) NOT NULL," +
                    "name VARCHAR(100) NOT NULL," +
                    "PRIMARY KEY (id)," +
                    "UNIQUE KEY (tax_number))"
            );
        }
    }

    static void destroyTable(Connection connection) throws SQLException {
        try (Statement st = connection.createStatement()) {
            st.execute("DROP TABLE IF EXISTS customer");
        }
    }
}
