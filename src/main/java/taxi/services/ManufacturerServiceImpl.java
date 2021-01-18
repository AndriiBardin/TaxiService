package taxi.services;

import java.sql.SQLException;
import java.util.List;
import taxi.dao.ManufacturerDao;
import taxi.lib.Inject;
import taxi.lib.Service;
import taxi.model.Manufacturer;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {
    @Inject
    private ManufacturerDao manufacturerDao;

    @Override
    public Manufacturer create(Manufacturer manufacturer) throws SQLException {
        return manufacturerDao.create(manufacturer);
    }

    @Override
    public Manufacturer get(Long id) throws SQLException {
        return manufacturerDao.get(id).orElseThrow(
                () -> new RuntimeException("Manufacturer with id " + id + " not found"));
    }

    @Override
    public List<Manufacturer> getAll() throws SQLException {
        return manufacturerDao.getAll();
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) throws SQLException {
        return manufacturerDao.update(manufacturer);
    }

    @Override
    public boolean delete(Long id) throws SQLException {
        return manufacturerDao.delete(id);
    }
}
