package ua.kiev.prog;

public class UserStatus {
    public static final int NOT_FOUND = -1;
    public static final int OFFLINE = 1;
    public static final int ONLINE = 2;
    public static final int BUSY = 3;

    public static String getString(int status)
    {
        switch(status)
        {
            case NOT_FOUND: return "NOT_FOUND";
            case ONLINE: return "ON-LINE";
            case OFFLINE: return "OFF-LINE";
            case BUSY: return "BUSY";
            default: return "UNDEFINED";
        }
    }

}