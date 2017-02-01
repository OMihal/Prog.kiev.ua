import java.sql.*;
import java.util.Collection;

public class Product {
    private int id;
    private int category;
    private int partNumber;
    private String name;
    private double price;

    private Product(int id, int category, int partNumber, String name, double price) {
        this.id = id;
        this.category = category;
        this.partNumber = partNumber;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public int getCategory() {
        return category;
    }

    public int getPartNumber() {
        return partNumber;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "[id = " + id + ", category = " + category + ", name = " + name + ", price = " + price + "]";
    }

    public static Product create(Connection conn, int category, int partNumber, String name, double price)
            throws SQLException {
        String sql = "INSERT INTO product (category, part_number, name, price) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, category);
            ps.setInt(2, partNumber);
            ps.setString(3, name);
            ps.setDouble(4, price);
            ps.executeUpdate();
        }
        return get(conn, category, partNumber);
    }

    public static Product get(Connection conn, int category, int partNumber)
            throws SQLException {
        String sql = "SELECT id, category, part_number, name, price FROM product WHERE category = ? AND part_number = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, category);
            ps.setInt(2, partNumber);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    return new Product(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getDouble(5));
                }
            }
        }
        return null;
    }

    public static Product get(Connection conn, int productId)
            throws SQLException {
        String sql = "SELECT id, category, part_number, name, price FROM product WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    return new Product(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getDouble(5));
                }
            }
        }
        return null;
    }
}
