package domain;

public class Product {
    private int id;
    private int category;
    private int partNumber;
    private String name;
    private double price;

    public Product(int id, int category, int partNumber, String name, double price) {
        this.id = id;
        this.category = category;
        this.partNumber = partNumber;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public int getCategory() {
        return category;
    }

    public int getPartNumber() {
        return partNumber;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "[id = " + id + ", category = " + category + ", name = " + name + ", price = " + price + "]";
    }
}
