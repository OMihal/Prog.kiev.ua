package ua.kiev.prog;

public class AuthMessage extends Message {
    public AuthMessage(String user, String password) {
        super(MessageType.LOGIN, user, password);
    }
}
