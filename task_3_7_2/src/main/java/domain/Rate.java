package domain;

import javax.persistence.*;

@Entity
@Table(name = "Rates")
@NamedQuery(name = "Rate.find",
        query = "SELECT r FROM Rate r WHERE firstCurrency = :first AND secondCurrency = :second")
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cur1_id")
    private domain.Currency firstCurrency;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cur2_id")
    private domain.Currency secondCurrency;

    @Column(nullable = false)
    private double value;

    public Rate() {
    }

    public Rate(Currency firstCurrency, Currency secondCurrency, double value) {
        this.firstCurrency = firstCurrency;
        this.secondCurrency = secondCurrency;
        this.value = value;
    }

    public Currency getFirstCurrency() {
        return firstCurrency;
    }

    public Currency getSecondCurrency() {
        return secondCurrency;
    }

    public double getValue() {
        return value;
    }
    public String getName(){
        return firstCurrency.getShortName() + "/" + secondCurrency.getShortName();
    }
}
