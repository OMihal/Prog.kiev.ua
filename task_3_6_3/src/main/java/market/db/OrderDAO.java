package market.db;

import market.Customer;
import market.Order;
import market.ProductQuantity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public interface OrderDAO {
    int insertOrder(String number, Customer customer, Collection<ProductQuantity> products) throws SQLException;
    Order findOrder(int id, CustomerDAO customerDAO, ProductDAO productDAO)  throws SQLException;
}
