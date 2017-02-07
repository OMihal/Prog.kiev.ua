package domain;

import javax.persistence.*;
import java.util.List;

public class Processing {
    private EntityManager em;

    public Processing(EntityManager em) {
        this.em = em;
    }
    public Currency getCurrency(int id) throws PersistenceException {
        return em.find(Currency.class, id);
    }
    public Client getClientByTaxNumber(String taxNumber) throws PersistenceException {
        em.clear();
        Query query = em.createNamedQuery("Client.byTaxNumber", Client.class);
        query.setParameter("taxNumber", taxNumber);
        List<Client> clients = query.getResultList();
        if (clients.isEmpty())
            throw new PersistenceException("Client not found!");
        return clients.get(0);
    }
    private Rate getRate(Currency first, Currency second) throws PersistenceException {
        em.clear();
        Query query = em.createNamedQuery("Rate.find", Rate.class);
        query.setParameter("first", first);
        query.setParameter("second", second);
        List<Rate> rates = query.getResultList();
        if (rates.isEmpty())
            throw new PersistenceException("Rate not found!");
        return rates.get(0);
    }
    public void addCacheToAccount(Account account, double sum) throws PersistenceException {
        em.clear();
        em.getTransaction().begin();
        try {
            Currency currency = account.getCurrency();
            Client bank = getClientByTaxNumber(Bank.TAX_NUMBER);
            Account bnkAcc = bank.getAccounts(currency).get(0);
            //debit
            cred(account, sum);
            // credit
            cred(bnkAcc, sum);
            // operation
            Transaction t = new Transaction(bnkAcc, account, sum);
            em.persist(t);

            em.getTransaction().commit();

        } catch (PersistenceException ex){
            em.getTransaction().rollback();
            throw ex;
        }
    }
    public void convertFromTo(Account from, double sum, Account to) throws PersistenceException {
        em.clear();
        em.getTransaction().begin();
        try {
            Rate rate = getRate(to.getCurrency(), from.getCurrency());
            dept(from, sum);
            cred(to, sum * rate.getValue());
            Transaction t = new Transaction(from, to, sum);
            em.persist(t);

            em.getTransaction().commit();

        } catch (PersistenceException ex){
            em.getTransaction().rollback();
            throw ex;
        }
    }

    public void transfer(Account from, double sum, Account to) throws PersistenceException {
        em.clear();
        em.getTransaction().begin();
        try {
            if (!from.getCurrency().equals(to.getCurrency()))
                throw new PersistenceException("Different account currencies!");
            dept(from, sum);
            cred(to, sum);
            Transaction t = new Transaction(from, to, sum);
            em.persist(t);
            em.getTransaction().commit();
        } catch (PersistenceException ex){
            em.getTransaction().rollback();
            throw ex;
        }
    }

    public double getTotalBalance(Client client, Currency equivalent) throws Exception {
        em.clear();
        List<Account> accounts = client.getAccounts();
        float totalBalance = 0;
        for (Account acc : accounts) {
            double balance = acc.getBalance();
            if (balance > 0) {
                if (acc.getCurrency().equals(equivalent)) {
                    totalBalance += balance;
                } else {
                    Rate rate = getRate(equivalent, acc.getCurrency());
                    totalBalance += balance * rate.getValue();
                }
            }
        }
        return totalBalance;
    }
    private void dept(Account account, double sum) throws PersistenceException {
        if (account.getBalance() < sum)
            throw new PersistenceException("Insufficient balance");
        account.setBalance(account.getBalance() - sum);
    }
    private void cred(Account account, double sum){
        account.setBalance(account.getBalance() + sum);
    }
}
