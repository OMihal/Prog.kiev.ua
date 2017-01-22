package ua.kiev.prog;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class RegisterServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        final String template =
            "<html><title>Prog.kiev.ua</title><body><h2>You registered as %s %s</h2>" +
                "<h3>Your ID: %d</h3><br>%s</body></html>";

        String fname = req.getParameter("fname");
        String lname = req.getParameter("lname");

        Person person = new Person(fname, lname);
        Persons persons = Persons.getInstance();
        int id = persons.addOrUpdate(person);

        HttpSession session = req.getSession();
        session.setAttribute("personId", id);

        String questions = buildQuestions();

        String out = String.format(template, fname, lname, id, questions);
        PrintWriter pw = resp.getWriter();
        pw.write(out);
    }

    private String buildQuestions()
    {
        final String radioTempl =
            "<input type=\"radio\" name=\"q%d\" value=\"%s\">%s";
        StringBuilder sb = new StringBuilder();
        Questions questions = Questions.getInstance();
        sb.append("<form name=\"qform\" action=\"\\answer\" method=\"POST\">");
        Set<Map.Entry<Integer, String>> map = questions.get();
        for (Map.Entry<Integer, String> entry : map)
        {
            int num = entry.getKey();
            sb.append("<hr><p><h3>");
            sb.append(num + ". " + entry.getValue() + "</h3><br>");
            sb.append(String.format(radioTempl, num, Answer.YES, "Yes"));
            sb.append(String.format(radioTempl, num, Answer.NO, "No"));
            sb.append("</p>");
        }
        sb.append("<hr>");
        sb.append("<input type=\"submit\" value=\"Answer\">");
        sb.append("</form>");
        return sb.toString();
    }
}

