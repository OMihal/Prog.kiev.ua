package market.db.mysql;

import market.db.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlDAOFactory extends DAOFactory {

    private static DbProperties props = new DbProperties();

    public Connection getConnection() throws SQLException{
        return DriverManager.getConnection(props.getUrl(), props.getUser(), props.getPassword());
    }

    @Override
    public StructureDAO getStructureDAO(Connection connection) {
        return new MySqlStructureDAO(connection);
    }

    @Override
    public ProductDAO getProductDAO(Connection connection) {
        return new MySqlProductDAO(connection);
    }

    @Override
    public CustomerDAO getCustomerDAO(Connection connection) {
        return new MySqlCustomerDAO(connection);
    }

    @Override
    public OrderDAO getOrderDAO(Connection connection) {
        return new MySqlOrderDAO(connection);
    }

}
