import domain.*;

import javax.persistence.*;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPABank");
        EntityManager em = emf.createEntityManager();
        try {
            createStructure(em);
            Processing processing = new Processing(em);

            System.out.println("---------- Get client ----------");
            Client client = processing.getClientByTaxNumber("1234567890");

            System.out.println("---------- Add cache money in UAH ----------");
            Currency uah = processing.getCurrency(980);
            Account uahAcc1 = client.getAccounts(uah).get(0);
            Account uahAcc2 = client.getAccounts(uah).get(1);
            processing.addCacheToAccount(uahAcc1, 10000);

            System.out.println("---------- Transfer money to other account ----------");
            processing.transfer(uahAcc1, 5000, uahAcc2);

            System.out.println("---------- Convert 100 UAH to dollars ----------");
            Currency usd = processing.getCurrency(840);
            Account usdAcc = client.getAccounts(usd).get(0);
            processing.convertFromTo(uahAcc1, 100, usdAcc);

            System.out.println("---------- Get client balance ----------");
            double clBalance = processing.getTotalBalance(client, uah);
            System.out.println("Total balance = " + clBalance);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }

    private static void createStructure(EntityManager em) throws Exception {
        em.getTransaction().begin();
        try {
            System.out.println("---------- Add currencies ----------");
            Currency usd = new Currency(840, "USD", "USA dollar");
            Currency eur = new Currency(978, "EUR", "Euro");
            Currency uah = new Currency(980, "UAH", "Ukrainian hrivna");

            Currency[] currencies = new Currency[]{usd, eur, uah};
            for (Currency c : currencies) {
                em.persist(c);
            }
            System.out.println("---------- Add rates (date ignored) ----------");
            Rate usd_uah = new Rate(usd, uah, (double) 1 / 27);
            Rate usd_eur = new Rate(usd, eur, (double) 1 / 1.09);
            Rate eur_usd = new Rate(eur, usd, 1.09);
            Rate eur_uah = new Rate(eur, uah, (double) 1 / 30);
            Rate uah_usd = new Rate(uah, usd, 27);
            Rate uah_eur = new Rate(uah, eur, 30);

            Rate[] rates = new Rate[]{
                    usd_uah, usd_eur, eur_usd, eur_uah, uah_usd, uah_eur};
            for (Rate rate : rates) {
                em.persist(rate);
            }
            System.out.println("---------- Add bank ----------");
            Client bank = new Client(Bank.TAX_NUMBER, Bank.NAME);
            em.persist(bank);

            System.out.println("---------- Add clients ----------");
            Client client1 = new Client("1234567890", "Ivanov Petro");
            Client client2 = new Client("0987654321", "Petrov Ivan");
            Client[] clients = new Client[]{client1, client2};
            for (Client client : clients) {
                em.persist(client);
            }
            System.out.println("---------- Add accounts ----------");
            Account[] accounts = new Account[]{
                    // bank
                    new Account("1001100000001", bank, uah),
                    new Account("1001100000002", bank, usd),
                    new Account("1001100000003", bank, eur),
                    // client 1
                    new Account("2600100010001", client1, uah),
                    new Account("2600100010002", client1, uah),
                    new Account("2600100010003", client1, usd),
                    // client2
                    new Account("2600100020001", client2, uah),
                    new Account("2600100020002", client2, eur),
            };
            for (Account acc : accounts) {
                em.persist(acc);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        }
    }
}
