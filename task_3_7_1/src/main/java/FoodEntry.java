import javax.persistence.*;

@Entity
@Table(name = "Menu")
@NamedQueries({
        @NamedQuery(name = "Foods.WeightAscending", query = "SELECT f FROM FoodEntry f ORDER BY weight ASC"),
        @NamedQuery(name = "Foods.WithDiscount", query = "SELECT f FROM FoodEntry f WHERE discount=true"),
        @NamedQuery(name = "Foods.MinMaxPrice", query = "SELECT f FROM FoodEntry f WHERE price >= :minPrice and price <= :maxPrice")})

public class FoodEntry {
    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double weight;

    @Column(nullable = false)
    private double price;

    @Column(name = "discount", nullable = false)
    private boolean hasDiscount;

    public FoodEntry() {
    }

    public FoodEntry(String name, double weight, double price, boolean hasDiscount) {
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.hasDiscount = hasDiscount;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getWeight() {
        return weight;
    }

    public boolean hasDiscount() {
        return hasDiscount;
    }

    @Override
    public String toString() {
        return "[id = " + id + ", name = " + name + ", w = " + weight +
                ", price = " + price + ", discount = " + hasDiscount + "]";
    }
}
