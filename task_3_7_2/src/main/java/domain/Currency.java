package domain;

import javax.persistence.*;

@Entity
@Table(name = "Currencies")
public class Currency {
    @Id
    @Column(name = "id", unique = true)
    private int id;

    @Column(name = "short_name", nullable = false)
    private String shortName;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    public Currency() {
    }

    public Currency(int id, String shortName, String fullName) {
        this.id = id;
        this.shortName = shortName;
        this.fullName = fullName;
    }

    public int getId() {
        return id;
    }

    public String getShortName() {
        return shortName;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj != null) && (obj.getClass() == domain.Currency.class)){
            return (this.id == ((domain.Currency)obj).id);
        }
        return false;
    }
}
