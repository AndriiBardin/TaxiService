package taxi.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import taxi.dao.DriverDao;
import taxi.lib.Dao;
import taxi.model.Driver;
import taxi.services.util.ConnectionUtil;

@Dao
public class DriverJdbcDao implements DriverDao {
    @Override
    public Driver create(Driver driver) {
        String addDriverQuery = "INSERT INTO drivers (name, licence) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(addDriverQuery,
                        Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, driver.getName());
            preparedStatement.setString(2, driver.getLicenceNumber());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                driver.setId(resultSet.getObject(1, Long.class));
            }
            return driver;
        } catch (SQLException e) {
            throw new RuntimeException("Can't add driver" + driver, e);
        }
    }

    @Override
    public Optional<Driver> get(Long id) {
        String getByIdQuery = "SELECT * FROM drivers WHERE id = ? AND deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(getByIdQuery)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getDriver(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get driver by id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Driver> getAll() {
        String getAllQuery = "SELECT * FROM drivers WHERE deleted = false";
        List<Driver> drivers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(getAllQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                drivers.add(getDriver(resultSet));
            }
            return drivers;
        } catch (SQLException e) {
            throw new RuntimeException("Can't read drivers table", e);
        }
    }

    @Override
    public Driver update(Driver driver) {
        String updateQuery = "UPDATE drivers SET name = ?, licence = ?"
                + "WHERE id = ? AND deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, driver.getName());
            preparedStatement.setString(2, driver.getLicenceNumber());
            preparedStatement.setLong(3, driver.getId());
            preparedStatement.executeUpdate();
            return driver;
        } catch (SQLException e) {
            throw new RuntimeException("Can't update driver " + driver, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteQuery = "UPDATE drivers SET deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Can't delete driver, id: " + id, e);
        }
    }

    private Driver getDriver(ResultSet resultSet) {
        try {
            Long driverId = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String licenseNumber = resultSet.getString("licence");
            Driver driver = new Driver(name, licenseNumber);
            driver.setId(driverId);
            return driver;
        } catch (SQLException e) {
            throw new RuntimeException("Cant get driver from resultSet");
        }
    }
}
