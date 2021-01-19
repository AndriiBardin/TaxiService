package taxi.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import taxi.db.Storage;
import taxi.lib.Dao;
import taxi.model.Car;

@Dao
public class CarDaoImpl implements CarDao {
    @Override
    public Car create(Car car) {
        Storage.add(car);
        return car;
    }

    @Override
    public Optional<Car> get(Long id) {
        return getAll().stream()
                .filter(car -> car.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Car> getAll() {
        return Storage.cars;
    }

    @Override
    public Car update(Car car) {
        IntStream.range(0, Storage.cars.size())
                .filter(i -> Storage.cars.get(i).getId().equals(car.getId()))
                .forEach(i -> Storage.cars.set(i, car));
        return car;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.cars
                .removeIf(i -> i.getId().equals(id));
    }

    @Override
    public List<Car> getAllByDriverId(Long driverId) {
        return Storage.cars.stream()
                .filter(car -> car.getDrivers().stream()
                        .anyMatch(driver -> driver.getId().equals(driverId)))
                .collect(Collectors.toList());
    }
}
