import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPARestaurant");
        EntityManager em = emf.createEntityManager();
        try {
            System.out.println("---------- Add new foods ----------");
            em.getTransaction().begin();
            try {
                FoodEntry[] foods = new FoodEntry[]{
                        new FoodEntry("Ukrainian borsch", 0.300, 2.80, false),
                        new FoodEntry("Mashed potatoes", 0.100, 1.0, false),
                        new FoodEntry("Meat", 0.200, 5.50, true),
                        new FoodEntry("Tomatto", 0.250, 1.25, true),
                        new FoodEntry("Carrot", 0.450, 3.45, true)
                };
                for (FoodEntry f : foods) {
                    em.persist(f);
                }

                em.getTransaction().commit();
            } catch (Exception ex) {
                em.getTransaction().rollback();
                return;
            }
            System.out.println("---------- Print with min/max price ----------");
            {
                em.clear();
                Query query = em.createNamedQuery("Foods.MinMaxPrice", FoodEntry.class);
                query.setParameter("minPrice", new Double(1.00));
                query.setParameter("maxPrice", new Double(3.00));
                List<FoodEntry> foods = query.getResultList();
                for (FoodEntry f : foods) System.out.println(f);
            }
            System.out.println("---------- Print with discount only ----------");
            {
                em.clear();
                Query query = em.createNamedQuery("Foods.WithDiscount", FoodEntry.class);
                List<FoodEntry> foods = query.getResultList();
                for (FoodEntry f : foods) System.out.println(f);
            }
            System.out.println("---------- Print no more 1 KG -----------");
            {
                em.clear();
                Query query = em.createNamedQuery("Foods.WeightAscending", FoodEntry.class);
                List<FoodEntry> foods = query.getResultList();
                float totalAmount = 0;
                for (FoodEntry f : foods) {
                    if (totalAmount + f.getWeight() > 1)
                        break;
                    System.out.println(f);
                    totalAmount += f.getWeight();
                }
                System.out.println("Total amount = " + totalAmount);
            }
        } finally {
            em.close();
            emf.close();
        }
    }
}
