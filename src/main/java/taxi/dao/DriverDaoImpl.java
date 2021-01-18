package taxi.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import taxi.db.Storage;
import taxi.lib.Dao;
import taxi.model.Driver;

@Dao
public class DriverDaoImpl implements DriverDao {
    @Override
    public Driver create(Driver driver) {
        Storage.add(driver);
        return driver;
    }

    @Override
    public Optional<Driver> get(Long id) {
        return getAll().stream()
                .filter(driver -> driver.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Driver> getAll() {
        return Storage.drivers;
    }

    @Override
    public Driver update(Driver driver) {
        IntStream.range(0, Storage.drivers.size())
                .filter(i -> Storage.drivers.get(i).getId().equals(driver.getId()))
                .forEach(i -> Storage.drivers.set(i, driver));
        return driver;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.drivers
                .removeIf(i -> i.getId().equals(id));
    }
}
