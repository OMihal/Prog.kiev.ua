package ua.kiev.prog.omihal.homework.task_3_2_1;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Train {
    private int id;
    private String to;
    private String from;
    private String date;
    private String departure;

    public Train(int id, String to, String from, String date, String departure) {
        this.id = id;
        this.to = to;
        this.from = from;
        this.date = date;
        this.departure = departure;
    }

    public int getId() {
        return id;
    }

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    public String getDate() {
        return date;
    }

    public String getDeparture() {
        return departure;
    }

    public static void appendTo(Train train, Node xmlNode) {
        Document document = xmlNode.getOwnerDocument();

        Element eTrain = document.createElement("train");
        eTrain.setAttribute("id", Integer.toString(train.id));

        Element eFrom = document.createElement("from");
        eFrom.setTextContent(train.from);
        eTrain.appendChild(eFrom);

        Element eTo = document.createElement("to");
        eTo.setTextContent(train.to);
        eTrain.appendChild(eTo);

        Element eDate = document.createElement("date");
        eDate.setTextContent(train.date);
        eTrain.appendChild(eDate);

        Element eDeparture = document.createElement("departure");
        eDeparture.setTextContent(train.departure);
        eTrain.appendChild(eDeparture);

        xmlNode.appendChild(eTrain);

    }

    public static Train parse(Element element) throws Exception {
        int id = Integer.parseInt(element.getAttribute("id"));
        String from = element.getElementsByTagName("from").item(0).
                getChildNodes().item(0).getNodeValue();
        String to = element.getElementsByTagName("to").item(0).
                getChildNodes().item(0).getNodeValue();

        String date = element.getElementsByTagName("date").item(0).
                getChildNodes().item(0).getNodeValue();

        String departure = element.getElementsByTagName("departure").item(0).
                getChildNodes().item(0).getNodeValue();

        return new Train(id, from, to, date, departure);
    }
}
