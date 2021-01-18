package taxi.services.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import taxi.lib.Service;

@Service
public class ConnectionServiceImpl implements ConnectionService {
    @Override
    public Connection getCon() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/storage",
                "root", "12341234");
    }
}

