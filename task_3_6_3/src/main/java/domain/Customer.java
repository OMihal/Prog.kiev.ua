package domain;

public class Customer {
    private int id;
    private String taxNumber;
    private String name;

    public Customer(int id, String taxNumber, String name) {
        this.id = id;
        this.taxNumber = taxNumber;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "[id = " + id + ", tax = " + taxNumber + ", name = " + name + "]";
    }
}
