package ua.kiev.prog;

public class User {
    private String loginName;
    private String password;
    private int status = UserStatus.OFFLINE;

    public User(String loginName, String password) {
        this.loginName = loginName;
        this.password = password;
    }

    public String getLoginName() {
        return loginName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isValidPassword(String password) {
        return this.password.equals(password);
    }
}
