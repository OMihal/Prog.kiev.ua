package ua.kiev.prog.omihal.homework.task_3_2_1;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

public class Main
{
    private static String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "<trains>" +
        "<train id=\"1\">" +
        "<from>Kyiv</from>" +
        "<to>Donetsk</to>" +
        "<date>21.12.2013</date>" +
        "<departure>15:05</departure>" +
        "</train>" +
        "<train id=\"2\">" +
        "<from>Lviv</from>" +
        "<to>Donetsk</to>" +
        "<date>21.12.2013</date>" +
        "<departure>19:05</departure>" +
        "</train>" +
        "</trains>";

    public static void main(String[] args)
    {
        try
        {
            InputStream is = new ByteArrayInputStream(xml.getBytes());

            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = f.newDocumentBuilder();

            Document document = documentBuilder.parse(is);
            Element trains = document.getDocumentElement(); // root

            // add new train
            Train newTrain = new Train(33, "Kyiv", "Poltava", new Date());
            newTrain.appendTo(trains);

            Calendar c = Calendar.getInstance();
            c.set(2013, 11, 21, 15, 0);
            Date notBefore = c.getTime();
            c.set(2013, 11, 21, 19, 0);
            Date notAfter = c.getTime();

            NodeList list = trains.getChildNodes();
            for (int i = 0; i < list.getLength(); i++)
            {
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element element = (Element)node;
                    Train train = Train.parse(element);
                    Date dt = train.getDate();
                    if (dt.after(notBefore) && dt.before(notAfter))
                        System.out.println(train);
                }
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
