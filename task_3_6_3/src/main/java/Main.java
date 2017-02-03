import dao.*;
import domain.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);

            try (Connection con = daoFactory.getConnection()) {
                StructureDAO structureDAO = daoFactory.getStructureDAO(con);
                structureDAO.createStructure();

                // create new customer
                CustomerDAO customerDAO = daoFactory.getCustomerDAO(con);
                int customerId = customerDAO.insertCustomer("2464569871", "Petrov Ivan");
                Customer customer = customerDAO.findCustomer(customerId);

                // add new products
                ProductDAO productDAO = daoFactory.getProductDAO(con);
                Product[] products = new Product[]{
                        productDAO.findProduct(productDAO.insertProduct(Category.FOOD, 1, "Tomatto", 2.50)),
                        productDAO.findProduct(productDAO.insertProduct(Category.SHOES, 345, "Boots", 47.80)),
                        productDAO.findProduct(productDAO.insertProduct(Category.WEAR, 25, "Raincoat", 100)),
                        productDAO.findProduct(productDAO.insertProduct(Category.ELECTRONICS, 45678399, "Samsung TV", 800)),
                        productDAO.findProduct(productDAO.insertProduct(Category.AUTO_PARTS, 123456789, "VW TDI 2.0 Engine", 4000))
                };
                System.out.println("Products inserted:");
                for (Product product : products) System.out.println(product);

                // set quantity of products
                List<ProductQuantity> productList = new ArrayList<>();
                productList.add(new ProductQuantity(products[0], 10));
                productList.add(new ProductQuantity(products[1], 1));
                productList.add(new ProductQuantity(products[2], 2));
                productList.add(new ProductQuantity(products[3], 50));

                // create order
                int orderId;
                OrderDAO orderDAO = daoFactory.getOrderDAO(con);
                con.setAutoCommit(false);
                try {
                    orderId = orderDAO.insertOrder("20170201/587", customer, productList);
                    con.commit();
                    System.out.println("domain.Order created with id = " + orderId);
                } catch (SQLException ex) {
                    con.rollback();
                    throw ex;
                }
                // get order from DB
                Order order = orderDAO.findOrder(orderId, customerDAO, productDAO);
                System.out.println("OrderNo = " + order.getNumber());
                System.out.println("domain.Customer = " + order.getCustomer());
                System.out.println("domain.Product positions = " + order.getProducts().size());
                for (ProductQuantity pq : order.getProducts()) System.out.println(pq);
                System.out.println("Total price = " + order.getOrderPrice());

                structureDAO.destroyStructure();
                System.out.println("DB structure destroyed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
