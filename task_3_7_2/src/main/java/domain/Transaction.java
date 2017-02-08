package domain;

import javax.persistence.*;

@Entity
@Table(name = "Transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "debit_id")
    private Account debit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_id")
    private Account credit;

    @Column(nullable = false)
    private double sum;

    public Transaction() {
    }

    public Transaction(Account debit, Account credit, double sum) {
        this.debit = debit;
        this.credit = credit;
        this.sum = sum;
    }

    public long getId() {
        return id;
    }

    public Account getDebit() {
        return debit;
    }

    public Account getCredit() {
        return credit;
    }

    public double getSum() {
        return sum;
    }
}
