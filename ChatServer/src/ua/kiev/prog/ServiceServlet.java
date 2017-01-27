package ua.kiev.prog;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Set;

public class ServiceServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        byte[] buf = Utils.requestBodyToArray(req);
        String bufStr = new String(buf, StandardCharsets.UTF_8);

        Message msg = Message.fromJSON(bufStr);
        if ((msg != null) && (msg.getType() == MessageType.SERVICE)) {
            String text = msg.getText();
            if (text.toLowerCase().equals(ServiceCommands.HELP)) {
                getHelp(resp);
            } else if (text.toLowerCase().equals(ServiceCommands.ALL_USERS)) {
                    getAllUsers(resp);
            } else if (text.toLowerCase().startsWith(ServiceCommands.USER_STATUS)) {
                getUserStatus(text, resp);
            } else if (text.equalsIgnoreCase(ServiceCommands.CHAT_ROOMS)) {
                getChatRooms(resp);
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    private static void getAllUsers(HttpServletResponse resp) throws IOException {
        Users users = Users.getInstance();
        Collection<String> names = users.getNames();
        ServiceResponse sr = new ServiceResponse(names);
        sendServiceRespone(sr, resp);
    }
    private static void getHelp(HttpServletResponse resp) throws IOException {
        ServiceResponse sr = new ServiceResponse(ServiceCommands.getHelp());
        sendServiceRespone(sr, resp);
    }
    private static void getUserStatus(String text, HttpServletResponse resp) throws IOException {
        String[] vals = text.split(" ");
        if (vals.length !=  2){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        Users users = Users.getInstance();
        int status = users.getStatus(vals[1]);
        ServiceResponse sr = new ServiceResponse(UserStatus.getString(status));
        sendServiceRespone(sr, resp);
    }
    private static void getChatRooms(HttpServletResponse resp) throws IOException {
        ChatRooms rooms = ChatRooms.getInstance();
        Collection<String> names = rooms.getNames();
        ServiceResponse sr = new ServiceResponse(names);
        sendServiceRespone(sr, resp);
    }
    private static void sendServiceRespone(ServiceResponse sr, HttpServletResponse resp) throws IOException {
        String json = sr.toJSON();
        if (json != null) {
            OutputStream os = resp.getOutputStream();
            byte[] buf = json.getBytes(StandardCharsets.UTF_8);
            os.write(buf);
        }
    }
}
