package ua.kiev.prog;

import java.util.*;

public class ServiceCommands {
    public static final String HELP = "/help";
    public static final String ALL_USERS = "/users";
    public static final String ONLINE_USERS = "/online";
    public static final String CHAT_ROOMS = "/chats";
    public static final String CHAT_ADD = "/addchat";
    public static final String ENTER_TO_CHAT = "/enter";
    public static final String EXIT_FROM_CHAT = "/exit";
    public static final String USER_STATUS = "/status";

    public static List<String> help = new ArrayList<>();

    static {
        help.add("Commands:");
        help.add(ALL_USERS + " - get all users");
        help.add(USER_STATUS + " <username> - get user status");
        help.add(CHAT_ROOMS + " - get all chat rooms");
        help.add(CHAT_ADD + " <chatroom> - add new chat room");
        help.add(ENTER_TO_CHAT + " <chatroom> - enter to chat room");
        help.add(EXIT_FROM_CHAT + " - exit from chat room");
    }

    public static Collection<String> getHelp() {
        return Collections.unmodifiableList(help);
    }
}
