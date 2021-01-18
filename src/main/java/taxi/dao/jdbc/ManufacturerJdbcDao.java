package taxi.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import taxi.dao.ManufacturerDao;
import taxi.lib.Dao;
import taxi.lib.Inject;
import taxi.model.Manufacturer;
import taxi.services.util.ConnectionService;

@Dao
public class ManufacturerJdbcDao implements ManufacturerDao {
    @Inject
    ConnectionService connection;

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String query = "insert into manufacturers (name, country) values (?,?)";

        try {
            PreparedStatement statement = connection.getCon().prepareStatement(query,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            manufacturer.setId(resultSet.getLong(1));
            return manufacturer;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String query = "select * from manufacturers where id = ? and deleted = false";
        Manufacturer manufacturer = null;
        try {
            PreparedStatement statement = connection.getCon().prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                manufacturer = getManufacturer(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new RuntimeException("Can't get manufacturer with id" + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String query = "select * from manufacturers where deleted = false";
        List<Manufacturer> manufacturers = new ArrayList<>();
        try {
            PreparedStatement statement = connection.getCon().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                manufacturers.add(getManufacturer(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get manufacturers from database", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String query = "UPDATE manufacturers SET name = ?, country = ? "
                + "WHERE id = ? AND deleted = false;";
        try {
            PreparedStatement statement = connection.getCon().prepareStatement(query);
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.setLong(3, manufacturer.getId());
            statement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new RuntimeException("Can't update manufacturer: " + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE manufacturers SET deleted = true WHERE id = ?";
        try {
            PreparedStatement statement = connection.getCon().prepareStatement(query);
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Can't delete manufacturer, id: " + id, e);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) {
        try {
            Long manufacturerId = resultSet.getObject("id", Long.class);
            String manufacturerName = resultSet.getString("name");
            String manufacturerCountry = resultSet.getString("country");
            Manufacturer manufacturer = new Manufacturer(manufacturerName, manufacturerCountry);
            manufacturer.setId(manufacturerId);
            return manufacturer;
        } catch (SQLException e) {
            throw new RuntimeException("Can't create manufacturer from resultSet", e);
        }
    }
}
