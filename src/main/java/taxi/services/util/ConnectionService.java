package taxi.services.util;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionService {
    Connection getCon() throws SQLException;
}
