package ua.kiev.prog.omihal.homework.task_3_2_1;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Main {
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

    public static void main(String[] args) {
        try {
            InputStream is = new ByteArrayInputStream(xml.getBytes());

            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = f.newDocumentBuilder();

            Document document = documentBuilder.parse(is);
            Element trains = document.getDocumentElement(); // root

            NodeList list = trains.getChildNodes();
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    Train train = Train.parse(element);
                    Date dtDeparture = new SimpleDateFormat("HH:mm").parse(train.getDeparture());
                    int tm = Integer.parseInt(new SimpleDateFormat("HHmm").format(dtDeparture));
                    if ((tm >= 1500) && (tm <= 1900)) {
                        System.out.println(String.format("Train #%d, %s - %s, %s",
                                train.getId(), train.getFrom(), train.getTo(), train.getDeparture()));
                    }
                }
            }
            // add new train
            Train newTrain = new Train(33, "Kyiv", "Poltava", "21.12.2013", "23:10");
            Train.appendTo(newTrain, trains); // append to trains node

            // print updated xml
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(writer));
            System.out.println(writer.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
