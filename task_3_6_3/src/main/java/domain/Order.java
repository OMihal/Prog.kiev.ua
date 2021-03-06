package domain;

import java.util.Collection;

public class Order {
    private int id;
    private String number;
    private Customer customer;
    private Collection<ProductQuantity> products;

    public Order(int id, String number, Customer customer, Collection<ProductQuantity> products) {
        this.id = id;
        this.number = number;
        this.customer = customer;
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Collection<ProductQuantity> getProducts() {
        return products;
    }

    public double getOrderPrice() {
        double sum = 0;
        if (products != null) {
            for (ProductQuantity s : products) {
                sum += s.getTotalPrice();
            }
        }
        return sum;
    }
}
