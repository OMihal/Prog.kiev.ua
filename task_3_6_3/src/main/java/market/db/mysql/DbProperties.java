package market.db.mysql;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class DbProperties {
    private String url;
    private String user;
    private String password;

    DbProperties() {
        InputStream is = getClass().getClassLoader().getResourceAsStream("db.properties");

        Properties props = new Properties();
        try {
            props.load(is);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        url = props.getProperty("db.url");
        user = props.getProperty("db.user");
        password = props.getProperty("db.password");
    }

    String getUrl() {
        return url;
    }

    String getUser() {
        return user;
    }

    String getPassword() {
        return password;
    }
}
