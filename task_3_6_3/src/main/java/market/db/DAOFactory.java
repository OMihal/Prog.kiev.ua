package market.db;

import market.db.mysql.MySqlDAOFactory;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DAOFactory {
    public static final int MYSQL = 1;
//    public static final int ORACLE = 2;
//    public static final int MSSQL = 3;

    public abstract StructureDAO getStructureDAO(Connection con);

    public abstract ProductDAO getProductDAO(Connection con);

    public abstract CustomerDAO getCustomerDAO(Connection con);

    public abstract OrderDAO getOrderDAO(Connection con);

    public abstract Connection getConnection() throws SQLException;

    public static DAOFactory getDAOFactory(int whichFactory) {
        switch (whichFactory) {
            case MYSQL:
                return new MySqlDAOFactory();
            default:
                throw new RuntimeException("Unsupported DAO-factory");
        }
    }
}
