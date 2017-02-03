package dao;

import domain.Customer;
import domain.Order;
import domain.ProductQuantity;

import java.sql.SQLException;
import java.util.Collection;

public interface OrderDAO {
    int insertOrder(String number, Customer customer, Collection<ProductQuantity> products) throws SQLException;
    Order findOrder(int id, CustomerDAO customerDAO, ProductDAO productDAO)  throws SQLException;
}
