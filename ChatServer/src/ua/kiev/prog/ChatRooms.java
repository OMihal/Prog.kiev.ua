package ua.kiev.prog;

import java.util.*;

public class ChatRooms {
    private static ChatRooms ourInstance = new ChatRooms();

    private Map<String, ChatRoom> chatRooms = Collections.synchronizedMap(new HashMap<>());

    public static ChatRooms getInstance() {
        return ourInstance;
    }

    private ChatRooms() {
    }

    public Collection<String> getNames() {
        return chatRooms.keySet();
    }
}
