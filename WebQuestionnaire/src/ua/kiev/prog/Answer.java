package ua.kiev.prog;

public class Answer
{
    public static final String YES = "yes";
    public static final String NO = "no";

    private int questionNum;
    private String answer;

    public Answer(int questionNum, String answer)
    {
        this.questionNum = questionNum;
        this.answer = answer;
    }

    public int getQuestionNum()
    {
        return questionNum;
    }

    public String getAnswer()
    {
        return answer;
    }
}
