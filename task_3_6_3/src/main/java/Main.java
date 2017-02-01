import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            DbProperties props = new DbProperties();

            try (Connection conn = DriverManager.getConnection(props.getUrl(), props.getUser(), props.getPassword())) {

                Structure.create(conn);
                System.out.println("DB structure created");

                // create new customer
                Customer customer = Customer.create(conn, "2464569871", "Petrov Ivan");
                System.out.println("Created customer:\n" + customer);

                // add new products
                Product[] products = new Product[]{
                        Product.create(conn, Category.FOOD, 1, "Tomatto", 2.50),
                        Product.create(conn, Category.SHOES, 345, "Boots", 47.80),
                        Product.create(conn, Category.WEAR, 25, "Raincoat", 100),
                        Product.create(conn, Category.ELECTRONICS, 45678399, "Samsung TV", 800),
                        Product.create(conn, Category.AUTO_PARTS, 123456789, "VW TDI 2.0 Engine", 4000),
                };
                System.out.println("Products inserted:");
                for (Product p : products) System.out.println(p);

                // set quantity of products
                List<ProductQuantity> productList = new ArrayList<>();
                productList.add(new ProductQuantity(products[0], 10));
                productList.add(new ProductQuantity(products[1], 1));
                productList.add(new ProductQuantity(products[2], 2));
                productList.add(new ProductQuantity(products[3], 50));

                // create order
                String orderNumber = "20170201/587";
                Order newOrder = Order.create(conn, orderNumber, customer, productList);
                System.out.println("Order created with id = " + newOrder.getId());

                // get order from DB
                Order order = Order.getByNumber(conn, orderNumber);
                System.out.println("OrderNo = " + order.getNumber());
                System.out.println("Customer = " + order.getCustomer());
                System.out.println("Product positions = " + order.getProducts().size());
                for (ProductQuantity pq : order.getProducts()) System.out.println(pq);
                System.out.println("Total price = " + order.getOrderPrice());

                Structure.destroy(conn);
                System.out.println("DB structure destroyed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
