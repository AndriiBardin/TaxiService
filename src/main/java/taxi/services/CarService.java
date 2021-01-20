package taxi.services;

import java.util.List;
import taxi.model.Car;
import taxi.model.Driver;

public interface CarService {
    Car create(Car car);

    Car update(Car car);

    Car get(Long id);

    List<Car> getAll();

    boolean delete(Long id);

    void addDriverToCar(Driver driver, Car car);

    void removeDriverFromCar(Driver driver, Car car);

    List<Car> getAllByDriver(Long driverId);
}
