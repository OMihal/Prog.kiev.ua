package ua.kiev.prog;

public class LogoutMessage extends Message {
    public LogoutMessage(String from) {
        super(MessageType.LOGOUT, from);
    }
}
