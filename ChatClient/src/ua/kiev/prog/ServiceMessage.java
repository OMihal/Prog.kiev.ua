package ua.kiev.prog;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Set;

public class ServiceMessage extends Message {
    public ServiceMessage(String from, String text) {
        super(MessageType.SERVICE, from, text);
    }

    @Override
    public int send(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        int res;
        try (OutputStream os = conn.getOutputStream()) {
            String json = toJSON();
            os.write(json.getBytes(StandardCharsets.UTF_8));
            res = conn.getResponseCode();
        }
        if (res == 200) {
            try (InputStream is = conn.getInputStream()) {
                byte[] inputBytes = Utils.inputStreamToArray(is);
                String json = new String(inputBytes, StandardCharsets.UTF_8);
                ServiceResponse sr = ServiceResponse.fromJSON(json);
                Collection<String> entries =  sr.get();
                if ((entries == null) || (entries.isEmpty())) {
                    System.out.println("no entries :(");
                } else {
                    for (String s : entries) {
                        System.out.println(s);
                    }
                }
            }
        }
        return res;
    }
}
