package ua.kiev.prog;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class Persons
{
    private AtomicInteger counter = new AtomicInteger();
    private Map<Integer, Person> arr =
        Collections.synchronizedMap(new HashMap<Integer, Person>());
    private int ageSum = 0;

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
            Person p = entry.getValue();
            if (p.getLastName().equalsIgnoreCase(person.getLastName()))
            {
                if (p.getFirstName().equalsIgnoreCase(person.getFirstName()))
                {
                    return entry.getKey();
                }
            }
        }
        return -1;
    }

    public synchronized int addOrUpdate(Person person)
    {
        int id = find(person);
        if (id == -1)
        {
            id = counter.incrementAndGet();
            ageSum += person.getAge();
        }
        arr.put(id, person);
        return id;
    }
    public int getCount()
    {
        return arr.size();
    }
    public int getAverageAge()
    {
        return ageSum/arr.size();
    }
}
