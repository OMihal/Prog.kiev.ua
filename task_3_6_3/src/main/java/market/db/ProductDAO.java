package market.db;

import market.Product;

import java.sql.SQLException;

public interface ProductDAO {
    int insertProduct(int category, int partNumber, String name, double price) throws SQLException;
    Product findProduct(int id) throws SQLException ;
}
