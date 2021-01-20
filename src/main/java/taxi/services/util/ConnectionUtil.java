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

    public static void clearTable() {
        String fkchecksOff = "SET FOREIGN_KEY_CHECKS = 0";
        String truncate1 = "TRUNCATE TABLE cars";
        String truncate2 = "TRUNCATE TABLE driver_cars";
        String truncate3 = "TRUNCATE TABLE drivers";
        String truncate4 = "TRUNCATE TABLE manufacturers";
        String fkchecksOn = "SET FOREIGN_KEY_CHECKS = 1";
        try (Connection connection = getConnection()) {
            connection.createStatement().executeUpdate(fkchecksOff);
            connection.createStatement().executeUpdate(truncate1);
            connection.createStatement().executeUpdate(truncate2);
            connection.createStatement().executeUpdate(truncate3);
            connection.createStatement().executeUpdate(truncate4);
            connection.createStatement().executeUpdate(fkchecksOn);
        } catch (SQLException throwables) {
            throw new RuntimeException("Couldn't establish connection to MySQL server. ",
                    throwables);
        }
    }
}

