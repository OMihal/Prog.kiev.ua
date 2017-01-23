package ua.kiev.prog.omihal.homework.task_3_2_1;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Train
{
    private int id;
    private String to;
    private String from;
    private Date date;

    public Train(int id, String to, String from, Date date)
    {
        this.id = id;
        this.to = to;
        this.from = from;
        this.date = date;
    }

    public int getId()
    {
        return id;
    }

    public String getTo()
    {
        return to;
    }

    public String getFrom()
    {
        return from;
    }

    public Date getDate()
    {
        return date;
    }

    public void appendTo(Node node)
    {
        Document document = node.getOwnerDocument();

        Element eTrain = document.createElement("train");
        eTrain.setAttribute("id", Integer.toString(id));

        Element eFrom = document.createElement("from");
        eFrom.setTextContent(from);
        eTrain.appendChild(eFrom);

        Element eTo = document.createElement("to");
        eTo.setTextContent(to);
        eTrain.appendChild(eTo);

        Element eDate = document.createElement("date");
        eDate.setTextContent(new SimpleDateFormat("dd.MM.yyyy").format(date));
        eTrain.appendChild(eDate);

        Element eDeparture = document.createElement("departure");
        eDeparture.setTextContent(new SimpleDateFormat("hh:mm").format(date));
        eTrain.appendChild(eDeparture);

        node.appendChild(eTrain);

    }
    public static Train parse(Element element) throws Exception
    {
        int id = Integer.parseInt(element.getAttribute("id"));
        String from = element.getElementsByTagName("from").item(0).
            getChildNodes().item(0).getNodeValue();
        String to = element.getElementsByTagName("to").item(0).
            getChildNodes().item(0).getNodeValue();

        String date = element.getElementsByTagName("date").item(0).
            getChildNodes().item(0).getNodeValue();

        String departure = element.getElementsByTagName("departure").item(0).
            getChildNodes().item(0).getNodeValue();

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date dt =  df.parse(date + " " + departure);

        return new Train(id, from, to, dt);
    }

    @Override
    public String toString()
    {
        return String.format("Id = %d, %s - %s, %s", id, from, to,
            new SimpleDateFormat("dd.MM.yyyy HH:mm").format(date));
    }
}
