package ua.kiev.prog;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Answers
{
    private Map<Integer/*person id*/, Answer[]> arr =
        Collections.synchronizedMap(new HashMap<Integer, Answer[]>());

    // singleton section
    private static Answers ourInstance = new Answers();

    public static Answers getInstance()
    {
        return ourInstance;
    }

    private Answers()
    {
    }

    public void addAnswers(int personId, Answer[] answers)
    {
        arr.put(personId, answers);
    }

    public int getStatistics(int qNum, String answer)
    {
        int result = 0;
        Set<Map.Entry<Integer, Answer[]>> set = arr.entrySet();
        for (Map.Entry<Integer, Answer[]> entry : set)
        {
            for (Answer a : entry.getValue())
            {
                if ((a.getQuestionNum() == qNum) &&
                   (a.getAnswer().equals(answer)))
                {
                    result++;
                }
            }
        }
        return result;
    }
}
