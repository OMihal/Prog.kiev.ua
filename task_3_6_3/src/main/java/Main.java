import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            DbProperties props = new DbProperties();

            try (Connection conn = DriverManager.getConnection(props.getUrl(), props.getUser(), props.getPassword())) {

                createStructure(conn);
                insertData(conn);
                deleteStructure(conn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createStructure(Connection conn) throws SQLException {
        try (Statement st = conn.createStatement()) {
            st.execute("DROP TABLE IF EXISTS links");
            st.execute("DROP TABLE IF EXISTS orders");
            st.execute("DROP TABLE IF EXISTS customers");
            st.execute("DROP TABLE IF EXISTS products");

            // products
            st.execute("CREATE TABLE products (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,  name VARCHAR(100) NOT NULL, " +
                    "description VARCHAR(255) NOT NULL, price DOUBLE NOT NULL)");

            // customers
            st.execute("CREATE TABLE customers (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,  name VARCHAR(100) NOT NULL, " +
                    "address VARCHAR(100) NOT NULL)");

            // orders
            st.execute("CREATE TABLE orders (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, date DATE NOT NULL, customer INT NOT NULL)");

            // links
            st.execute("CREATE TABLE links (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, orderId INT NOT NULL, productId INT NOT NULL)");
        }
        System.out.println("Tables created");
    }

    private

//    private static void insertData(Connection conn) throws SQLException {
//        FlatCollection col = FlatCollection.getInstance();
//        Collection<Flat> flats = col.getFlats();
//
//        String sql = "INSERT INTO flats (region, address, square, rooms, price) VALUES (?, ?, ?, ?, ?)";
//
//        conn.setAutoCommit(false);
//        try {
//            try (PreparedStatement ps = conn.prepareStatement(sql)) {
//                for (Flat f : flats) {
//                    ps.setString(1, f.getRegion());
//                    ps.setString(2, f.getAddress());
//                    ps.setDouble(3, f.getSquare());
//                    ps.setInt(4, f.getRooms());
//                    ps.setDouble(5, f.getPrice());
//                    ps.executeUpdate();
//                }
//            }
//            conn.commit();
//            conn.setAutoCommit(true);
//            System.out.println("Created " + flats.size() + " records in database!");
//        } catch (SQLException ex) {
//            conn.rollback();
//            throw ex;
//        }
//    }

//    private static Collection<Flat> getByParameters(Connection conn, String region, Integer rooms, Double minPrice,
//                                                    Double maxPrice) throws SQLException {
//        String sql = "Select region, address, square, rooms, price from flats where 1 = 1";
//
//        if (region != null)
//            sql += " and region = '" + region + "'";
//
//        if (rooms != null)
//            sql += " and rooms = " + rooms;
//
//        if (minPrice != null)
//            sql += " and price >= " + minPrice;
//
//        if (maxPrice != null)
//            sql += " and price <= " + maxPrice;
//
//        List<Flat> flats = new ArrayList<>();
//        try (PreparedStatement ps = conn.prepareStatement(sql)) {
//            try (ResultSet rs = ps.executeQuery()) {
//                ResultSetMetaData md = rs.getMetaData();
//
//                while (rs.next()) {
//                    flats.add(new Flat(rs.getString(1), rs.getString(2), rs.getDouble(3), rs.getInt(4), rs.getDouble(5)));
//                }
//            }
//        }
//        return flats;
//    }

    private static void deleteStructure(Connection conn) throws SQLException {
        try (Statement st = conn.createStatement()) {
            st.execute("DROP TABLE IF EXISTS links");
            st.execute("DROP TABLE IF EXISTS orders");
            st.execute("DROP TABLE IF EXISTS customers");
            st.execute("DROP TABLE IF EXISTS products");
        }
        System.out.println("Tables droped");
    }
}
