import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class FlatCollection {
    private static FlatCollection ourInstance = new FlatCollection();
    private List<Flat> flats = new ArrayList<>();

    private FlatCollection() {
        flats.add(new Flat("Solomensky", "3, Pulyuya st.", 78.6, 3, 66000));
        flats.add(new Flat("Golosiivsky", "23, Vasilkivska st.", 108.2, 4, 106000));
        flats.add(new Flat("Podilsky", "1, Sagaidachnogo st.", 245.8, 6, 250000));
        flats.add(new Flat("Dniprovsky", "10, Bazhana ave.", 69.7, 2, 50000));
    }
    public static FlatCollection getInstance() {
        return ourInstance;
    }

    public Collection<Flat> getFlats() {
        return Collections.unmodifiableList(flats);
    }
}
