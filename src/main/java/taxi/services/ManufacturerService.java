package taxi.services;

import java.sql.SQLException;
import java.util.List;
import taxi.model.Manufacturer;

public interface ManufacturerService {
    Manufacturer create(Manufacturer manufacturer) throws SQLException;

    Manufacturer get(Long id) throws SQLException;

    List<Manufacturer> getAll() throws SQLException;

    Manufacturer update(Manufacturer manufacturer) throws SQLException;

    boolean delete(Long id) throws SQLException;
}
