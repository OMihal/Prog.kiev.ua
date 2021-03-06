package ua.kiev.prog;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Questions {
    // singleton section
    private static Questions ourInstance = new Questions();

    public static Questions getInstance() {
        return ourInstance;
    }

    private Questions() {
    }

    private static Map<Integer, String> arr = new HashMap<Integer, String>();

    static {
        arr.put(1, "First Question?");
        arr.put(2, "Second Question?");
        arr.put(3, "Third Question?");
        arr.put(4, "Forth Question?");
    }

    Set<Map.Entry<Integer, String>> get() {
        return Collections.unmodifiableSet(arr.entrySet());
    }

    public int getCount() {
        return arr.size();
    }
}
