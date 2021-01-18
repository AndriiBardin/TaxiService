package taxi.services.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import taxi.lib.Service;

@Service
public class ConnectionServiceImpl implements ConnectionService {
    @Override
    public Connection getCon() throws SQLException {
        Properties connectProps = new Properties();
        connectProps.put("user", "root");
        connectProps.put("password", "12341234");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/storage", connectProps);
    }
}

