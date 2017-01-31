public class Product {
    private String name;
    private String briefDescription;
    private double price;

    public Product(String name, String briefDescription, double price) {
        this.name = name;
        this.briefDescription = briefDescription;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getBriefDescription() {
        return briefDescription;
    }

    public double getPrice() {
        return price;
    }
}
