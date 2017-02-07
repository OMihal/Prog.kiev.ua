package domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Accounts")
//@NamedQueries({
//        @NamedQuery(name = "Accounts.findByClient",
//                query = "SELECT a FROM Account a WHERE client = :client"),
//        @NamedQuery(name = "Account.findByClientAndCurrency",
//                query = "SELECT a FROM Account a WHERE client = :client and currency = :currency")})
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

//    @OneToMany(cascade = {CascadeType.ALL},fetch= FetchType.EAGER, mappedBy = "debit")
//    private Set<Transaction> debit_transactions;
//
//    @OneToMany(cascade = {CascadeType.ALL},fetch= FetchType.EAGER, mappedBy = "credit")
//    private Set<Transaction> credit_transactions;

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
