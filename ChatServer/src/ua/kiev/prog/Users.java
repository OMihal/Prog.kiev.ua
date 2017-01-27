package ua.kiev.prog;

import sun.text.resources.CollationData;

import java.util.*;

public class Users {
    private Map<String, User> users = Collections.synchronizedMap(new HashMap<>());
    private static Users ourInstance = new Users();

    public static Users getInstance() {
        return ourInstance;
    }

    private Users() {
        add(new User("User1", "password1"));
        add(new User("User2", "password2"));
        add(new User("User3", "password3"));
        add(new User("User4", "password4"));
    }

    public boolean checkAuth(String loginName, String password) {
        User user = users.get(loginName.toLowerCase());
        if ((user != null) && (user.isValidPassword(password))) {
            return true;
        }
        return false;
    }

    public int getStatus(String loginName) {
        User user = users.get(loginName.toLowerCase());
        if (user != null) {
            return user.getStatus();
        }
        return UserStatus.NOT_FOUND;
    }

    public boolean setStatus(String loginName, int status) {
        User user = users.get(loginName.toLowerCase());
        if (user != null) {
            user.setStatus(status);
            return true;
        }
        return false;
    }

    private void add(User user)
    {
        String nameLC = user.getLoginName().toLowerCase();
        users.put(nameLC, user);
    }
    public Collection<String> getNames() {
        return users.keySet();
    }

}
