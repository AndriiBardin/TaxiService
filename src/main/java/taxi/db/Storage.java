package taxi.db;

import java.util.ArrayList;
import java.util.List;

import taxi.model.Car;
import taxi.model.Driver;
import taxi.model.Manufacturer;

public class Storage {
    private static Long manufacturerId = 0L;
    private static Long carId = 0L;
    private static Long driverId = 0L;
    public static final List<Manufacturer> manufacturers = new ArrayList<>();
    public static final List<Car> cars = new ArrayList<>();
    public static final List<Driver> drivers = new ArrayList<>();

    public static void add(Manufacturer manufacturer) {
        manufacturer.setId(++manufacturerId);
        manufacturers.add(manufacturer);
    }

    public static void add(Car car) {
        car.setId(++carId);
        cars.add(car);
    }

    public static void add(Driver driver) {
        driver.setId(++driverId);
        drivers.add(driver);
    }
}
