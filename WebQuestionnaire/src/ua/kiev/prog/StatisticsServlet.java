package ua.kiev.prog;

import javafx.util.converter.PercentageStringConverter;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class StatisticsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        parseAnswers(req);

        final String template =
            "<html><title>Prog.kiev.ua</title><body><h2>Statistics</h2><hr>" +
                "<form action=\"\\\">%s<hr><input type=\"submit\" value=\"Go to Start\">" +
                "</form></body></html>";
        String stat = getStatistics();
        PrintWriter pw = resp.getWriter();
        pw.write(String.format(template, stat));
    }

    private void parseAnswers(HttpServletRequest req)
    {
        HttpSession session = req.getSession();
        int personId = (int)session.getAttribute("personId");

        Questions q = Questions.getInstance();
        int count = q.getCount();

        List<Answer> answers = new ArrayList<>();
        for (int i = 1; i <= count; i++)
        {
            String value = req.getParameter("q" + i);
            if ((value != null) && (!value.isEmpty()))
                answers.add(new Answer(i, value));
        }
        Answer[] arr = answers.toArray(new Answer[answers.size()]);
        Answers.getInstance().addAnswers(personId, arr);
    }

    private String getStatistics()
    {
        StringBuilder sb = new StringBuilder();

        int persons = Persons.getInstance().getCount();
        sb.append(String.format("<p>Persons: %d</p>", persons));

        int averageAge = Persons.getInstance().getAverageAge();
        sb.append(String.format("<p>Average age: %d</p>", averageAge));

        final String template = "<p>Question %s: YES - %d, NO - %d</p>";

        Questions q = Questions.getInstance();
        int count = q.getCount();

        Answers answers = Answers.getInstance();
        for (int i = 1; i <= count; i++)
        {
            int yesCount = answers.getStatistics(i, Answer.YES);
            int noCount = answers.getStatistics(i, Answer.NO);
            sb.append(String.format(template, i, yesCount, noCount));
        }
        return sb.toString();
    }
}
