import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer {
    private int id;
    private String taxNumber;
    private String name;

    private Customer(int id, String taxNumber, String name) {
        this.id = id;
        this.taxNumber = taxNumber;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "[id = " + id + ", tax = " + taxNumber + ", name = " + name + "]";
    }

    public static Customer create(Connection conn, String taxNumber, String name) throws SQLException {
        String sql = "INSERT INTO customer (tax_number, name) VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, taxNumber);
            ps.setString(2, name);
            ps.executeUpdate();
        }
        return getByTaxNumber(conn, taxNumber);
    }

    public static Customer getById(Connection conn, int customerId)
            throws SQLException {
        String sql = "SELECT id, tax_number, name FROM customer WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    return new Customer(rs.getInt(1), rs.getString(2), rs.getString(3));
                }
            }
        }
        return null;
    }

    public static Customer getByTaxNumber(Connection conn, String taxNumber)
            throws SQLException {
        String sql = "SELECT id, tax_number, name FROM customer WHERE tax_number = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, taxNumber);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    return new Customer(rs.getInt(1), rs.getString(2), rs.getString(3));
                }
            }
        }
        return null;
    }
}
