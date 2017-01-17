package ua.kiev.prog;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Answers {
    // singleton section
    private static Answers ourInstance = new Answers();
    public static Answers getInstance() {
        return ourInstance;
    }
    private Answers() {
    }
    //members
    private Map<Integer/*person id*/, Answer[]> arr =
            Collections.synchronizedMap(new HashMap<Integer, Answer[]>());


}
