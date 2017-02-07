package domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "currency_id")
    private domain.Currency currency;

    @Column(nullable = false)
    private double balance;

    public Account() {
    }

    public Account(String name, Client client, Currency currency) {
        this.name = name;
        this.client = client;
        this.currency = currency;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public String getFullName() {
        return name + "." + currency.getId();
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Client getClient() {
        return client;
    }
}
