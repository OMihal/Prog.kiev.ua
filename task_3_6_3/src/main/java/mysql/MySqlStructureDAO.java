package mysql;

import dao.StructureDAO;

import java.sql.Connection;
import java.sql.SQLException;

class MySqlStructureDAO implements StructureDAO {
    private Connection connection;

    public MySqlStructureDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createStructure()  throws SQLException {

        destroyStructure();

        MySqlProductDAO.createTable(connection);
        MySqlCustomerDAO.createTable(connection);
        MySqlOrderDAO.createTable(connection);
    }

    @Override
    public void destroyStructure() throws SQLException{
        MySqlOrderDAO.destroyTable(connection);
        MySqlCustomerDAO.destroyTable(connection);
        MySqlProductDAO.destroyTable(connection);
    }
}
