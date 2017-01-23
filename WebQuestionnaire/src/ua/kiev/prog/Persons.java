package ua.kiev.prog;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class Persons
{
    private AtomicInteger counter = new AtomicInteger();
    private Map<Integer, Person> arr = new HashMap<Integer, Person>();
    private int averageAge;

    // singleton section
    private static Persons ourInstance = new Persons();

    public static Persons getInstance()
    {
        return ourInstance;
    }

    private Persons()
    {
    }

    private int find(Person person)
    {
        Set<Map.Entry<Integer, Person>> set = arr.entrySet();
        for (Map.Entry<Integer, Person> entry : set)
        {
            if (person.equals(entry.getValue()))
            {
                return entry.getKey();
            }
        }
        return -1;
    }

    public synchronized int register(Person person)
    {
        int id = find(person);
        if (id == -1)
        {
            id = counter.incrementAndGet();
            arr.put(id, person);
            calculateAverageAge();
        }
        return id;
    }
    public int getCount()
    {
        return arr.size();
    }

    public int getAverageAge() {
        return averageAge;
    }

    private void calculateAverageAge()
    {
        int ageSum = 0;
        Set<Map.Entry<Integer, Person>> set = arr.entrySet();
        for (Map.Entry<Integer, Person> entry : set) {
            ageSum += entry.getValue().getAge();
        }
        averageAge = ageSum / arr.size();
    }
}
