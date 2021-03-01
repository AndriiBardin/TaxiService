package taxi.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import taxi.dao.CarDao;
import taxi.lib.Dao;
import taxi.model.Car;
import taxi.model.Driver;
import taxi.model.Manufacturer;
import taxi.services.util.ConnectionUtil;

@Dao
public class CarJdbcDao implements CarDao {
    @Override
    public Car create(Car car) {
        String query = "INSERT INTO cars (model, manufacturer_id) VALUES (?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, car.getModel());
            preparedStatement.setLong(2, car.getManufacturer().getId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                car.setId(resultSet.getObject(1, Long.class));
            }
            return car;
        } catch (SQLException e) {
            throw new RuntimeException("Can't create car" + car.getModel(), e);
        }
    }

    @Override
    public Optional<Car> get(Long id) {
        String getCarQuery = "SELECT c.id, c.model, m.id, m.name, m.country "
                + "FROM cars c INNER JOIN manufacturers m ON c.manufacturer_id = m.id "
                + "WHERE c.id = ? AND c.deleted = false AND m.deleted = false";
        Car car = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(getCarQuery)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                car = getCarFromResult(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cant get car by id " + id, e);
        }
        if (car != null) {
            car.setDrivers(getDriversFromCar(car.getId()));
        }
        return Optional.ofNullable(car);
    }

    @Override
    public List<Car> getAll() {
        List<Car> cars = new ArrayList<>();
        String getAllCars = "SELECT c.id, c.model, m.id, m.name, m.country "
                + "FROM cars c INNER JOIN manufacturers m ON c.manufacturer_id = m.id "
                + "WHERE c.deleted = false AND m.deleted = false ";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(getAllCars)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cars.add(getCarFromResult(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't create list of cars", e);
        }
        for (Car car : cars) {
            car.setDrivers(getDriversFromCar(car.getId()));
        }
        return cars;
    }

    @Override
    public Car update(Car car) {
        String updateQuery = "UPDATE cars SET model = ?, manufacturer_id = ? "
                + "WHERE id = ? AND deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, car.getModel());
            preparedStatement.setLong(2, car.getManufacturer().getId());
            preparedStatement.setLong(3, car.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can't update car " + car.getId(), e);
        }
        deleteDriversFromCars(car);
        addDriversToCar(car);
        return car;
    }

    @Override
    public boolean delete(Long id) {
        String deleteQuery = "UPDATE cars SET deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Can't delete car - id " + id, e);
        }
    }

    @Override
    public List<Car> getAllByDriverId(Long driverId) {
        String getAllCarsByDriver = "Select distinct c.id, c.model, m.id, m.name, m.country "
                + "FROM cars c INNER JOIN manufacturers m ON c.manufacturer_id = m.id "
                + "INNER JOIN driver_cars dc ON c.id = dc.car_id "
                + "INNER JOIN drivers d ON dc.driver_id = d.id "
                + "WHERE dc.driver_id = ? "
                + "AND c.deleted = false AND d.deleted = false";
        List<Car> carsByDriver = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(getAllCarsByDriver)) {
            preparedStatement.setLong(1, driverId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                carsByDriver.add(getCarFromResult(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cant create list of all cars for this driver "
                    + driverId, e);
        }
        for (Car car : carsByDriver) {
            car.setDrivers(getDriversFromCar(car.getId()));
        }
        return carsByDriver;
    }

    private Car getCarFromResult(ResultSet resultSet) {
        try {
            Long carId = resultSet.getObject("c.id", Long.class);
            String model = resultSet.getObject("c.model", String.class);
            Long manufacturerId = resultSet.getObject("m.id", Long.class);
            String manufacturerName = resultSet.getObject("m.name", String.class);
            String manufacturerCountry = resultSet.getObject("m.country", String.class);
            Manufacturer manufacturer = new Manufacturer(manufacturerName, manufacturerCountry);
            manufacturer.setId(manufacturerId);
            Car car = new Car(model, manufacturer);
            car.setId(carId);
            return car;
        } catch (SQLException e) {
            throw new RuntimeException("Cant get car from resultSet", e);
        }
    }

    private void deleteDriversFromCars(Car car) {
        String query = "DELETE FROM driver_cars WHERE car_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, car.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Cant remove drivers from car " + car.getId(), e);
        }
    }

    private void addDriversToCar(Car car) {
        String query = "INSERT INTO driver_cars (car_id, driver_id) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, car.getId());
            for (Driver driver : car.getDrivers()) {
                preparedStatement.setLong(2, driver.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't add drivers to car " + car.getId(), e);
        }
    }

    private List<Driver> getDriversFromCar(Long carId) {
        String query = "SELECT * FROM driver_cars dc "
                + "INNER JOIN drivers d ON dc.driver_id = d.id "
                + " WHERE car_id = ? AND deleted = false";
        List<Driver> drivers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, carId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getObject("name", String.class);
                String licence = resultSet.getObject("licence", String.class);
                Long driverId = resultSet.getObject("id", Long.class);
                String login = resultSet.getObject("login", String.class);
                String password = resultSet.getObject("password", String.class);
                Driver driver = new Driver(name, licence, login, password);
                driver.setId(driverId);
                drivers.add(driver);
            }
            return drivers;
        } catch (SQLException e) {
            throw new RuntimeException("Can't get drivers from car " + carId, e);
        }
    }
}
