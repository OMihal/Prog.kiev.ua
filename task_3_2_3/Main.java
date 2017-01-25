package ua.kiev.prog.omihal.homework.task_3_2_3;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        String request = "http://query.yahooapis.com/v1/public/yql?format=xml&q=select%20" +
                "*%20from%20yahoo.finance.xchange%20where%20pair%20in%20(\"USDEUR\",\"USDUAH\")&env=store://datatables.org/alltableswithkeys";

        try {
            String result = performRequest(request);
            System.out.println(result);

            JAXBContext jaxbContext = JAXBContext.newInstance(Query.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Query query = (Query) unmarshaller.unmarshal(new ByteArrayInputStream(result.getBytes()));

            for (Rate rate : query.getResults().getRate()) {
                System.out.printf(rate.getName() + " = " + rate.getRate() + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String performRequest(String request) throws Exception {
        URL obj = new URL(request);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "TEST");

        int responseCode = con.getResponseCode();
        System.out.println("Response Code : " + responseCode);

        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        return response.toString();
    }

}
