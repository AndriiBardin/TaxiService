package taxi.services.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    public static Connection getConnection() {
        Properties connectProps = new Properties();
        connectProps.put("user", "root");
        connectProps.put("password", "12341234");
        String url = "jdbc:mysql://localhost:3306/storage";
        try {
            return DriverManager.getConnection(url, connectProps);
        } catch (SQLException e) {
            throw new RuntimeException("Can't connect to storage", e);
        }
    }
}

