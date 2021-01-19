package taxi.services.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import taxi.lib.Service;

@Service
public class ConnectionUtil {
    public static Connection getCon() {
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

