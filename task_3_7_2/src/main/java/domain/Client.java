package domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "Clients")
@NamedQuery(name = "Client.byTaxNumber", query = "SELECT c FROM Client c WHERE taxCode = :taxNumber")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "tax_code", nullable = false, unique = true)
    private String taxCode;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Account> accounts = new ArrayList<>();

    public Client() {
    }

    public Client(String taxCode, String name) {
        this.taxCode = taxCode;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public String getName() {
        return name;
    }

    public List<Account> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }

    public List<Account> getAccounts(Currency currency) throws PersistenceException {
        List<Account> out = new ArrayList<>();
        for (Account acc : accounts) {
            if (acc.getCurrency().equals(currency))
                out.add(acc);
        }
        return out;
    }
}
