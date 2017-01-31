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

                System.out.println("=====  Flats in Podilsky region");
                Collection<Flat> founded = getByParameters(conn, "Podilsky", null, null, null);
                printResults(founded);

                System.out.println("=====  3-rooms flats in Podilsky region");
                founded = getByParameters(conn, "Podilsky", 3, null, null);
                printResults(founded);

                System.out.println("=====  2-rooms flats");
                founded = getByParameters(conn, null, 2, null, null);
                printResults(founded);

                System.out.println("=====  Flats with 50K price and above");
                founded = getByParameters(conn, null, null, new Double(50000), null);
                printResults(founded);

                System.out.println("=====  Flats with 100-200K price");
                founded = getByParameters(conn, null, null, new Double(100000), new Double(200000));
                printResults(founded);

                deleteStructure(conn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createStructure(Connection conn) throws SQLException {
        try (Statement st = conn.createStatement()) {
            st.execute("DROP TABLE IF EXISTS flats");
            st.execute("CREATE TABLE flats (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,  region VARCHAR(100) NOT NULL, " +
                    "address VARCHAR(100) NOT NULL, square DOUBLE NOT NULL, rooms INT NOT NULL, price DOUBLE NOT NULL)");
        }
        System.out.println("Table created");
    }

    private static void insertData(Connection conn) throws SQLException {
        FlatCollection col = FlatCollection.getInstance();
        Collection<Flat> flats = col.getFlats();

        String sql = "INSERT INTO flats (region, address, square, rooms, price) VALUES (?, ?, ?, ?, ?)";

        conn.setAutoCommit(false);
        try {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                for (Flat f : flats) {
                    ps.setString(1, f.getRegion());
                    ps.setString(2, f.getAddress());
                    ps.setDouble(3, f.getSquare());
                    ps.setInt(4, f.getRooms());
                    ps.setDouble(5, f.getPrice());
                    ps.executeUpdate();
                }
            }
            conn.commit();
            conn.setAutoCommit(true);
            System.out.println("Created " + flats.size() + " records in database!");
        } catch (SQLException ex) {
            conn.rollback();
            throw ex;
        }
    }

    private static void printResults(Collection<Flat> flats) {
        if (flats.isEmpty()) {
            System.out.println("Empty set!");
        } else {
            for (Flat f : flats) System.out.println(f);
        }
    }

    private static Collection<Flat> getByParameters(Connection conn, String region, Integer rooms, Double minPrice,
                                                    Double maxPrice) throws SQLException {
        String sql = "SELECT region, address, square, rooms, price FROM flats WHERE 1 = 1";

        if (region != null)
            sql += " AND region = '" + region + "'";

        if (rooms != null)
            sql += " AND rooms = " + rooms;

        if (minPrice != null)
            sql += " AND price >= " + minPrice;

        if (maxPrice != null)
            sql += " AND price <= " + maxPrice;

        List<Flat> flats = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                ResultSetMetaData md = rs.getMetaData();

                while (rs.next()) {
                    flats.add(new Flat(rs.getString(1), rs.getString(2), rs.getDouble(3), rs.getInt(4), rs.getDouble(5)));
                }
            }
        }
        return flats;
    }

    private static void deleteStructure(Connection conn) throws SQLException {
        try (Statement st = conn.createStatement()) {
            st.execute("DROP TABLE IF EXISTS flats");
        }
        System.out.println("Table droped");
    }
}
